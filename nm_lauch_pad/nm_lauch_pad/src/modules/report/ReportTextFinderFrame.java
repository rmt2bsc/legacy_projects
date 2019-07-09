package modules.report;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;

import net.miginfocom.swing.MigLayout;

import modules.LaunchPadException;

import com.InvalidDataException;
import com.TooManyInstancesException;

import com.ui.AbstractModelessWindow;
import com.ui.wordserach.TextCompWordSearcher;

/**
 * Displays the results of a report's find operation and allows the user to view
 * the results on a page by page basis.
 * <p>
 * This window operates on the Singleton pattern, which means only one instance
 * of this window can be open at a time.
 * 
 * @author rterrell
 *
 */
public class ReportTextFinderFrame extends AbstractModelessWindow implements
        HyperlinkListener {

    private static final long serialVersionUID = -3776428864990890547L;

    private static final Logger logger = Logger
            .getLogger(ReportTextFinderFrame.class);

    private ReportViewer viewer;

    private JEditorPane editor;

    private String searchArg;

    /**
     * Create a ReportTextFinderFrame specifying the its parent window, size,
     * position and window title.
     * 
     * @param parent
     *            an instance of {@link ReportViewer}
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public ReportTextFinderFrame(ReportViewer parent, Dimension size,
            Point pos, String title) {
        super(size, pos, title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        String content = this.generateContent(parent);
        this.viewer = parent;
        this.searchArg = this.viewer.getSearchArgument();
        this.editor.setText(content);
        // Make sure editor is scrolled to the top
        this.editor.setCaretPosition(0);
        this.editor.addHyperlinkListener(this);
    }

    /**
     * Performs specific constructor related tasks for this window.
     * <p>
     * Sets the text of the cancel to "Close" and removes the OK button from the
     * window.
     */
    @Override
    protected void initFrame() {
        // Display a more custom message before calling ancesotr initFram method
        // in the event the singleton pattern is violated.
        if (this.instanceExists()) {
            this.msg = "An instance of the Find Results window is already open.  Close it and try the \"Find\" operation again.";
            logger.error(this.msg);
            JOptionPane.showMessageDialog(this, this.msg, this.getTitle()
                    + " Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            throw new TooManyInstancesException(this.msg);
        }

        // Proceed with frame initialization
        super.initFrame();

        this.cancelButton.setText("Close");
        this.okButton.setVisible(false);
    }

    /**
     * Creates the content of the window i the form of HTML based on the find
     * results.
     * <p>
     * The generated content will vary based on whether or not a match was
     * found. When a mathc is found, the content will contain an overview
     * stating one or more matches were found, instructions on how to view the
     * results, and a list of page hyperlinks the user can select to view the
     * results. Each page hyperlink will indicate the number of occurrences that
     * were found. Conversely, when the find operation's criteria is not
     * sataisfied, the content will simply state that a match was not found and
     * indicate the search argument involved.
     * 
     * @param parent
     *            an instance of {@link ReportViewer} containing the actual
     *            report and its related meta information.
     * @return the content formatted as HTML.
     */
    protected String generateContent(ReportViewer parent) {
        String word = parent.getSearchArgument();
        StringBuffer htmlDetail = new StringBuffer();
        String pages[] = parent.getPages();
        for (int pageNdx = 0; pageNdx < pages.length; pageNdx++) {
            String content = pages[pageNdx];
            int pageWordCount = 0;
            int lastIndex = 0;
            int wordSize = word.length();
            while ((lastIndex = content.toLowerCase().indexOf(
                    word.toLowerCase(), lastIndex)) != -1) {
                int endIndex = lastIndex + wordSize;
                lastIndex = endIndex;
                pageWordCount++;
            }
            if (pageWordCount > 0) {
                htmlDetail.append("<a href=");
                htmlDetail.append(pageNdx + 1);
                htmlDetail.append(">Page ");
                htmlDetail.append(pageNdx + 1);
                htmlDetail.append("</a>&nbsp;&nbsp;(");
                htmlDetail.append(pageWordCount);
                htmlDetail.append(" occurrences)");
                htmlDetail.append("<br>");
            }
        }

        StringBuffer html = new StringBuffer();
        String content = null;
        if (htmlDetail.length() <= 0) {
            html.append("No matches found in the report for, ");
            html.append("<i>");
            html.append(parent.getSearchArgument());
            html.append("</i><br>");
        }
        else {
            html.append("<html>");
            html.append("<body>");

            // Setup Overview and Instruction statements
            html.append("Matches found for, ");
            html.append("<i>");
            html.append(parent.getSearchArgument());
            html.append("</i><br><br>");

            html.append(htmlDetail);
            html.append("</body>");
            html.append("</html>");
        }

        content = html.toString();
        return content;
    }

    /**
     * Uses the MigLayout to paint the layout of controls for this window.
     * 
     */
    @Override
    public JPanel createContentLayout() {
        this.editor = new JEditorPane();
        // set up editor pane
        Font f = new Font("Monospaced", Font.PLAIN, 12);
        editor = new JEditorPane();
        editor.setSize(this.size.width - 20, this.size.height);
        editor.setFont(f);
        editor.setEditable(false);
        editor.setContentType("text/html");

        // Use the MigLayout to setup window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[]"));
        mainPanel.setSize(this.size.width - 20, this.size.height);
        mainPanel.add(new JScrollPane(editor), "dock center");
        return mainPanel;
    }

    /**
     * Recognizes the user's selection of the page hyperlink and invokes the
     * process responsible for loading the selected page and highlighting its
     * find occurrences.
     * 
     * @param e
     *            An instance of {@link HyperlinkEvent} which corresponds to the
     *            page link selected by the user.
     * @throws RuntimeException
     *             Page number is invalid or missing.
     */
    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (!HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
            return;
        }
        String pageNoStr = e.getDescription();
        int pageNo;
        try {
            pageNo = Integer.parseInt(pageNoStr);
            this.highlightPageFindResults(pageNo, this.searchArg);
        } catch (NumberFormatException ex) {
            this.msg = "Selection is unable to load page due to the page number ia invalid or missing";
            logger.error(this.msg, ex);
            throw new RuntimeException(this.msg, ex);
        }
    }

    /**
     * Loads the page of the report with each find occurrence highlighted when a
     * page hyperlink is selected by the user.
     * 
     * @param pageNo
     *            an integer representing the page to be loaded.
     * @param searchArg
     *            The text value that is to be highlighted on the page.
     */
    protected void highlightPageFindResults(int pageNo, String searchArg) {
        JEditorPane parentEditor = this.viewer.getEditorPane();
        this.viewer.doGoToPage(pageNo);
        TextCompWordSearcher searcher = new TextCompWordSearcher(parentEditor);
        int offset = searcher.search(searchArg);
        if (offset != -1) {
            try {
                parentEditor.scrollRectToVisible(parentEditor
                        .modelToView(offset));
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Stub method.
     * 
     * @return Always returns null.
     * @throws InvalidDataException
     *             N/A
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        return null;
    }

    /**
     * Stub method.
     * 
     * @param data
     *            N/A
     * @return Always returns null.
     * @throws LaunchPadException
     *             N?A
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        return null;
    }

}

package modules.report;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;

import javax.print.attribute.PrintRequestAttributeSet;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import net.miginfocom.swing.MigLayout;

import modules.LaunchPadException;
import modules.model.ReportDetail;

import com.InvalidDataException;

import com.nv.util.ComponentPrintingException;
import com.nv.util.GeneralUtil;
import com.nv.util.OsFileConverter;
//import com.nv.util.PrintPreview;
import com.nv.util.PrintUtility;

import com.ui.AbstractModelessWindow;
import com.ui.WindowProcessTemplateListener;

/**
 * A report viewer for displaying the results of a report request.
 * <p>
 * Besides providing an area for view a report, this window provides other
 * abilities to better the user experience regarding report management. For
 * instance, text searching, page up/page down navigation, random page seeking,
 * printing of the current page displayed, printing to the entire report, and
 * saving the report to the file system.
 * 
 * @author rterrell
 *
 */
public class ReportViewer extends AbstractModelessWindow implements KeyListener {

    private static final long serialVersionUID = 1519672862513677303L;

    private static final Logger logger = Logger.getLogger(ReportViewer.class);

    /**
     * The action command to save the contents of window.
     */
    private static final String ACTION_COMMAND_FIND = "FIND_TEXT";

    private static final String ACTION_COMMAND_PGUP = "PAGE_UP";

    private static final String ACTION_COMMAND_PGDOWN = "PAGE_DOWN";

    private static final String ACTION_COMMAND_PRINT = "PRINT_DOC";

    private static final String ACTION_COMMAND_GOTO_PAGE = "GOTO_PAGE";

    private static final String SEARCHTEXT_COMP_NAME = "SEARCH_TEXT";

    private static final String GOTO_PAGE_COMP_NAME = "GOTO_PAGE";

    private static final int DISPLAY_FONT_SIZE = 12;

    private static final int PRINT_FONT_SIZE = 7;

    private static final String PRINT_FONT_FAMILY = "Monospaced"; // SansSerif

    private JLabel pageInfoLbl;

    private JTextField searchText;

    private JTextField goToPageText;

    private JButton goToButton;

    private JButton findButton;

    private JButton pageUpButton;

    private JButton pageDownButton;

    private JButton printButton;

    private JEditorPane editorPane;

    private ReportDetail rptInfo;

    private String report;

    private String pages[];

    private int curPage;

    /**
     * Create a ReportViewer supplying its size, position, the report details,
     * the report data, and window title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param rptInfo
     *            an instance of {@link ReportDetail} containing meta data for
     *            the report.
     * @param data
     *            the actual report data.
     * @param title
     *            the window's title.
     */
    public ReportViewer(Dimension size, Point pos, ReportDetail rptInfo,
            String data, String title) {
        super(size, pos, title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.rptInfo = rptInfo;

        // Split document into pages
        this.report = data;
        pages = data.split("\f");
        // Display first page.
        editorPane.setText(pages[0]);
        // Make sure editor is scrolled to the top
        this.editorPane.setCaretPosition(0);
        // Set current page
        this.curPage = 0;
        // Display page information
        this.updatePageInfo(this.curPage);

        String rptTitle = this.getReportTitle();
        this.setTitle(title + " - " + rptTitle);

        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(100, 100, screenDim.width - 150, screenDim.height - 200); // you
                                                                                 // have
                                                                                 // replaced
                                                                                 // the
                                                                                 // two
                                                                                 // parameters
                                                                                 // first
                                                                                 // width
                                                                                 // and
                                                                                 // then
                                                                                 // the
                                                                                 // height!;

    }

    /**
     * Performs specific constructor related tasks for this window.
     * <p>
     * Changes the text of the cancel and Ok buttons to "Close" and "Save",
     * respectively. Adds the buttons <i>Go</i>, <i>Page Down</i>, <i>Page
     * Up</i>, <i>Print</i>, <i>Print Page</i>, and <i>page</i>. Also, the
     * following text fields are added to the window's landscape for caputuring,
     * <i>Search Text</i> and <i>The Page Number to Navigate</i>.
     */
    @Override
    protected void initFrame() {
        this.setSingleton(false);

        super.initFrame();

        goToPageText = new JTextField(4);
        this.goToPageText.setName(ReportViewer.GOTO_PAGE_COMP_NAME);
        this.goToPageText.addKeyListener(this);
        goToButton = new JButton("Go");
        goToButton.setActionCommand(ReportViewer.ACTION_COMMAND_GOTO_PAGE);
        this.goToButton.setEnabled(false);
        goToButton.addActionListener(this);

        this.cancelButton.setText("Close");
        this.cancelButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_CANCEL);

        this.okButton.setText("Save");
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);

        // set up Page Up and Page Down buttons
        pageDownButton = new JButton("Page Down");
        pageDownButton.setActionCommand(ReportViewer.ACTION_COMMAND_PGDOWN);
        pageDownButton.addActionListener(this);
        pageUpButton = new JButton("Page Up");
        pageUpButton.setActionCommand(ReportViewer.ACTION_COMMAND_PGUP);
        pageUpButton.addActionListener(this);

        // Setup print button
        printButton = new JButton("Print");
        printButton.setActionCommand(ReportViewer.ACTION_COMMAND_PRINT);
        printButton.addActionListener(this);

        // put all control components in a panel
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("", "[][]20[][][] 30 [][][][] 5"));
        panel.add(pageUpButton);
        panel.add(pageDownButton);

        panel.add(new JLabel("Page"));
        panel.add(goToPageText);
        panel.add(goToButton);

        // panel.add(printPreviewButton);
        panel.add(printButton);
        panel.add(okButton);
        panel.add(this.cancelButton);

        this.buttonPane.add(panel);
    }

    /**
     * Uses the MigLayout to paint the layout of controls for this window.
     * 
     * @return an instance of {@link JPanel} which is subdivided into two JPanel
     *         objects; One for the Text Search area and the other for the
     *         contents of the report.
     */
    @Override
    public JPanel createContentLayout() {

        // set up the top panel containing search text facility and page
        // information
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new MigLayout("", "[][][][]", "[]"));
        this.searchText = new JTextField(15);
        this.searchText.setName(ReportViewer.SEARCHTEXT_COMP_NAME);
        this.searchText.addKeyListener(this);
        findButton = new JButton("Find");
        findButton.setActionCommand(ReportViewer.ACTION_COMMAND_FIND);
        findButton.addActionListener(this);
        this.findButton.setEnabled(false);
        searchPanel.add(new JLabel("Text Search"));
        searchPanel.add(searchText);
        searchPanel.add(findButton);
        pageInfoLbl = new JLabel();
        searchPanel.add(pageInfoLbl, "gapleft 100");

        // set up editor pane
        Font f = new Font(ReportViewer.PRINT_FONT_FAMILY, Font.PLAIN,
                ReportViewer.DISPLAY_FONT_SIZE);
        editorPane = new JEditorPane();
        editorPane.setSize(this.size.width - 20, this.size.height);
        editorPane.setFont(f);
        editorPane.setEditable(false);
        editorPane.setContentType("text/plain");

        // Now create the content pane
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[]", "[][]"));
        mainPanel.setSize(this.size.width - 20, this.size.height);
        mainPanel.add(searchPanel, "wrap");
        mainPanel.add(new JScrollPane(editorPane), "dock center");
        return mainPanel;
    }

    /**
     * Displays a dialog for the user to choose the name of the file and its
     * directory path where the report contents will be persisted.
     * 
     * @return Returns the full path of the file where the report will be
     *         stored. Returns null when the user cancels the dialoag.
     * @throws InvalidDataException
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        String selectedFile = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Report as File");
        chooser.setApproveButtonText("Save");
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile().getAbsolutePath();
        }
        return selectedFile;
    }

    /**
     * Saves the report to a specified location in the file system.
     * 
     * @param data
     *            The full path of the file where the report will be stored.
     * @return The path where the report was stored or null when <i>data</i>
     *         equals null.
     * @throws RuntimeException
     *             for general file IO errors.
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        String title = this.getReportTitle() + " Save File As";
        if (data == null) {
            this.msg = this.getReportTitle()
                    + ": user's request to save report was cancelled";
            logger.warn(this.msg);
            this.msg = "Save file operation cancelled";
            JOptionPane.showMessageDialog(this, this.msg, title,
                    JOptionPane.WARNING_MESSAGE);
            return this.msg;
        }

        // Save the report to a file
        String filePath = data.toString();
        String content = this.report;
        int rc = this.doSaveAsFile(filePath, content);
        // Show confirmation message to user
        this.msg = this.getReportTitle()
                + ": user's request to save report was successful";
        logger.info(this.msg);
        this.msg = "Save file operation successful";
        JOptionPane.showMessageDialog(this, this.msg, title,
                JOptionPane.INFORMATION_MESSAGE);
        // Return the number of bytes saved.
        return rc;
    }

    /**
     * Routes the user's actions to a particular action handler for processing.
     * 
     * @param evt
     *            an instance of {@link ActionEvent}
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        super.actionPerformed(evt);

        String command = evt.getActionCommand();

        if (command.equals(ReportViewer.ACTION_COMMAND_PGDOWN)) {
            this.doPageDown();
            return;
        }
        if (command.equals(ReportViewer.ACTION_COMMAND_PGUP)) {
            this.doPageUp();
            return;
        }
        if (command.equals(ReportViewer.ACTION_COMMAND_FIND)) {
            this.doFind();
            return;
        }
        if (command.equals(ReportViewer.ACTION_COMMAND_GOTO_PAGE)) {
            this.doGoToPage();
            return;
        }
        if (command.equals(ReportViewer.ACTION_COMMAND_PRINT)) {
            this.doPrint();
            return;
        }
    }

    /**
     * Saves the report to a specified location in the file system.
     * 
     * @param filePath
     *            The full path of the file where the report will be stored.
     * @param data
     *            The content to save.
     * @return int value as the total number of bytes save to the file.
     */
    private int doSaveAsFile(String filePath, String data) {
        try {
            OsFileConverter.convert(data, filePath,
                    OsFileConverter.STYLE_WINDOWS);
            return data.length();
        } catch (IOException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("Error occured saving report to file, ");
            buf.append(filePath);
            buf.append("\n\nDetail Message: ");
            buf.append(e.getMessage());
            buf.append("\n\nPlease contact the support team for assistance.");
            this.msg = buf.toString();
            JOptionPane.showMessageDialog(this, this.msg, "Save Report Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

    }

    /**
     * Action handler for the page down button
     */
    private void doPageDown() {
        if (this.curPage < (this.pages.length - 1)) {
            this.editorPane.setText(this.pages[++this.curPage]);
            this.updatePageInfo(this.curPage);
            // Make Sure the editor is scrolled all the way to the top
            this.editorPane.setCaretPosition(0);
        }
    }

    /**
     * Action handler for the page up button
     */
    private void doPageUp() {
        if (this.curPage > 0) {
            this.editorPane.setText(this.pages[--this.curPage]);
            this.updatePageInfo(this.curPage);
            // Make Sure the editor is scrolled all the way to the top
            this.editorPane.setCaretPosition(0);
        }
    }

    /**
     * Action handler for the GO button
     */
    private void doGoToPage() {
        int pageNo;
        try {
            pageNo = Integer.parseInt(this.goToPageText.getText());
        } catch (NumberFormatException e) {
            this.msg = "Error occurred trying to navigate to given page due to the target page value is a non numeric";
            logger.error(this.msg);
            return;
        }
        this.doGoToPage(pageNo);
    }

    /**
     * Navigates the user to a specified page.
     * 
     * @param pageNo
     *            An integer representing the page the user wishes to navigate
     *            to.
     */
    public void doGoToPage(int pageNo) {
        if (pageNo > 0 && pageNo <= this.pages.length) {
            // We have a valid page...load it
            this.curPage = pageNo - 1;
            this.editorPane.setText(this.pages[this.curPage]);
            this.updatePageInfo(this.curPage);
            // Make Sure the editor is scrolled all the way to the top
            this.editorPane.setCaretPosition(0);
            return;
        }
        else {
            this.msg = "An invalid page number was entered...must be a value in the page range of 1 and "
                    + this.pages.length;
            logger.error(this.msg);
            return;
        }
    }

    /**
     * Action handler for the Print button, which prints the entire report.
     */
    private void doPrint() {
        String rptTitle = this.getReportTitle();

        try {
            boolean portrait = false;
            if (this.rptInfo != null) {
                String orient = this.rptInfo.getRptOrientation();
                portrait = (orient == null ? false : orient
                        .equalsIgnoreCase("P"));
            }

            // Set font size based on print orientation
            int fontSize = PRINT_FONT_SIZE;
            logger.info("Report, " + rptTitle
                    + ", print statistics: Orientation ["
                    + (portrait ? "Portrait" : "Landscape") + "]  Font size ["
                    + fontSize + "]  Font [" + ReportViewer.PRINT_FONT_FAMILY
                    + "]");

            // Set printable font
            Font f = new Font(ReportViewer.PRINT_FONT_FAMILY, Font.BOLD,
                    fontSize);
            JEditorPane printEditorPane = new JEditorPane();
            printEditorPane.setFont(f);

            // Open print dialog
            PrintRequestAttributeSet attrs = PrintUtility
                    .getUserPrintAttributes(portrait, null);
            // Print each page of the report using the default printer
            if (attrs != null) {
                PrintUtility.print(printEditorPane, this.pages, attrs);
                this.msg = "A print job was created for report, " + rptTitle;
            }
            else {
                this.msg = "The print job was cancelled for report, "
                        + rptTitle;
            }

            // Clean up
            printEditorPane.setText("");
            printEditorPane = null;

            JOptionPane.showMessageDialog(this, this.msg);
            logger.info(this.msg);
        } catch (ComponentPrintingException e) {
            this.msg = "----------- Error occurred printing report, "
                    + rptTitle;
            logger.error(this.msg, e);
            throw new RuntimeException(this.msg, e);
        }
    }

    /**
     * Action handler for the Find button.
     */
    private void doFind() {
        ReportTextFinderFrame finder = new ReportTextFinderFrame(this,
                new Dimension(450, 300), new Point(1100, 50), "Find Results");
        finder.setVisible(true);
    }

    private String getReportTitle() {
        String rptTitle = (rptInfo == null || rptInfo.getRptDesc() == null ? "Unknown Report"
                : rptInfo.getRptDesc());
        return rptTitle;
    }

    /**
     * Updates the text on the window that displays current page and page range
     * information.
     * 
     * @param curPage
     *            The number of the current page
     */
    protected void updatePageInfo(int curPage) {
        StringBuffer buf = new StringBuffer();
        buf.append("Page ");
        buf.append(curPage + 1);
        buf.append(" of ");
        buf.append(this.pages.length);
        this.pageInfoLbl.setText(buf.toString());
        return;
    }

    /**
     * Returns the component containing the main content.
     * 
     * @return an instance of {@link JEditorPane}
     */
    public JEditorPane getEditorPane() {
        return editorPane;
    }

    /**
     * Returns the entire report as a String array of pages.
     * 
     * @return an array of Strings
     */
    public String[] getPages() {
        return pages;
    }

    /**
     * Returns the content of the Search Text input field.
     * 
     * @return trimmed content as a String, "" when the control is empty, or
     *         null when the search text input field component is null.
     */
    public String getSearchArgument() {
        return (searchText == null ? null : searchText.getText());
    }

    /**
     * Controls whether or not the <i>Find</i> and <i>Go</i> buttons are enabled
     * based on the content of their respecitive text input fields.
     * <p>
     * The Find button is enabled only its associated text field contains on or
     * more characters. The Go button is enabled only when its associated text
     * field contains a numeric value that is greater than zero and less than or
     * equal to the total number of report pages availble for view.
     * 
     * @param e
     *            an instance of {@link KeyEvent}
     */
    @Override
    public void keyReleased(KeyEvent e) {
        JTextField txtCntrl = null;
        if (e.getSource() instanceof JTextField) {
            txtCntrl = (JTextField) e.getSource();
        }
        String val = txtCntrl.getText();
        int len = val.length();

        if (txtCntrl.getName().equals(this.searchText.getName())) {
            this.findButton.setEnabled(len > 0);
            return;
        }
        if (txtCntrl.getName().equals(this.goToPageText.getName())) {
            this.goToButton.setEnabled(len > 0 && GeneralUtil.isNumeric(val)
                    && Integer.parseInt(val) > 0
                    && Integer.parseInt(val) <= this.pages.length);
            return;
        }
    }

    /**
     * Not implemented
     * 
     * @param e
     *            an instance of {@link KeyEvent}
     */
    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    /**
     * Not implemented
     * 
     * @param e
     *            an instance of {@link KeyEvent}
     */
    @Override
    public void keyPressed(KeyEvent e) {
        return;
    }
}

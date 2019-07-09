package modules.report;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import net.miginfocom.swing.MigLayout;

import modules.AppMessages;
import modules.LaunchPadException;

import modules.model.ReportDetail;
import modules.model.ReportHeader;
import modules.model.ReportParam;

import com.InvalidDataException;

import com.nv.db.DatabaseException;

import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;
import com.nv.util.GeneralUtil;

import com.ui.AbstractModelessWindow;
import com.ui.WindowProcessTemplateListener;

/**
 * Window for allowing the user to setup and submit requests for various
 * reports.
 * <p>
 * Once reqeuested, the report will be generated on the designated UNIX server,
 * and depending on the metho d of invocateion, will be returned back to the
 * Launch Pad application for viewing and/or printing. This window does not
 * require any security and can be invoked straight from the Launch Pad main
 * menu withot being logged into the system.
 * <p>
 * For each report, the user will have the option of printing the report
 * immediately, or bringing the report back for examination prior to printing.
 * Some reports will have additional parameters as needed. Once the user selects
 * a report, the window will gather the required arguments and perform the
 * following sequence of events:
 * <ol>
 * <li>Launch Pad (LP) opens a separate thread for the requested report.</li>
 * <li>The new thread opens a socket to the Unix server.</li>
 * <li>LP passes the report ID and arguments to the server.</li>
 * <li>If the report is designated as print-only, the server will respond with
 * an acknowledgement (instead of the report) after receiving the arguments. In
 * this case, LP will close the socket after receiving the acknowledgment.</li>
 * <li>Otherwise, the server will generate the text report, then return it to LP
 * via the socket.</li>
 * <li>After LP finishes receiving the report, it will close the socket.</li>
 * <li>LP will display the report in a window, and offer the user the
 * opportunity to search and/or print.</li>
 * </ol>
 * Most of the reports require only a fraction of a second to finish. However,
 * certain reports require several minutes for completion. This is why a
 * separate thread (and window) should be opened for each report request, so
 * that Launch Pad is not frozen while waiting for the data to be returned. The
 * report data returned to Launch Pad will be in the form of an ASCII text file.
 * The only special character returned will be a control-L, which indicates a
 * page feed.
 * 
 * @author rterrell
 *
 */
public class ReportRequestFrame extends AbstractModelessWindow implements
        ListSelectionListener {

    private static final long serialVersionUID = -3776428864990890547L;

    private static final Logger logger = Logger
            .getLogger(ReportRequestFrame.class);

    private static final String TITLE_PARM_SECT = "Report Parameters";

    private static final String TITLE_CATG_SECT = "Category";

    private static final String TITLE_REPORTS_SECT = "Reports";

    private static final String TITLE_PARAM_REPORT_HEAD = "Report Selected";

    private static final String TITLE_PARAM_NOSEL_REPORT_VALUE = "No Report Selected";

    private static final String DEFAULT_PARM_VAL_DAYSOUT = "8";

    private static final String ARG_PRINT = "P";

    private static final String ARG_DISPLAY = "D";

    private JList catgList;

    private JList rptList;

    private JPanel parmForm;

    private JPanel rptDetailList;

    private JLabel formTitleLbl;

    private JScrollPane rptListSp;

    private Map<String, ParameterComponentInfo> parameterListMap;

    /**
     * Inner class providing a structure for tracking parameter label/value
     * pairs for each report selected.
     * 
     * @author rterrell
     *
     */
    protected class ParameterComponentInfo {
        private JLabel label;

        private JComponent comp;

        private ButtonGroup buttonGrp;
    }

    /**
     * Create a ReportRequestFrame specifying the its size, position and window
     * title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public ReportRequestFrame(Dimension size, Point pos, String title) {
        super(size, pos, title);
    }

    /**
     * Performs specific constructor related tasks for this window.
     * <p>
     * Changes the text of the cancel and Ok buttons to "Exit PC List" and
     * "Close PC", respectively. Adds the refresh button and dht find sku button
     * and arranges the order which the buttons are displayed.
     */
    @Override
    protected void initFrame() {
        // Proceed with frame initialization
        super.initFrame();
        this.cancelButton.setText("Close");

        // Rename the label and set the command action name of the OK button.
        this.okButton.setText("Submit");
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);
        this.okButton.setEnabled(false);

        try {
            this.buttonPane.getComponentCount();
            this.buttonPane.setComponentZOrder(this.okButton, 0);
            this.buttonPane.setComponentZOrder(this.cancelButton, 1);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
        this.setResizable(false);
    }

    /**
     * Fetch all report header records from the database and package as a List
     * of ReportHeader objects.
     * 
     * @return List of {@link ReportHeader} objects
     * @throws DatabaseException
     */
    private List<ReportHeader> doReportHeaderQuery() throws DatabaseException {
        ReportFactory f = new ReportFactory();
        ReportDao dao = f
                .getDaoInstance(UserSecurityManager.getSecurityToken());

        try {
            List<ReportHeader> list = dao.fetchHeader();
            return list;
        } catch (DatabaseException e) {
            logger.error("Error fetching report header list from the database",
                    e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Fetch all report detail records related to a particular report category
     * and package the results as a List of ReportDetail objects.
     * 
     * @param rptCatgId
     *            The id of the report category
     * @return A List of {@link ReportDetail} objects.
     * @throws DatabaseException
     */
    private List<ReportDetail> doReportDetailQuery(String rptCatgId)
            throws DatabaseException {
        ReportFactory f = new ReportFactory();
        ReportDao dao = f
                .getDaoInstance(UserSecurityManager.getSecurityToken());
        try {
            List<ReportDetail> list = dao.fetchDetails(rptCatgId);
            return list;
        } catch (DatabaseException e) {
            logger.error("Error fetching report detail list from the database",
                    e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Fetch all parameters that a related to a particular report and package
     * the results as a list of ReportParam objects.
     * 
     * @param rptId
     *            The id of the report to fetch parameters for.
     * @return A List of {@link ReportParam} objects.
     * @throws DatabaseException
     */
    private List<ReportParam> doReportParamQuery(String rptId)
            throws DatabaseException {
        ReportFactory f = new ReportFactory();
        ReportDao dao = f
                .getDaoInstance(UserSecurityManager.getSecurityToken());
        try {
            List<ReportParam> list = dao.fetchParams(rptId);
            return list;
        } catch (DatabaseException e) {
            logger.error(
                    "Error fetching report parameter list from the database", e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Paints the report category list component.
     * 
     * @return {@link JPanel} containing the JList of report categories.
     */
    protected JPanel createReportCategoryList() {
        // Get list of report categories
        List<ReportHeader> list = null;
        ListModel model = null;
        try {
            list = this.doReportHeaderQuery();
            model = new ReportHeaderListModelImpl(list);
        } catch (DatabaseException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("Error occured obtain report category list.");
            buf.append("\n\nDetail Message: ");
            buf.append(e.getMessage());
            buf.append("\n\nPlease contact the support team for assistance.");
            this.msg = buf.toString();
            JOptionPane.showMessageDialog(this, this.msg,
                    "Report Request Database Initialization Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

        JList comp = new JList(model);
        comp.setCellRenderer(new ReportHeaderListCellRenderer());
        this.catgList = comp;
        this.catgList.addListSelectionListener(this);

        JLabel listTitle = new JLabel(ReportRequestFrame.TITLE_CATG_SECT);
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(180, 180));
        p.setLayout(new MigLayout("", "[]", "[] 5 []"));
        p.add(listTitle, "wrap");

        this.rptListSp = new JScrollPane(this.catgList);
        this.rptListSp.setPreferredSize(p.getPreferredSize());
        p.add(this.rptListSp);
        return p;
    }

    /**
     * Paints the list of reports as it relates to the selected report category.
     * 
     * @param rptCatgId
     *            The id of the report category to retrieve related reports.
     * @return {@link JPanel} containing the JList of report names.
     */
    protected JPanel createReportDetailList(String rptCatgId) {
        // Get list of report details by category
        List<ReportDetail> list = new ArrayList<ReportDetail>();
        ListModel model = new ReportDetailListModelImpl(list);
        JList comp = new JList(model);
        comp.setCellRenderer(new ReportDetailListCellRenderer());
        this.rptList = comp;
        this.rptList.addListSelectionListener(this);

        JLabel listTitle = new JLabel(ReportRequestFrame.TITLE_REPORTS_SECT);
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(180, 180));
        p.setLayout(new MigLayout("", "[]", "[] 5 []"));
        p.add(listTitle, "wrap");

        JScrollPane sp = new JScrollPane(this.rptList);
        sp.setPreferredSize(p.getPreferredSize());
        p.add(sp);
        return p;
    }

    /**
     * Paints the Report Parameter section of the window when the window is
     * first displayed.
     * 
     * @return A {@link JPanel} containing the form of parameters for user
     *         input.
     */
    protected JPanel createReportRequestForm() {
        // Initialize list to null since this is the first time that the form is
        // constructed
        JPanel mainPanel = new JPanel();
        // Use the MigLayout to setup window
        mainPanel.setLayout(new MigLayout("", "[]", "[] 5 []"));

        this.formTitleLbl = new JLabel(ReportRequestFrame.TITLE_PARM_SECT);
        mainPanel.add(this.formTitleLbl, "wrap");

        // build list of parameters
        this.parmForm = this.createReportParameterSection();
        JScrollPane sp = new JScrollPane(this.parmForm);
        sp.setPreferredSize(this.parmForm.getPreferredSize());
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        mainPanel.add(sp);
        return mainPanel;
    }

    /**
     * Creates the inner container of the Report Parameter which will only
     * include the value "No Report Selected" for Report Selected label.
     * 
     * @return A {@link JPanel}
     */
    private JPanel createReportParameterSection() {
        // Use the MigLayout to setup window
        String layoutColConfig = "[] 5 []";
        String layoutRowConfig = "[][][][][][][][][][]";
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(330, this.size.height - 120));

        p.setLayout(new MigLayout("", layoutColConfig, layoutRowConfig));

        // Indicate that no report is selected at this time...
        this.setSelectedRptTitleForParmForm(p, null);
        return p;
    }

    /**
     * Creates the appropriate Swing input component for a given report paramter
     * based on its arugment type.
     * <p>
     * By default, the "Display" is selected for the "Print or Display" form
     * field when the form is initially displayed.
     * 
     * @param parm
     *            An instance of {@link ReportParam} representing the actual
     *            report parameter.
     * @return An instance of {@link ParameterComponentInfo} containing the
     *         label component and the input component that is used to represent
     *         the report parameter.
     */
    private ParameterComponentInfo setupParameterComponent(ReportParam parm) {
        ParameterComponentInfo info = new ParameterComponentInfo();
        JLabel l = new JLabel();
        JTextField txtComp = new JTextField();
        parm.setArgType(parm.getArgType().trim());
        if (parm.getArgType().equalsIgnoreCase(
                ReportConstants.ARGTYPE_PRINT_OR_DISPLAY)) {
            l.setText(ReportConstants.ARGDESC_PRINT_OR_DISPLAY + ":");
            ButtonGroup grp = new ButtonGroup();
            JRadioButton rbPrint = new JRadioButton("Print");
            rbPrint.setActionCommand(ARG_PRINT);
            JRadioButton rbDisplay = new JRadioButton("Display");
            rbDisplay.setActionCommand(ARG_DISPLAY);
            rbDisplay.setSelected(true);
            grp.add(rbPrint);
            grp.add(rbDisplay);

            JPanel p = new JPanel();
            p.setName("radio_button_panel");
            p.setLayout(new MigLayout("", "[] 2 []", "[]"));
            p.add(rbPrint);
            p.add(rbDisplay);

            info.comp = p;
            info.label = l;
            info.buttonGrp = grp;
        }
        else if (parm.getArgType().equalsIgnoreCase(
                ReportConstants.ARGTYPE_PC_NO)) {
            l.setText(ReportConstants.ARGDESC_PC_NO + ":");
            info.label = l;
            info.comp = txtComp;
        }
        else if (parm.getArgType().equalsIgnoreCase(
                ReportConstants.ARGTYPE_DAYSOUT)) {
            l.setText(ReportConstants.ARGDESC_DAYSOUT + ":");
            info.label = l;
            txtComp.setText(ReportRequestFrame.DEFAULT_PARM_VAL_DAYSOUT);
            info.comp = txtComp;
        }
        else if (parm.getArgType().equalsIgnoreCase(
                ReportConstants.ARGTYPE_XFER_NO)) {
            l.setText(ReportConstants.ARGDESC_XFER_NO + ":");
            info.label = l;
            info.comp = txtComp;
        }
        else if (parm.getArgType().equalsIgnoreCase(
                ReportConstants.ARGTYPE_DATE)) {
            l.setText(ReportConstants.ARGDESC_DATE + ":");
            info.label = l;
            info.comp = txtComp;
        }
        else if (parm.getArgType().equalsIgnoreCase(
                ReportConstants.ARGTYPE_RTV_NO)) {
            l.setText(ReportConstants.ARGDESC_RTV_NO + ":");
            info.label = l;
            info.comp = txtComp;
        }
        else if (parm.getArgType().equalsIgnoreCase(
                ReportConstants.ARGTYPE_DEPT)) {
            l.setText(ReportConstants.ARGDESC_DEPT + ":");
            info.label = l;
            info.comp = txtComp;
        }
        else if (parm.getArgType().equalsIgnoreCase(
                ReportConstants.ARGTYPE_DATE_START)) {
            l.setText(ReportConstants.ARGDESC_DATE_START + ":");
            info.label = l;
            info.comp = txtComp;
        }
        else if (parm.getArgType().equalsIgnoreCase(
                ReportConstants.ARGTYPE_DATE_END)) {
            l.setText(ReportConstants.ARGDESC_DATE_END + ":");
            info.label = l;
            info.comp = txtComp;
        }
        return info;
    }

    /**
     * Uses the MigLayout to paint the layout of controls for this window.
     * 
     */
    @Override
    public JPanel createContentLayout() {
        // Get list of report categories
        JPanel p1 = this.createReportCategoryList();
        this.rptDetailList = this.createReportDetailList(null);
        JPanel p3 = this.createReportRequestForm();

        // Use the MigLayout to setup window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[] 5 [] [] ", "[] [] 10 []"));

        // Add data grid
        mainPanel.add(p1, "dock center");
        mainPanel.add(p3, "dock center, span 2 2, wrap");
        mainPanel.add(this.rptDetailList, "dock center, wrap");

        // Add message area
        this.msgLbl.setForeground(Color.RED);
        this.msgLbl.setVisible(false);
        mainPanel.add(this.msgLbl, "span 4");

        return mainPanel;
    }

    /**
     * This method updates the reports displayed in the Reports section of the
     * window when a new report category is selected.
     * 
     * @param rptCatgId
     *            The report category id that was selected.
     */
    protected void updateReportDetailList(String rptCatgId) {
        // Get list of report details by category
        List<ReportDetail> list = null;
        ReportDetailListModelImpl model = null;
        try {
            list = this.doReportDetailQuery(rptCatgId);
            if (list == null) {
                list = new ArrayList<ReportDetail>();
            }
            model = (ReportDetailListModelImpl) this.rptList.getModel();
            model.setItems(list);
            this.rptList.getSelectionModel().clearSelection();
            model.update();
        } catch (DatabaseException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("A database error occured while attemptig to update the report list for the selected category, ");
            buf.append(rptCatgId);
            buf.append(".");
            buf.append("\n\nDetail Message: ");
            buf.append(e.getMessage());
            buf.append("\n\nPlease contact the support team for assistance.");
            this.msg = buf.toString();
            JOptionPane.showMessageDialog(this, this.msg,
                    "Report Request Database Initialization Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the Report Parameters section of the window by dynamically
     * generating the input controls that are to represent the parameters for a
     * given report.
     * 
     * @param rpt
     *            The id of the selected report.
     */
    protected void updateReportRequestForm(ReportDetail rpt) {
        String rptId = (rpt == null ? null : rpt.getRptTag());
        String rptTitle = (rpt == null ? null : rpt.getRptDesc());
        // Get list of report parameters
        List<ReportParam> list = null;
        try {
            list = this.doReportParamQuery(rptId);
        } catch (DatabaseException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("A database error occured while attempting to construct report parameters for the selected report, ");
            buf.append(rpt.getRptDesc());
            buf.append(".");
            buf.append("\n\nDetail Message: ");
            buf.append(e.getMessage());
            buf.append("\n\nPlease contact the support team for assistance.");
            this.msg = buf.toString();
            JOptionPane.showMessageDialog(this, this.msg,
                    "Report Request Database Initialization Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

        // Remove all compoents contained in the panel
        this.parmForm.removeAll();
        // build new list of parameters and update GUI
        this.updateReportParameterSection(list, rptTitle);
        // make sure that the panel is repainted with the updates.
        this.parmForm.revalidate();
        this.repaint();
        return;
    }

    /**
     * Updates the report parameter form by cycling through the list of report
     * parameters in <i>rptParms</i> in order to build an appropriate input
     * component for each paramteter encountered.
     * 
     * @param rptParms
     *            A List of {@link ReportParam} objects representing one or more
     *            parameters.
     * @param reportTitle
     *            The name of the report that is selected. This will be
     *            displayed as the Report Selected.
     */
    private void updateReportParameterSection(List<ReportParam> rptParms,
            String reportTitle) {
        // Reset the map of parameters currently displayed
        this.parameterListMap = new LinkedHashMap<String, ParameterComponentInfo>();

        // Indicate the title of the selected report
        this.setSelectedRptTitleForParmForm(this.parmForm, reportTitle);

        if (rptParms == null) {
            return;
        }
        int totParms = rptParms.size();

        // Setup each input parameter
        for (int ndx = 0; ndx < totParms; ndx++) {
            ReportParam parm = rptParms.get(ndx);
            ParameterComponentInfo comp = this.setupParameterComponent(parm);
            this.parmForm.add(comp.label);

            String attr;
            if (comp.buttonGrp == null) {
                attr = "width 120:120:120";
            }
            else {
                attr = "width 180:180:180";
            }
            if ((ndx + 1) < totParms) {
                attr += ", wrap";
            }
            this.parmForm.add(comp.comp, attr);
            this.parameterListMap.put(parm.getArgType(), comp);
        }
        return;
    }

    /**
     * Assings the value of the Report Selected header in the Report Parameters
     * section.
     * 
     * @param formPanel
     *            The containing panel
     * @param rptTitle
     *            The report title.
     */
    private void setSelectedRptTitleForParmForm(JPanel formPanel,
            String rptTitle) {
        JLabel lnLblHead = new JLabel(
                ReportRequestFrame.TITLE_PARAM_REPORT_HEAD + ":");
        JLabel lnLblTxt = new JLabel();
        lnLblTxt.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        if (rptTitle == null) {
            lnLblTxt.setForeground(Color.RED);
            lnLblTxt.setText(ReportRequestFrame.TITLE_PARAM_NOSEL_REPORT_VALUE);
        }
        else {
            lnLblTxt.setForeground(Color.BLACK);
            lnLblTxt.setText(rptTitle);
        }
        formPanel.add(lnLblHead);
        formPanel.add(lnLblTxt, "width 180:180:180, wrap");
        return;
    }

    /**
     * Action handler that responds to List selections made in the Category and
     * Reports sections of the window.
     * 
     * @param evt
     *            An instance of {@link ListSelectionEvent}
     */
    public void valueChanged(ListSelectionEvent evt) {
        if (evt.getSource() instanceof JList) {
            JList src = (JList) evt.getSource();

            // Use the runtime data type of the JList's data model to determine
            // the origin of the selection
            ListModel model = src.getModel();
            // Evaluate Category selection
            if (model instanceof ReportHeaderListModelImpl) {
                ReportHeader rh = (ReportHeader) this.catgList
                        .getSelectedValue();
                try {
                    this.updateReportDetailList(rh.getCatgTag());
                    this.updateReportRequestForm(null);
                    this.okButton.setEnabled(false);
                    this.msgLbl.setVisible(false);
                } catch (Exception e) {
                    this.msg = "";
                    logger.error(this.msg, e);
                    throw new RuntimeException(this.msg, e);
                }
            }
            // Evaluate Report selection
            if (model instanceof ReportDetailListModelImpl) {
                ReportDetail rd = (ReportDetail) this.rptList
                        .getSelectedValue();
                try {
                    this.updateReportRequestForm(rd);
                    this.okButton.setEnabled(true);
                    this.msgLbl.setVisible(false);
                } catch (Exception e) {
                    this.msg = "";
                    logger.error(this.msg, e);
                    throw new RuntimeException(this.msg, e);
                }
            }
        }
    }

    /**
     * Validates and builds the report request based on the contents of each
     * available input report parameter.
     * 
     * @return A String as the official report request ready to be submitted to
     *         the UNIX server.
     * @throws InvalidDataException
     *             A validation error regarding any of the report parameters.
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        logger.info("Begin report input parameter validation: " + new Date());
        String reqValues = null;
        try {
            reqValues = this.validateInputParameters();
        } catch (InvalidDataException e) {
            this.msg = "Unable to send report request due to one or more input parameters are invalid";
            logger.error(this.msg, e);
            AppUtil.showMessage(this.msgLbl, e.getMessage(), true);
            throw new InvalidDataException(this.msg, e);
        }
        logger.info("End report input parameter validation: " + new Date());
        return this.buildReportRequest(reqValues);
    }

    /**
     * Submits the report request to the UNIX server for processing.
     * <p>
     * A separate thread is utilize for submitting the report request for the
     * sole purpose of not blocking the application in the event the report
     * yields a long running process. Most of the reports require only a
     * fraction of a second to finish. However, certain reports require serveal
     * minutes to process, hence, the need for an additional thread.
     * 
     * @param data
     *            The report request String disguised as an arbitrary object.
     * @return The report request String disguised as an arbitrary object.
     * @throws LaunchPadException
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        String request = data.toString();
        logger.info("The following report request was submitted to the UNIX server: "
                + request);
        ReportDetail rd = (ReportDetail) this.rptList.getSelectedValue();
        ReportRequestWorker worker = new ReportRequestWorker(request, rd);
        worker.execute();

        // Determine what confirmation message to display based on value
        // selected for "Print or Display" argument.
        ParameterComponentInfo comp = this.parameterListMap
                .get(ReportConstants.ARGTYPE_PRINT_OR_DISPLAY);
        ButtonModel model = comp.buttonGrp.getSelection();
        String parmValue = model.getActionCommand();
        if (parmValue.equalsIgnoreCase(ARG_PRINT)) {
            this.msg = "Your report request has been submitted.";
        }
        else if (parmValue.equalsIgnoreCase(ARG_DISPLAY)) {
            this.msg = "Your report request has been submitted, and the results will be displayed momentarily.";
        }

        String rptTitle = "Report [" + rd.getRptDesc().trim()
                + "] Submittal Confirmation";
        JOptionPane.showMessageDialog(this, this.msg, rptTitle,
                JOptionPane.INFORMATION_MESSAGE);
        return request;
    }

    /**
     * Validates the contents of all report parameters displayed for the
     * selected report.
     * <p>
     * All parameters are required to have a value.
     * 
     * @return A String containing the value of each parameter in the order
     *         displayed on the screen. Each value is separated by a space since
     *         all parameter values contain no spaces.
     * @throws InvalidDataException
     *             A parameter was found to be invalid.
     */
    protected String validateInputParameters() throws InvalidDataException {
        Iterator<String> keys = this.parameterListMap.keySet().iterator();
        StringBuffer buf = new StringBuffer();
        while (keys.hasNext()) {
            buf.append(" ");
            String key = keys.next();
            ParameterComponentInfo comp = this.parameterListMap.get(key);
            String val = this.validateParameter(key, comp);
            buf.append(val);
        }
        return buf.toString();
    }

    /**
     * Validates a single report parameter.
     * <p>
     * The list below contains the valid parameter types:
     * <table border="1">
     * <tr>
     * <th>Parmater Type</th>
     * <th>Desription</th>
     * </tr>
     * <tr>
     * <td>PrintOrDisplay</td>
     * <td>Either “P” or “D”</td>
     * </tr>
     * <tr>
     * <td>PCNumber</td>
     * <td>Number between 6 and 10 digits</td>
     * </tr>
     * <tr>
     * <td>DaysOut</td>
     * <td>Field should default to 8 and allow values from 1 to 10</td>
     * </tr>
     * <tr>
     * <td>XferNumber</td>
     * <td>Number between 6 and 15 digits</td>
     * </tr>
     * <tr>
     * <td>Date</td>
     * <td>A valid date (passed with format “mm/dd/yyyy”)</td>
     * </tr>
     * <tr>
     * <td>RTVNumber</td>
     * <td>Number between 4 and 9 digits</td>
     * </tr>
     * <tr>
     * <td>Dept</td>
     * <td>Number between 1 and 5 digits</td>
     * </tr>
     * <tr>
     * <td>StartDate</td>
     * <td>Starting date in a date range. Must be paired with an end date.</td>
     * </tr>
     * <tr>
     * <td>EndDate</td>
     * <td>Ending date in a date range. Should be greater than or equal to the
     * start date.</td>
     * </tr>
     * </table>
     * 
     * @param parmType
     *            A String representing the parameter type.
     * @param comp
     *            An instance of {@link ParameterComponentInfo}, which contains
     *            the actual input component, related to the parameter where the
     *            value entered by the user can be obtained.
     * @return The value of the parameter entered by the user.
     * @throws InvalidDataException
     *             When <i>parmType</i> is found not to have a value or its data
     *             is invalid.
     */
    protected String validateParameter(String parmType,
            ParameterComponentInfo comp) throws InvalidDataException {
        String parmValue = null;
        if (parmType.equalsIgnoreCase(ReportConstants.ARGTYPE_PRINT_OR_DISPLAY)) {
            ButtonModel model = comp.buttonGrp.getSelection();
            if (model == null) {
                parmValue = null;
            }
            else {
                parmValue = model.getActionCommand();
            }
        }
        else {
            parmValue = ((JTextField) comp.comp).getText().trim();
        }

        int numValue = 0;
        if (parmType.equalsIgnoreCase(ReportConstants.ARGTYPE_PRINT_OR_DISPLAY)) {
            if (parmValue == null
                    || parmValue.equals(GeneralUtil.spaces(parmValue.length()))
                    || parmValue.equals("")) {
                this.msg = "A value for the Print or Display parameter must be selected";
                throw new InvalidDataException(this.msg);
            }
        }
        else if (parmType.equalsIgnoreCase(ReportConstants.ARGTYPE_PC_NO)) {
            if (parmValue == null
                    || parmValue.equals(GeneralUtil.spaces(parmValue.length()))
                    || parmValue.equals("")) {
                this.msg = "Price Change Number is required to have a value";
                throw new InvalidDataException(this.msg);
            }
            if (!GeneralUtil.isNumeric(parmValue)) {
                this.msg = "Price Change Number must be numeric";
                throw new InvalidDataException(this.msg);
            }
            if (parmValue.length() < 6 || parmValue.length() > 10) {
                this.msg = "Price Change Number must be 6 to 10 digits in length";
                throw new InvalidDataException(this.msg);
            }
        }
        else if (parmType.equalsIgnoreCase(ReportConstants.ARGTYPE_DAYSOUT)) {
            if (parmValue == null
                    || parmValue.equals(GeneralUtil.spaces(parmValue.length()))
                    || parmValue.equals("")) {
                this.msg = "Days Out is required to have a value";
                throw new InvalidDataException(this.msg);
            }
            if (!GeneralUtil.isNumeric(parmValue)) {
                this.msg = "Days Out must be numeric";
                throw new InvalidDataException(this.msg);
            }
            numValue = Integer.parseInt(parmValue);
            if (numValue < 1 || numValue > 10) {
                this.msg = "Days Out is required to be a number between 1 and 10";
                throw new InvalidDataException(this.msg);
            }
        }
        else if (parmType.equalsIgnoreCase(ReportConstants.ARGTYPE_XFER_NO)) {
            if (parmValue == null
                    || parmValue.equals(GeneralUtil.spaces(parmValue.length()))
                    || parmValue.equals("")) {
                this.msg = "Transfer Number is required to have a value";
                throw new InvalidDataException(this.msg);
            }
            if (!GeneralUtil.isNumeric(parmValue)) {
                this.msg = "Transfer Number must be numeric";
                throw new InvalidDataException(this.msg);
            }
            if (parmValue.length() < 6 || parmValue.length() > 15) {
                this.msg = "Transfer Number must be 6 to 15 digits in length";
                throw new InvalidDataException(this.msg);
            }
        }
        else if (parmType.equalsIgnoreCase(ReportConstants.ARGTYPE_DATE)) {
            if (parmValue == null
                    || parmValue.equals(GeneralUtil.spaces(parmValue.length()))
                    || parmValue.equals("")) {
                this.msg = AppMessages.MSG_INVALID_DATE;
                throw new InvalidDataException(this.msg);
            }

            try {
                Date dt = GeneralUtil.validateLaunchPadDateEdits("Date",
                        parmValue);
                parmValue = GeneralUtil.formatDate(dt, "MM/dd/yyyy");
            } catch (Exception e) {
                throw new InvalidDataException(AppMessages.MSG_INVALID_DATE, e);
            }
        }
        else if (parmType.equalsIgnoreCase(ReportConstants.ARGTYPE_RTV_NO)) {
            if (parmValue == null
                    || parmValue.equals(GeneralUtil.spaces(parmValue.length()))
                    || parmValue.equals("")) {
                this.msg = "RTV Number is required to have a value";
                throw new InvalidDataException(this.msg);
            }
            if (!GeneralUtil.isNumeric(parmValue)) {
                this.msg = "RTV Number must be numeric";
                throw new InvalidDataException(this.msg);
            }
            if (parmValue.length() < 4 || parmValue.length() > 9) {
                this.msg = "RTV Number must be 4 to 9 digits in length";
                throw new InvalidDataException(this.msg);
            }
        }
        else if (parmType.equalsIgnoreCase(ReportConstants.ARGTYPE_DEPT)) {
            if (parmValue == null
                    || parmValue.equals(GeneralUtil.spaces(parmValue.length()))
                    || parmValue.equals("")) {
                this.msg = "Department is required to have a value";
                throw new InvalidDataException(this.msg);
            }
            if (!GeneralUtil.isNumeric(parmValue)) {
                this.msg = "Department must be numeric";
                throw new InvalidDataException(this.msg);
            }
            if (parmValue.length() < 1 || parmValue.length() > 5) {
                this.msg = "Department must be 1 to 5 digits in length";
                throw new InvalidDataException(this.msg);
            }
        }
        else if (parmType.equalsIgnoreCase(ReportConstants.ARGTYPE_DATE_START)) {
            if (parmValue == null
                    || parmValue.equals(GeneralUtil.spaces(parmValue.length()))
                    || parmValue.equals("")) {
                this.msg = AppMessages.MSG_INVALID_DATE_START;
                throw new InvalidDataException(this.msg);
            }

            try {
                Date dt = GeneralUtil.validateLaunchPadDateEdits("Start Date",
                        parmValue);
                parmValue = GeneralUtil.formatDate(dt, "MM/dd/yyyy");
            } catch (Exception e) {
                throw new InvalidDataException(
                        AppMessages.MSG_INVALID_DATE_START, e);
            }

            // The component should exist, but did the user enter a value for
            // it?
            ParameterComponentInfo pairedComp = this.parameterListMap
                    .get(ReportConstants.ARGTYPE_DATE_END);
            String pairedValue = ((JTextField) pairedComp.comp).getText();
            if (pairedValue.equals(null) || pairedValue.equals("")) {
                this.msg = "Start Date is required to be paired with an End Date";
                throw new InvalidDataException(this.msg);
            }
        }
        else if (parmType.equalsIgnoreCase(ReportConstants.ARGTYPE_DATE_END)) {
            if (parmValue == null
                    || parmValue.equals(GeneralUtil.spaces(parmValue.length()))
                    || parmValue.equals("")) {
                this.msg = AppMessages.MSG_INVALID_DATE_END;
                throw new InvalidDataException(this.msg);
            }

            Date endDate = null;
            try {
                endDate = GeneralUtil.validateLaunchPadDateEdits("End Date",
                        parmValue);
            } catch (Exception e) {
                throw new InvalidDataException(AppMessages.MSG_INVALID_DATE_END);
            }

            // At this point, the start date component should exist and be
            // valid.
            // Validate if end date is greater than or equal to start date
            ParameterComponentInfo pairedComp = this.parameterListMap
                    .get(ReportConstants.ARGTYPE_DATE_START);
            String pairedValue = ((JTextField) pairedComp.comp).getText();
            Date startDate = null;
            try {
                startDate = GeneralUtil.stringToDate(pairedValue);
                parmValue = GeneralUtil.formatDate(endDate, "MM/dd/yyyy");
            } catch (Exception e) {
                throw new InvalidDataException(e);
            }
            if (endDate.before(startDate)) {
                this.msg = "End Date is required to be greater than or equal to the Start Date";
                throw new InvalidDataException(this.msg);
            }

        }
        return parmValue;
    }

    /**
     * Constructs a report request based on the the parameter list values,
     * <i>reqValues</i>.
     * <p>
     * Each report request will contain at least three arguments. They are 1)
     * the length of the request as a four byte ASCII value, 2) the report ID,
     * and 3) a flag indicating whether the report should be printed or
     * displayed. Additional arguments are included depending on the specific
     * report. Each argument will be separated by a space. As an example, the
     * transfer extract report will require three parameters:
     * 
     * <pre>
     *    1) Request Length
     *    2) Report ID
     *    3) Flag indicating Print or Display
     *    4) Extract Date.
     * </pre>
     * 
     * So the data passed to the Unix server will look like this:<br>
     * <i>0025xfer-extract D 01/25/2012</i>.
     * 
     * @param parmList
     *            A String containing a list of all the values of the report
     *            parameters where each value is separated by a space.
     * @return The complete report request.
     * @throws InvalidDataException
     *             Inability to obtain the report id of the selected report.
     */
    protected String buildReportRequest(String parmList)
            throws InvalidDataException {
        logger.debug("Begin report input parameter assembly: " + new Date());
        String request = null;
        ReportDetail rd = (ReportDetail) this.rptList.getSelectedValue();
        String rptId = rd.getRptTag();
        if (rptId == null) {
            this.msg = "Failed to build report request due to the report id is invalid or inaccessible";
            logger.fatal(this.msg);
            throw new InvalidDataException(this.msg);
        }
        parmList = rptId.trim() + parmList;
        int len = parmList.length();
        String reqLen = null;
        if (len < 10) {
            reqLen = "000" + len;
        }
        else if (len >= 10 && len < 100) {
            reqLen = "00" + len;
        }
        else if (len >= 100 && len < 1000) {
            reqLen = "0" + len;
        }
        request = reqLen + parmList;

        logger.debug("End report input parameter assembly: " + new Date());
        return request;
    }

}

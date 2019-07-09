package modules.rtv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import modules.LaunchPadException;

import modules.model.RtvHeader;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import com.InvalidDataException;
import com.NotFoundException;

import com.nv.db.DatabaseException;

import com.nv.util.AppUtil;

import com.ui.AbstractModelessWindow;
import com.ui.WindowProcessTemplateListener;

import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.event.ComponentItemSelectedEvent;
import com.ui.event.ComponentSelectionListener;

import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * This is a window frame equipped with common RTV functionality for displaying
 * a list of header records, selecting and editing the header record, changing
 * the status of a given header record, refreshing the header list, and closing
 * the RTV Admin.
 * 
 * @author rterrell
 *
 */
public class CommonRtvFrame extends AbstractModelessWindow implements
        ComponentSelectionListener {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger.getLogger(CommonRtvFrame.class);

    /**
     * The title for the Transfer Header Edit dialog
     */
    public static final String UI_HEAD_DETAIL_TITLE = "Detail For %s% RTV ";

    /**
     * The action command name for the "Refresh List" button
     */
    public static final String ACTION_COMMAND_REFRESH = "REFRESH_LIST";

    /**
     * The action command name for the "Edit Header" button
     */
    public static final String ACTION_COMMAND_DETAILS = "EDIT_DETAILS";

    /**
     * The action command name for changing the status of the header to Suspend'
     */
    public static final String ACTION_COMMAND_STATUS_SUSPEND = "suspendheader";

    /**
     * The action command name for printing the Vendor Detail Report'
     */
    public static final String ACTION_COMMAND_PRINT = "printreport";

    /**
     * The Refresh command button.
     */
    protected JButton refreshButton;

    /**
     * The Print Report command button.
     */
    protected JButton printButton;

    /**
     * The data grid component.
     */
    protected ScrollableDataGrid grid;

    /**
     * The data grid's column definition component.
     */
    protected List<ColumnDefinition> colDefs;

    /**
     * An instance of {@link RtvHeader} for holding the data of the currently
     * selected row of the data grid.
     */
    private RtvHeader selectedHeader;

    /**
     * Generic dialog for displayig and updating a list of RTV detail records.
     */
    private CommonRtvDetailListDialog detailsDialog;

    /**
     * Create a CommonRtvFrame specifying the its size, position and window
     * title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public CommonRtvFrame(Dimension size, Point pos, String title) {
        super(size, pos, title);
    }

    /**
     * Performs specific constructor related tasks for painting the components
     * of this window.
     * <p>
     * Changes the text of the cancel and Ok buttons to "Exit List" and
     * "Suspend", respectively. Adds the refresh button and the "Print" button
     * and arranges the order which the buttons are displayed.
     */
    @Override
    protected void initFrame() {
        // Proceed with frame initialization
        super.initFrame();

        this.cancelButton.setText("Exit List");

        // Rename the label and set the command action name of the OK button.
        this.okButton.setText("Suspend");
        this.okButton.setEnabled(false);

        this.refreshButton = new JButton("Refresh List");
        this.refreshButton
                .setActionCommand(CommonRtvFrame.ACTION_COMMAND_REFRESH);
        this.refreshButton.addActionListener(this);
        this.buttonPane.add(this.refreshButton);

        this.printButton = new JButton("Print");
        this.printButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);
        this.printButton.setEnabled(false);
        this.printButton.setVisible(false);
        this.printButton.addActionListener(this);
        this.buttonPane.add(this.printButton);

        // Resequence the z-indexes of the buttons
        try {
            this.buttonPane.getComponentCount();
            this.buttonPane.setComponentZOrder(this.refreshButton, 0);
            this.buttonPane.setComponentZOrder(this.printButton, 1);
            this.buttonPane.setComponentZOrder(this.okButton, 2);
            this.buttonPane.setComponentZOrder(this.cancelButton, 3);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Stub method for performing the query to obtain data for the data grid.
     * 
     * @return always returns null
     * @throws DatabaseException
     */
    protected List<RtvHeader> doQuery() throws DatabaseException {
        return null;
    }

    /**
     * Creates the structure of the ScrollableDataGrid based on the List of data
     * items that are of the datatype, RtvHeader.
     * <p>
     * The column definitions are required to be setup at the descendant object.
     * 
     * @param list
     *            a List of {@link RtvHeader} objects
     * @return an instance of {@link ScrollableDataGrid}
     * @throws NotFoundException
     *             the column difinition component was invalid or not setup
     *             properly.
     */
    protected ScrollableDataGrid createDataGrid(List<RtvHeader> list)
            throws NotFoundException {
        if (this.colDefs == null) {
            this.msg = "Unable to discover column definitions for data grid.  Please contact the support team for assistance";
            logger.fatal(this.msg);
            throw new NotFoundException(this.msg);
        }

        // Set the size of the data grid component
        Dimension size = new Dimension(880, 250);
        // Create ScrollableDataGrid grid component
        ScrollableDataGrid dg = new ScrollableDataGrid(list, this.colDefs,
                ListSelectionModel.SINGLE_SELECTION, size);
        dg.addItemSelectionListener(this);
        return dg;
    }

    /**
     * Uses the MigLayout to layout the controls for this window.
     * 
     */
    @Override
    public JPanel createContentLayout() {
        // Get active price change info from the database
        List<RtvHeader> list;
        try {
            list = this.doQuery();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        // Build grid with PC data
        this.grid = this.createDataGrid(list);

        // Use the MigLayout to setup window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[]", "[]20[]"));

        // Add data grid
        mainPanel.add(this.grid, "dock center, wrap");

        // Add message area
        this.msgLbl.setForeground(Color.RED);
        this.msgLbl.setVisible(false);
        mainPanel.add(this.msgLbl);

        return mainPanel;
    }

    /**
     * Gathers the input data needed for handling the RTV Header Details
     * request.
     * 
     * @return the current selection of the data grid which is the Transfer
     *         record to be updated.
     * @throws InvalidDataException
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        return this.selectedHeader;
    }

    /**
     * Triggers the process of printing the Vendor Detail Report.
     * 
     * @param data
     *            The RTV header record to be updated.
     * @return an isntance of teh {@link RtvHeader} object just updated.
     * @throws LaunchPadException
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        RtvHeader item = null;
        if (data instanceof RtvHeader) {
            item = (RtvHeader) data;
        }
        else {
            return null;
        }

        return item;
    }

    /**
     * The handler for opening the Details List Dialog for a given RTV Header
     * record.
     * 
     * @param item
     *            an instance of {@link RtvHeader}
     */
    protected void openHeaderDetailListWindow(RtvHeader item) {
        try {
            this.detailsDialog
                    .setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            this.detailsDialog.setVisible(true);
        } catch (Exception e) {
            StringBuffer buf = new StringBuffer();
            buf.append("A problem occurred opening the ");
            buf.append(this.detailsDialog.getTitle());
            buf.append(" diaolog.\n");
            buf.append(e.getMessage());
            buf.append(".\n\nPlease contact the support team for assistance");
            this.msg = buf.toString();
            CommonRtvFrame.logger.fatal(this.msg, e);
            String errorTitle = this.getTitle() + "Error";
            JOptionPane.showMessageDialog(this, this.msg, errorTitle,
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    /**
     * Impelented as a stub method
     * 
     * @param newStatus
     *            the status that will be applied to the RTV header record.
     * @return Always returns false
     */
    protected boolean updateHeaderStatus(String newStatus) {
        return false;
    }

    /**
     * Reloads the data grid by way of querying the database for the list of RTV
     * Header records.
     */
    protected void refreshListData() {
        try {
            // Get active price change info from the database
            List<RtvHeader> list = this.doQuery();
            // reload grid with new PC data list
            this.grid.loadView(list);

            // Disable buttons that require a row to be selected.
            this.okButton.setEnabled(false);
            this.printButton.setEnabled(false);
            this.displayRecordCount();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the click actions of the <i>Refresh List</i>, <i>Suspend</i>, and
     * <i>Exut List</i> buttons.
     * 
     * @param e
     *            instance of {@link ActionEvent}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getActionCommand().equals(CommonRtvFrame.ACTION_COMMAND_REFRESH)) {
            this.refreshListData();
        }
        if (e.getActionCommand().equals(
                CommonRtvFrame.ACTION_COMMAND_STATUS_SUSPEND)) {
            this.updateHeaderStatus(RtvConstants.STATUS_SUSPEND);
        }
    }

    /**
     * Inovkes the handler setup for opening the RTV header details window.
     * 
     * @param evt
     *            an instance of {@link ComponentItemDoubleClickedEvent}
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        RtvHeader item = (RtvHeader) evt.getSelectedItem();
        this.selectedHeader = item;
        this.openHeaderDetailListWindow(item);
    }

    /**
     * Identifies the selected item and enables the Print and Suspend buttons by
     * default.
     * <p>
     * Since the specific types of RTV records require numerous strict
     * validations, the validation logic should be deferred to the descendent of
     * that RTV type.
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        RtvHeader item = null;
        if (evt.getSelectedItem() == null) {
            return;
        }
        if (evt.getSelectedItem() instanceof RtvHeader) {
            item = (RtvHeader) evt.getSelectedItem();
        }

        this.printButton.setEnabled(true);
        this.okButton.setEnabled(true);
        this.selectedHeader = item;
    }

    /**
     * Sets the details dialog instance.
     * 
     * @param detailsDialog
     *            an instance of {@link CommonRtvDetailListDialog}
     */
    public void setDetailsDialog(CommonRtvDetailListDialog detailsDialog) {
        this.detailsDialog = detailsDialog;
    }

    /**
     * Returns the selected RTV header record
     * 
     * @return the selectedHeader
     */
    public RtvHeader getSelectedHeader() {
        return selectedHeader;
    }

    /**
     * Displays the total number of RTV Heaer records fetched from the database
     * in the message area.
     */
    @Override
    protected void displayRecordCount() {
        super.displayRecordCount();

        // Dispaly record count
        this.msg = this.grid.getTableView().getRowCount() + " records found";
        AppUtil.showMessage(this.msgLbl, this.msg, false);
    }

}

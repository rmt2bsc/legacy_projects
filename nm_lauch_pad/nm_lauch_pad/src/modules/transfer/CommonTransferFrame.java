package modules.transfer;

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
import modules.model.Transfer;
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
 * This is a window frame equipped with common Transfer functionality for
 * displaying a list of header records, selecting and editing a header record,
 * chanting the status of a given header record to "Finish/Unfinish", refreshing
 * the header list, and closing the Transfer window.
 * 
 * @author rterrell
 *
 */
public class CommonTransferFrame extends AbstractModelessWindow implements
        ComponentSelectionListener {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(CommonTransferFrame.class);

    /**
     * The title for the Transfer Header Edit dialog
     */
    public static final String UI_HEAD_DETAIL_TITLE = "Header Detail For %s% Transfer ";

    /**
     * The title for the Transfer Detail List dialog
     */
    public static final String UI_DETAIL_LIST_TITLE = "Detail For %s% Transfer ";

    /**
     * The action command name for the "Refresh List" button
     */
    public static final String ACTION_COMMAND_REFRESH = "REFRESH_LIST";

    /**
     * The action command name for the "Edit Header" button
     */
    public static final String ACTION_COMMAND_EDIT = "EDIT_HEADER";

    /**
     * The action command name for changing the status of an item to Finish'
     */
    public static final String ACTION_COMMAND_FINISH_ITEM = "finishitem";

    /**
     * The action command name for changing the status of an item to Unfinish'
     */
    public static final String ACTION_COMMAND_UNFINISH_ITEM = "unfinishitem";

    /**
     * The Refresh command button.
     */
    protected JButton refreshButton;

    /**
     * The Edit Header command button.
     */
    protected JButton editButton;

    /**
     * The data grid component.
     */
    protected ScrollableDataGrid grid;

    /**
     * The data grid's column definition component.
     */
    protected List<ColumnDefinition> colDefs;

    /**
     * An instance of {@link Transfer} for holding the data of the currently
     * selected row of the data grid.
     */
    private Transfer selectedTransfer;

    /**
     * Generic dialog for updating a transfer detail record.
     */
    private CommonTransferDetailListDialog detailsDialog;

    /**
     * Create a CommonTransferFrame specifying the its size, position and window
     * title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public CommonTransferFrame(Dimension size, Point pos, String title) {
        super(size, pos, title);
    }

    /**
     * Performs specific constructor related tasks for painting the components
     * of this window.
     * <p>
     * Changes the text of the cancel and Ok buttons to "Exit List" and
     * "Finish", respectively,. Adds the refresh button and the "Edit Header"
     * button and arranges the order which the buttons are displayed.
     */
    @Override
    protected void initFrame() {
        // Proceed with frame initialization
        super.initFrame();

        this.cancelButton.setText("Exit List");

        // Rename the label and set the command action name of the OK button.
        this.okButton.setText("Finish");
        this.okButton.setEnabled(false);

        this.refreshButton = new JButton("Refresh List");
        this.refreshButton
                .setActionCommand(CommonTransferFrame.ACTION_COMMAND_REFRESH);
        this.refreshButton.addActionListener(this);
        this.buttonPane.add(this.refreshButton);

        this.editButton = new JButton("Edit Header");
        this.editButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);
        this.editButton.setEnabled(false);
        this.editButton.addActionListener(this);
        this.buttonPane.add(this.editButton);

        // Resequence the z-indexes of the buttons
        try {
            this.buttonPane.getComponentCount();
            this.buttonPane.setComponentZOrder(this.refreshButton, 0);
            this.buttonPane.setComponentZOrder(this.editButton, 1);
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
    protected List<Transfer> doQuery() throws DatabaseException {
        return null;
    }

    /**
     * Creates the structure of the ScrollableDataGrid based on the List of data
     * items that are of the datatype, Transfer.
     * <p>
     * The column definitions are required to be setup at the descendant object.
     * 
     * @param list
     *            a List of {@link Transfer} objects
     * @return an instance of {@link ScrollableDataGrid}
     * @throws NotFoundException
     *             the column difinition component was invalid or not setup
     *             properly.
     */
    protected ScrollableDataGrid createDataGrid(List<Transfer> list)
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
        List<Transfer> list;
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
     * Gathers the input data needed for handling the Transfer Header Edit
     * request.
     * 
     * @return the current selection of the data grid which is the Transfer
     *         record to be updated.
     * @throws InvalidDataException
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        return this.selectedTransfer;
    }

    /**
     * Triggers the process of opening the Transfer Header Edit dialog
     * 
     * @param data
     *            The transfer header record to be updated.
     * @return an isntance of teh {@link Transfer} object just updated.
     * @throws LaunchPadException
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        Transfer item = null;
        if (data instanceof Transfer) {
            item = (Transfer) data;
        }
        else {
            return null;
        }
        this.editHeader(item);
        return item;
    }

    /**
     * The handler for opening the Header Details List Dialog for a given
     * Transfer record.
     * 
     * @param item
     */
    protected void openTransferDetailListWindow(Transfer item) {
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
            CommonTransferFrame.logger.fatal(this.msg, e);
            String errorTitle = this.getTitle() + "Error";
            JOptionPane.showMessageDialog(this, this.msg, errorTitle,
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    /**
     * The handler for opening the Header Edit Dialog for a given Tranfer
     * record.
     * 
     * @param item
     *            The Transfer model to edit.
     */
    protected void editHeader(Transfer item) {
        try {
            String transferTypeDesc = null;
            if (item.getTranferType() == TransferConstants.XFER_TYPE_SI) {
                transferTypeDesc = "SI";
            }
            else {
                transferTypeDesc = "BI";
            }
            String dialogTitle = CommonTransferFrame.UI_HEAD_DETAIL_TITLE
                    .replaceAll("%s%", transferTypeDesc);
            CommonTransferHeaderEditDialog dialog = new CommonTransferHeaderEditDialog(
                    this, item, new Dimension(350, 300), new Point(740, 400),
                    dialogTitle);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);

            // Update selected model
            Transfer modifiedModel = dialog.getModifiedModel();

            // Abort if user canceled dialog
            if (modifiedModel == null) {
                return;
            }

            // Preform an in-memory refresh of grid or refresh grid from the
            // database
            this.selectedTransfer.setToStore(modifiedModel.getToStore());
            this.selectedTransfer.setCarrierId(modifiedModel.getCarrierId());
            this.selectedTransfer.setPkgType(modifiedModel.getPkgType());
            this.selectedTransfer.setManifestNo(modifiedModel.getManifestNo());
            this.selectedTransfer.setProBill(modifiedModel.getProBill());
            this.selectedTransfer.setShipDate(modifiedModel.getShipDate());
            this.selectedTransfer.setReasonCd(modifiedModel.getReasonCd());
            int currentRow = this.grid.getSelectedRow();
            this.grid.refreshGrid();
            this.grid.scrollToVisible(currentRow);
        } catch (Exception e) {
            StringBuffer buf = new StringBuffer();
            buf.append("A problem occurred opening the Transfer Header Edit diaolog.\n");
            buf.append(e.getMessage());
            buf.append(".\n\nPlease contact the support team for assistance");
            this.msg = buf.toString();
            CommonTransferFrame.logger.fatal(this.msg, e);
            String errorTitle = (item.getTranferType() == TransferConstants.XFER_TYPE_BI ? "BI "
                    : "SI ")
                    + "Transfer Admin";
            JOptionPane.showMessageDialog(this, this.msg, errorTitle,
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    /**
     * Provides common functionality to identify the target status which the
     * user intends to change the status of the Transfer header.
     * <p>
     * <i>Finish</i> and <i>Unfinish</i> are the only statuses supported by this
     * class. The descendent can offer logic to support more statuses. The only
     * thing this method does is to 1) identify the destiation status and 2)
     * populate the message member variable with a confirmation message to be
     * viewed by the user from the descendent component. When a status match is
     * found, true is returned and the caller or descendant class can identify
     * the confirmation message via the member variable, <i>this.msg</i>, to use
     * at his/her disscetion.
     * 
     * @param newStatus
     *            the status that will be applied to the transfer record. Valid
     *            values are "F" (Finish) and "C" (Closed).
     * @return true is returned when <i>newStatus</i> is recognized by the
     *         implementation and this.msg variable contains the user message.
     *         Otherwise, false is the result.
     */
    protected boolean updateHeaderStatus(String newStatus) {
        if (newStatus == null) {
            return false;
        }

        boolean statusFound = false;
        if (newStatus.equalsIgnoreCase(TransferConstants.STATUS_FINISH)
                || newStatus.equalsIgnoreCase(TransferConstants.STATUS_COLSED)) {
            // Construct confirmation message
            this.msg = " Transfer #" + this.selectedTransfer.getTranferId()
                    + ".  Are You Sure?";
            if (newStatus.equalsIgnoreCase(TransferConstants.STATUS_FINISH)) {
                this.msg = "Finish" + this.msg;
                statusFound = true;
            }
            else if (newStatus
                    .equalsIgnoreCase(TransferConstants.STATUS_COLSED)) {
                this.msg = "Unfinish" + this.msg;
                statusFound = true;
            }
        }
        else {
            statusFound = false;
        }
        return statusFound;
    }

    /**
     * Reloads the data grid by way of querying the database for the list of
     * transfer records.
     */
    protected void refreshListData() {
        try {
            // Get active price change info from the database
            List<Transfer> list = this.doQuery();
            // reload grid with new PC data list
            this.grid.loadView(list);

            // Disable buttons that require a row to be selected.
            this.okButton.setEnabled(false);
            this.editButton.setEnabled(false);
            this.displayRecordCount();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the click actions of the <i>Refresh List</i>, <i>Edit Header</i>,
     * <i>Finish/Unfinish</i> and <i>Exut List</i> buttons.
     * 
     * @param e
     *            instance of {@link ActionEvent}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getActionCommand()
                .equals(CommonTransferFrame.ACTION_COMMAND_EDIT)) {
            this.editHeader(this.selectedTransfer);
        }
        if (e.getActionCommand().equals(
                CommonTransferFrame.ACTION_COMMAND_REFRESH)) {
            this.refreshListData();
        }
        if (e.getActionCommand().equals(
                CommonTransferFrame.ACTION_COMMAND_FINISH_ITEM)) {
            this.updateHeaderStatus(TransferConstants.STATUS_FINISH);
        }
        if (e.getActionCommand().equals(
                CommonTransferFrame.ACTION_COMMAND_UNFINISH_ITEM)) {
            this.updateHeaderStatus(TransferConstants.STATUS_COLSED);
        }
    }

    /**
     * Inovkes the handler setup for opening the Transfer header details window.
     * 
     * @param evt
     *            an instance of {@link ComponentItemDoubleClickedEvent}
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        Transfer item = (Transfer) evt.getSelectedItem();
        this.selectedTransfer = item;
        if (item.getStatus().equalsIgnoreCase(
                TransferConstants.STATUS_SUMMARY_RECV)) {
            this.msg = "Transfer is in received status and has no detail records to display";
            String errorTitle = "Transfer Details Error";
            JOptionPane.showMessageDialog(this, this.msg, errorTitle,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.openTransferDetailListWindow(item);
    }

    /**
     * Dynamically manages the state of the "Finish/Unfinish" Status button and
     * the "Edit Header" button.
     * <p>
     * For all intents and purposes of this particular use case, this button
     * state includes 1) the text that is displayed and 2) whether or not the
     * button is enabled. The button's state change is based on the row
     * currently selected in the data grid. The following rules apply for the
     * button's state changes:
     * <ol>
     * <li>When the selected transfer record has a status of <b>"C"</b>
     * (Closed), the button's text will read "Finish" and is required to be
     * enabled.</li>
     * <li>When the selected transfer record has a status of <b>"F"</b>
     * (Finished), the button's text will read "Unfinished" and is required to
     * be enabled.</li>
     * <li>When the selected transfer record has a status of something else, the
     * button's text will read "Finished" and the button will be disabled.</li>
     * </ol>
     * <p>
     * The Edit Header button is enabled once a selection is made from the data
     * grid.
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        boolean okToEdit = false;
        Transfer item = null;
        if (evt.getSelectedItem() == null) {
            return;
        }
        if (evt.getSelectedItem() instanceof Transfer) {
            item = (Transfer) evt.getSelectedItem();
        }
        if (item.getStatus().equalsIgnoreCase("F")) {
            okToEdit = true;
            this.okButton.setText("Unfinished");
            this.okButton
                    .setActionCommand(CommonTransferFrame.ACTION_COMMAND_UNFINISH_ITEM);
            this.okButton.setEnabled(true);
        }
        else if (item.getStatus().equalsIgnoreCase("C")) {
            okToEdit = true;
            this.okButton.setText("Finished");
            this.okButton.setEnabled(true);
            this.okButton
                    .setActionCommand(CommonTransferFrame.ACTION_COMMAND_FINISH_ITEM);
        }
        else {
            this.okButton.setText("Finished");
            this.okButton.setEnabled(false);
        }

        this.editButton.setEnabled(okToEdit);
        this.selectedTransfer = item;
    }

    /**
     * Sets the details dialog instance.
     * 
     * @param detailsDialog
     *            an instance of {@link CommonTransferDetailListDialog}
     */
    public void setDetailsDialog(CommonTransferDetailListDialog detailsDialog) {
        this.detailsDialog = detailsDialog;
    }

    /**
     * @return the selectedTransfer
     */
    public Transfer getSelectedTransfer() {
        return selectedTransfer;
    }

    /**
     * Displays the total number of transfer records fetched from the database
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

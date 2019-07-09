package modules.transfer.bi;

import java.awt.Dimension;
import java.awt.Point;

import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modules.model.Transfer;

import modules.transfer.CommonTransferFrame;
import modules.transfer.TransferConstants;
import modules.transfer.TransferDao;

import org.apache.log4j.Logger;

import com.NotFoundException;

import com.nv.db.DatabaseException;

import com.nv.security.UserSecurityManager;

import com.nv.util.AppUtil;

import com.ui.event.ComponentItemSelectedEvent;
import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * This is the main window for managing Buyer Initiated (BI) Transfers.
 * 
 * @author rterrell
 *
 */
public class BITransferFrame extends CommonTransferFrame {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(BITransferFrame.class);

    /**
     * The action command name for changing the status of an item to Finish'
     */
    public static final String ACTION_COMMAND_AVAIL_ITEM = "zeropostitem";

    /**
     * Create a BITransferFrame specifying the its size, position and window
     * title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public BITransferFrame(Dimension size, Point pos, String title) {
        super(size, pos, title);
    }

    /**
     * Intitalizes the frame and setup the data grid row sorters for numeric and
     * Date related columns.
     */
    @Override
    protected void initFrame() {
        super.initFrame();

        // Identify those columns that need to use special comparators for
        // sorting.
        // This is good place for this event since the the grid is setup from
        // the ancestor
        int numericCols[] = { 1, 2, 3, 4, 5 };
        this.grid.setNumericColumnSorter(numericCols);
        int dateCols[] = { 0 };
        this.grid.setDateColumnSorter(dateCols);
    }

    /**
     * Fetches the BI Transfer header data from the database.
     * <p>
     * All BI transfer header header records associated with a status of “B”,
     * “C”, “F”, and “R” are fetch from the database and packaged into a List
     * collection of Transfer objects.
     * 
     * @return a List of {@link Transfer} objects
     * @throws DatabaseException
     */
    protected List<Transfer> doQuery() throws DatabaseException {
        List<Transfer> list = null;
        TransferDao dao = null;
        try {
            BITransferFactory f = new BITransferFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchTransfers();
            return list;
        } catch (DatabaseException e) {
            logger.error(
                    "Error fetching BI Transfer header list from the database",
                    e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Creates the data grid for the BI Transfer frame based on the List of
     * Transfer data items that are to be displayed in the grid data.
     * 
     * @param list
     *            a List of {@link Transfer} objects
     * @return an instance of {@link ScrollableDataGrid}
     * @throws NotFoundException
     *             the column difinition component was invalid or not setup
     *             properly.
     */
    protected ScrollableDataGrid createDataGrid(List<Transfer> list) {

        // Setup column definitions for BI Transfer header list
        this.colDefs = new ArrayList<ColumnDefinition>();
        this.colDefs.add(new ColumnDefinition("sendByDateStr", "Send By", 0,
                100));
        this.colDefs
                .add(new ColumnDefinition("tranferId", "Transfer #", 1, 130));
        this.colDefs.add(new ColumnDefinition("dept", "Dept", 2, 75));
        this.colDefs.add(new ColumnDefinition("toStore", "To Store", 3, 80));
        this.colDefs
                .add(new ColumnDefinition("fromStore", "From Store", 4, 80));
        this.colDefs.add(new ColumnDefinition("reasonCd", "RC", 5, 50));
        this.colDefs.add(new ColumnDefinition("status", "S", 6, 50));
        this.colDefs.add(new ColumnDefinition("message", "Message", 7, 293));

        try {
            return super.createDataGrid(list);
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    this.getTitle(), JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    /**
     * The handler for opening the BI Tranfer Header Edit Dialog for a given
     * Tranfer record.
     * <p>
     * The incoming transfer object, <i>item</i>, is tagged or identified as a
     * BI transfer type. This is done as a means of providing additional
     * information to common transfer header edit process so that the
     * appropriate resources are utilized for the item.
     * <p>
     * Disallows the user from editing a BI Transfer with a status of Available,
     * Detail Recevied, and Summary Received in which their codes are, "A", "B",
     * or "R", respectively.
     * <p>
     * When one of these codes are encounterd, an appropriate message is
     * displayed to the user in the form of a popup window. Next, the
     * CommonTransferHeaderDialog window is closed and control is returned to
     * the caller. Otherwise, the user is allowed to edit the transfer header
     * record.
     * 
     * @param item
     *            The Transfer model to edit.
     */
    @Override
    protected void editHeader(Transfer item) {
        // Identify the transfer item type as "BI"
        item.setTranferType(TransferConstants.XFER_TYPE_BI);

        String errorTitle = this.getTitle() + " Error";
        if (item.getStatus().equalsIgnoreCase(
                TransferConstants.STATUS_AVAILABLE)) {
            this.msg = "The BI Transfer is still in work and cannot be edited at this time.";
            JOptionPane.showMessageDialog(this, this.msg, errorTitle,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if (item.getStatus().equalsIgnoreCase(
                TransferConstants.STATUS_DETAIL_RECV)
                || item.getStatus().equalsIgnoreCase(
                        TransferConstants.STATUS_SUMMARY_RECV)) {
            this.msg = "This is an inbound BI Transfer which cannot be edited at this time.";
            JOptionPane.showMessageDialog(this, this.msg, errorTitle,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // invoke the dialog from the ancestor.
        super.editHeader(item);
    }

    /**
     * Uses an instance of BITransferDetailListDialog for opening the BI
     * Transfer Header SKU Details List dialog for a given Transfer record.
     * 
     * @param item
     *            an instance of {@link Transfer} used to fetch and manage its
     *            SKU details
     */
    @Override
    protected void openTransferDetailListWindow(Transfer item) {
        // Identify the transfer item type as "BI"
        item.setTranferType(TransferConstants.XFER_TYPE_BI);
        // Setup window
        String dialogTitle = CommonTransferFrame.UI_DETAIL_LIST_TITLE
                .replaceAll("%s%", "BI");
        BITransferDetailListDialog dialog = new BITransferDetailListDialog(
                this, item, new Dimension(720, 330), new Point(720, 400),
                dialogTitle);
        this.setDetailsDialog(dialog);
        // Execute window using common ancestor logic
        super.openTransferDetailListWindow(item);
    }

    /**
     * Updates the status of the BI Transfer record to the value of
     * <i>newStatus</i>.
     * 
     * @param newStatus
     *            the status that will be applied to the transfer record. Valid
     *            values are "F" (Finish) and "C" (Closed).
     * @return true when the BI Transfer Header's status is updated successfully
     *         as <i>Finish</i> or <i>Unfinish</i>. Otherwise, false is returned
     *         and the update status is aborted.
     */
    @Override
    protected boolean updateHeaderStatus(String newStatus) {
        boolean statusFound = super.updateHeaderStatus(newStatus);

        Transfer item = this.getSelectedTransfer();
        if (!statusFound) {
            return false;
        }

        // Apply update to the database.
        TransferDao dao = null;
        int rowIndex = this.grid.getSelectedRow();
        try {
            BITransferFactory f = new BITransferFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            int rows = dao.updateStatus(item, newStatus);

            // Display friendly message
            StringBuffer buf = new StringBuffer();
            buf.append("BI Transfer status was changed successfully for ");
            buf.append(item.getTranferId());
            buf.append(".  ");
            buf.append(rows);
            buf.append(" rows(s) effected header/detail records.");
            this.msg = buf.toString();
            logger.info(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, false);

            // Update model selected item status so that it can be reflected in
            // the view
            item.setStatus(newStatus);
            this.grid.refreshGrid();
            this.grid.scrollToVisible(rowIndex);
            return true;
        } catch (DatabaseException e) {
            this.msg = "Error updating status to \'" + newStatus
                    + "\' for Transfer number, " + item.getTranferId();
            logger.error(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new RuntimeException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Dynamically changes the text of the Status button to "Zero Post" when the
     * transfer status equals "A".
     * <p>
     * Calls the ancestor to manage the state of Status and the Edit Header
     * buttons in regards to displaying and/or handling their respective
     * actions.
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        super.handleSelectionChanged(evt);

        Transfer item = null;
        if (evt.getSelectedItem() == null) {
            return;
        }
        if (evt.getSelectedItem() instanceof Transfer) {
            item = (Transfer) evt.getSelectedItem();
        }
        if (item.getStatus().equalsIgnoreCase("A")) {
            this.okButton.setText("Zero Post");
            this.okButton
                    .setActionCommand(BITransferFrame.ACTION_COMMAND_AVAIL_ITEM);
            this.okButton.setEnabled(true);
        }
    }

    /**
     * Handles the click actions of the <i>Zero Post</i> button.
     * <p>
     * In additon to the zero post button, this method also recognizes other
     * actions that may occur at the ancestor.
     * 
     * @param e
     *            instance of {@link ActionEvent}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        if (e.getActionCommand().equals(
                BITransferFrame.ACTION_COMMAND_AVAIL_ITEM)) {
            this.updateHeaderStatus(TransferConstants.STATUS_COLSED);
        }
    }

}

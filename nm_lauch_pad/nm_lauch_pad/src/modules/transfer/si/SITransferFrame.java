package modules.transfer.si;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import modules.model.Transfer;
import modules.transfer.CommonTransferFrame;
import modules.transfer.TransferConstants;
import modules.transfer.TransferDao;

import org.apache.log4j.Logger;

import com.NotFoundException;

import com.nv.db.DatabaseException;
import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;
import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * This is the main window for managing Store Initiated (SI) Transfers.
 * 
 * @author rterrell
 *
 */
public class SITransferFrame extends CommonTransferFrame {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(SITransferFrame.class);

    /**
     * Create a SITransferFrame specifying the its size, position and window
     * title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public SITransferFrame(Dimension size, Point pos, String title) {
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
        int numericCols[] = { 1, 2, 3, 4, 5, 6 };
        this.grid.setNumericColumnSorter(numericCols);
        int dateCols[] = { 0 };
        this.grid.setDateColumnSorter(dateCols);
    }

    /**
     * Fetches the SI Transfer header data from the database.
     * <p>
     * All SI transfer header header records associated with a status of “B”,
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
            SITransferFactory f = new SITransferFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchTransfers();
            return list;
        } catch (DatabaseException e) {
            logger.error(
                    "Error fetching Transfer header list from the database", e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Creates the data grid for the SI Transfer frame based on the List of
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

        // Setup column definitions for SI Transfer header list
        this.colDefs = new ArrayList<ColumnDefinition>();
        this.colDefs
                .add(new ColumnDefinition("transferDateStr", "Date", 0, 100));
        this.colDefs.add(new ColumnDefinition("master", "Master Transfer", 1,
                130));
        this.colDefs
                .add(new ColumnDefinition("tranferId", "Transfer #", 2, 110));
        this.colDefs.add(new ColumnDefinition("dept", "Dept", 3, 75));
        this.colDefs
                .add(new ColumnDefinition("fromStore", "From Store", 4, 80));
        this.colDefs.add(new ColumnDefinition("toStore", "To Store", 5, 80));
        this.colDefs.add(new ColumnDefinition("reasonCd", "RC", 6, 50));
        this.colDefs.add(new ColumnDefinition("status", "S", 7, 50));
        this.colDefs.add(new ColumnDefinition("message", "Message", 8, 200));

        try {
            return super.createDataGrid(list);
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    this.getTitle(), JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    /**
     * The handler for opening the SI Tranfer Header Edit Dialog for a given
     * Tranfer record.
     * <p>
     * The incoming transfer object, <i>item</i>, is tagged or identified as a
     * SI transfer type. This is done as a means of providing additional
     * information to common transfer header edit process so that the
     * appropriate resources are utilized for the item.
     * 
     * @param item
     *            The Transfer model to edit.
     */
    @Override
    protected void editHeader(Transfer item) {
        // Identify the transfer item type as "SI"
        item.setTranferType(TransferConstants.XFER_TYPE_SI);

        // invoke the dialog from the ancestor.
        super.editHeader(item);
    }

    /**
     * Uses an instance of SITransferDetailListDialog for opening the SI
     * Transfer Header SKU Details List dialog for a given Transfer record.
     * 
     * @param item
     *            an instance of {@link Transfer} used to fetch and manage its
     *            SKU details
     */
    @Override
    protected void openTransferDetailListWindow(Transfer item) {
        // Identify the transfer item type as "SI"
        item.setTranferType(TransferConstants.XFER_TYPE_SI);
        // Setup window
        String dialogTitle = CommonTransferFrame.UI_DETAIL_LIST_TITLE
                .replaceAll("%s%", "SI");
        SITransferDetailListDialog dialog = new SITransferDetailListDialog(
                this, item, new Dimension(645, 330), new Point(740, 400),
                dialogTitle);
        this.setDetailsDialog(dialog);
        // Execute window using common ancestor logic
        super.openTransferDetailListWindow(item);
    }

    /**
     * Updates the status of the SI Transfer record to the value of
     * <i>newStatus</i>.
     * 
     * @param newStatus
     *            the status that will be applied to the transfer record. Valid
     *            values are "F" (Finish) and "C" (Closed).
     * @return true when the SI Transfer Header's status is updated successfully
     *         as <i>Finish</i> or <i>Unfinish</i>. Otherwise, false is returned
     *         and the update status is aborted.
     */
    @Override
    protected boolean updateHeaderStatus(String newStatus) {
        boolean statusFound = super.updateHeaderStatus(newStatus);

        if (!statusFound) {
            return false;
        }

        Transfer item = this.getSelectedTransfer();
        int rowIndex = this.grid.getSelectedRow();
        TransferDao dao = null;
        try {
            SITransferFactory f = new SITransferFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            int rows = dao.updateStatus(item, newStatus);

            // Display friendly message
            StringBuffer buf = new StringBuffer();
            buf.append("SI Transfer status was changed successfully for ");
            buf.append(item.getTranferId());
            buf.append(".  ");
            buf.append(rows);
            buf.append(" rows(s) effected header/detail records.");
            this.msg = buf.toString();
            logger.info(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, false);

            // Update model with the new status so that it can be reflected in
            // the view
            if (item.getMaster() == null) {
                // Update the status only for the selected item since it is not
                // assoicated with a master transfer number.
                item.setStatus(newStatus);
                this.grid.refreshGrid();
                this.grid.scrollToVisible(rowIndex);
            }
            else {
                // Update the status of all items with the same master transfer
                // number.
                MasterXferNoStatusUpdater statUpdater = new MasterXferNoStatusUpdater(
                        item.getMaster(), newStatus, rowIndex);
                SwingUtilities.invokeLater(statUpdater);
            }

            // Preform an in-memory refresh of grid or refresh grid from the
            // database
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
     * Inner class to be used as a separate thread when updating the status of
     * all items that have the same master transfer number.
     * 
     * @author rterrell
     *
     */
    private class MasterXferNoStatusUpdater implements Runnable {

        private long masterXferNo;

        private int currentRow;

        private String newStatus;

        private int effectRowCount = 0;

        private MasterXferNoStatusUpdater(long xferNo, String status,
                int currentRow) {
            this.masterXferNo = xferNo;
            this.newStatus = status;
            this.currentRow = currentRow;
        }

        @Override
        public void run() {
            int totalRows = grid.getTableView().getRowCount();

            for (int ndx = 0; ndx < totalRows; ndx++) {
                Transfer item = (Transfer) grid.getTableView()
                        .getSelectedRowData(ndx);
                if (item.getMaster() != null
                        && item.getMaster() == masterXferNo) {
                    item.setStatus(newStatus);
                    grid.refreshGrid();
                    effectRowCount++;
                }
            }
            grid.scrollToVisible(currentRow);
            return;
        }

    }
}

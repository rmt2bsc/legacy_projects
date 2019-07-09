package modules.rtv.bi;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modules.model.RtvHeader;

import modules.rtv.CommonRtvFrame;
import modules.rtv.RtvConstants;
import modules.rtv.RtvDao;

import org.apache.log4j.Logger;

import com.NotFoundException;

import com.nv.db.DatabaseException;

import com.nv.security.UserSecurityManager;

import com.nv.util.AppUtil;

import com.ui.event.ComponentItemSelectedEvent;

import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * This is the main window for managing Buyer Initiated (BI) RTV information.
 * 
 * @author rterrell
 *
 */
public class BIRtvFrame extends CommonRtvFrame {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger.getLogger(BIRtvFrame.class);

    /**
     * The action command name for changing the status of the header to Open'
     */
    public static final String ACTION_COMMAND_STATUS_REOPEN = "reopenheader";

    /**
     * The action command name for changing the status of the header to Closed'
     */
    public static final String ACTION_COMMAND_STATUS_ZEROPOST = "zeropost";

    /**
     * Create a BIRtvFrame specifying the its size, position and window title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public BIRtvFrame(Dimension size, Point pos, String title) {
        super(size, pos, title);
    }

    /**
     * Intitalize the frame and setup the data grid's row sorters for numeric
     * and Date related columns.
     */
    @Override
    protected void initFrame() {
        super.initFrame();

        // Permanently set status button to "Suspended".
        this.okButton.setText("Suspended");
        this.okButton
                .setActionCommand(CommonRtvFrame.ACTION_COMMAND_STATUS_SUSPEND);

        // Identify those columns that need to use special comparators for
        // sorting.
        // This is good place for this event since the the grid is setup from
        // the ancestor
        int numericCols[] = { 1, 2, 3 };
        this.grid.setNumericColumnSorter(numericCols);
        int dateCols[] = { 0 };
        this.grid.setDateColumnSorter(dateCols);
    }

    /**
     * Fetches the BI RTV header record data from the database.
     * <p>
     * All BI RTV header header records associated with a status of “O”, “I”,
     * “C”, “S”, and “Z” are fetch from the database and packaged into a List
     * collection of RTV Header objects.
     * 
     * @return a List of {@link RtvHeader} objects
     * @throws DatabaseException
     */
    protected List<RtvHeader> doQuery() throws DatabaseException {
        List<RtvHeader> list = null;
        RtvDao dao = null;
        try {
            BIRTVFactory f = new BIRTVFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchHeader();
            return list;
        } catch (DatabaseException e) {
            logger.error("Error fetching BI RTV header list from the database",
                    e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Creates the data grid for the BI RTV frame based on the List of RTV data
     * items that are to be displayed in the grid data.
     * 
     * @param list
     *            a List of {@link RtvHeader} model objects to be displayed.
     * @return an instance of {@link ScrollableDataGrid}
     * @throws NotFoundException
     *             the column difinition component was invalid or not setup
     *             properly.
     */
    protected ScrollableDataGrid createDataGrid(List<RtvHeader> list) {

        // Setup column definitions for BI RTV header list
        this.colDefs = new ArrayList<ColumnDefinition>();
        this.colDefs
                .add(new ColumnDefinition("dateEnteredStr", "Date", 0, 100));
        this.colDefs.add(new ColumnDefinition("rtvNo", "RTV #", 1, 50));
        this.colDefs.add(new ColumnDefinition("dept", "Dept", 2, 50));
        this.colDefs.add(new ColumnDefinition("reasonCd", "RC", 3, 40));
        this.colDefs.add(new ColumnDefinition("status", "Status", 4, 200));

        try {
            return super.createDataGrid(list);
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    this.getTitle(), JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    /**
     * Uses an instance of BIRtvDetailListDialog for opening the BI RTV Header
     * SKU Details List dialog for a given BI RTV Header record.
     * <p>
     * Once the user has made the desired quantity sent changes, this method
     * will obtain the updated sum of the RTN Header's quantity and update the
     * RTV Header model with that sum which in turn will update the view.
     * 
     * @param item
     *            an instance of {@link RtvHeader} used to fetch and manage its
     *            SKU details
     */
    @Override
    protected void openHeaderDetailListWindow(RtvHeader item) {
        // Setup window
        String dialogTitle = CommonRtvFrame.UI_HEAD_DETAIL_TITLE.replaceAll(
                "%s%", "BI");
        BIRtvDetailListDialog dialog = new BIRtvDetailListDialog(this, item,
                new Dimension(675, 330), new Point(740, 400), dialogTitle);
        this.setDetailsDialog(dialog);
        // Execute window using common ancestor logic
        super.openHeaderDetailListWindow(item);

        // Get updated quantiy sum for RTV number
        BIRTVFactory f = new BIRTVFactory();
        RtvDao dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
        try {
            int curRow = this.grid.getSelectedRow();
            int qtySumUpdate = dao.fetchRtvNoQtySum(item.getRtvNo());
            item.setQtySummed(qtySumUpdate);
            this.grid.refreshGrid();
            this.grid.scrollToVisible(curRow);
        } catch (DatabaseException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("Error occured fetching the summed quatntiy for BI RTV Header [");
            buf.append(item.getRtvNo());
            buf.append("] after updating one of its SKU item's sent quantity.");
            buf.append("\n\nPlease contact the support team for assistance.");
            this.msg = buf.toString();
            JOptionPane
                    .showMessageDialog(
                            this,
                            this.msg,
                            "Database Error for BI RTV Header Quantity Summation Operation",
                            JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Updates the status of the SI RTV record to the value of <i>newStatus</i>.
     * <p>
     * Valid statuses that apply to this implementation are <i>Suspend, Repoen,
     * and Close</i>.
     * 
     * @param newStatus
     *            the status that will be applied to the SI RTV record. The
     *            status, Suspended, is the only applicable value for this
     *            method.
     * @return true when the SI RTV Header's status is updated successfully with
     *         <i>Suspend</i>. Otherwise, false is returned and the update
     *         status is aborted.
     */
    @Override
    protected boolean updateHeaderStatus(String newStatus) {
        super.updateHeaderStatus(newStatus);
        RtvHeader item = this.getSelectedHeader();

        // At this point we have a status to change
        int rowIndex = this.grid.getSelectedRow();
        RtvDao dao = null;
        try {
            BIRTVFactory f = new BIRTVFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());

            // Verify that RTV does not have a quantity
            if (newStatus.equalsIgnoreCase(RtvConstants.STATUS_CLOSED)) {
                int count = dao.fetchRtvNoQtySum(item.getRtvNo());
                if (count > 0) {
                    this.msg = "BI RTV has a quantity and is not eligible to zero post";
                    AppUtil.showMessage(this.msgLbl, this.msg, true);
                    throw new RuntimeException(this.msg);
                }
            }

            int rows = dao.updateStatus(item.getRtvNo(), newStatus);

            // Display friendly message
            StringBuffer buf = new StringBuffer();
            buf.append("BI RTV status was changed successfully for ");
            buf.append(item.getRtvNo());
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
                    + "\' for RTV header number, " + item.getRtvNo();
            logger.error(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new RuntimeException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Functions to enable/disable the <i>Print</i> and <i>Suspend/Reopen/Zero
     * Post</i> buttons.
     * <p>
     * If the status of the selecte RTV is "C"(Closes) or "S" (Suspend), the
     * button should be enabled. Otherwise, the button should be disabled.
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        super.handleSelectionChanged(evt);

        RtvHeader item = this.getSelectedHeader();
        String stat = null;

        if (item.getStatus() == null) {
            this.printButton.setEnabled(false);
            this.okButton.setEnabled(false);
            return;
        }

        stat = item.getStatus().trim();
        if (stat.equalsIgnoreCase("I") || stat.equalsIgnoreCase("O")
                || stat.equalsIgnoreCase("Z")) {
            this.printButton.setEnabled(false);
        }

        if (stat.equalsIgnoreCase("O")) {
            this.okButton.setEnabled(true);
            this.okButton.setText("Zero Post");
            this.okButton
                    .setActionCommand(BIRtvFrame.ACTION_COMMAND_STATUS_ZEROPOST);
        }
        else if (stat.equalsIgnoreCase("C") && item.getQtySummed() != null
                && item.getQtySummed() == 0) {
            this.okButton.setEnabled(true);
            this.okButton.setText("Reopen");
            this.okButton
                    .setActionCommand(BIRtvFrame.ACTION_COMMAND_STATUS_REOPEN);
        }
        else if (stat.equalsIgnoreCase("C") && item.getQtySummed() != null
                && item.getQtySummed() != 0) {
            this.okButton.setEnabled(true);
            this.okButton.setText("Suspend");
            this.okButton
                    .setActionCommand(BIRtvFrame.ACTION_COMMAND_STATUS_SUSPEND);
        }
        else {
            this.okButton.setEnabled(false);
            this.okButton.setText("Zero Post");
            this.okButton
                    .setActionCommand(BIRtvFrame.ACTION_COMMAND_STATUS_ZEROPOST);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * modules.rtv.CommonRtvFrame#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        if (e.getActionCommand().equals(
                BIRtvFrame.ACTION_COMMAND_STATUS_ZEROPOST)) {
            this.updateHeaderStatus(RtvConstants.STATUS_CLOSED);
        }
        if (e.getActionCommand()
                .equals(BIRtvFrame.ACTION_COMMAND_STATUS_REOPEN)) {
            this.updateHeaderStatus(RtvConstants.STATUS_OPEN);
        }
    }

}

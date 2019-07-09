package modules.transfer.bi;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modules.LaunchPadException;
import modules.model.Transfer;
import modules.model.TransferSkuItem;
import modules.transfer.CommonTransferDetailListDialog;
import modules.transfer.TransferConstants;
import modules.transfer.TransferDao;

import org.apache.log4j.Logger;

import com.NotFoundException;

import com.nv.db.DatabaseException;
import com.nv.security.UserSecurityManager;
import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * List the SKU item details for a given BI Transfer number.
 * 
 * @author rterrell
 *
 */
public class BITransferDetailListDialog extends CommonTransferDetailListDialog {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(BITransferDetailListDialog.class);

    private Transfer transferModel;

    /**
     * Create a BITransferDetailListDialog object initailized with a parent
     * object, the transfer header model item, the window size, the window
     * postion and window title.
     * 
     * @param owner
     *            the parent window reference
     * @param item
     *            The BI Transfer Header model as an instance of
     *            {@link Transfer}.
     * @param size
     *            The window size coordinates
     * @param pos
     *            The window position coordinates
     * @param winTitle
     *            The window title.
     */
    public BITransferDetailListDialog(Frame owner, Transfer item,
            Dimension size, Point pos, String winTitle) {
        super(owner, item, size, pos, winTitle);
    }

    /**
     * Performs specific initialization tasks for this dialog.
     * <p>
     * The incoming arbitrary data, <i>item</i>, is captured as an instance of
     * {@link Transfer}. Another, initialization task is a row sorter is created
     * for those header columns containing numeric data. Lastly, the transfer
     * number is appended to the window's title.
     */
    @Override
    protected void initDialog() {
        this.transferModel = (Transfer) this.inData;
        super.initDialog();

        // Identify those columns that need to use special comparators for
        // sorting.
        // This is good place for this event since the the grid is setup from
        // the ancestor
        int numericCols[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int dateCols[] = { 10 };

        int numericColsBStat[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
        int dateColsBStat[] = { 9 };

        if (this.transferModel.getStatus().equalsIgnoreCase(
                TransferConstants.STATUS_DETAIL_RECV)) {
            this.grid.setNumericColumnSorter(numericColsBStat);
            this.grid.setDateColumnSorter(dateColsBStat);
        }
        else {
            this.grid.setNumericColumnSorter(numericCols);
            this.grid.setDateColumnSorter(dateCols);
        }

        // Modify window title to include the transfer number.
        String appTitle = this.getTitle();
        appTitle += this.transferModel.getTranferId();
        this.setTitle(appTitle);
    }

    /**
     * Fetches one or more sku items related to a BI transfer number.
     * <p>
     * The transfer number is stored and managed internally at the ancestor.
     * 
     * @return a List of {@link TransferSkuItem} objects
     * @throws DatabaseException
     *             general database errors.
     */
    @Override
    protected List<TransferSkuItem> doQuery() throws DatabaseException {
        List<TransferSkuItem> list = null;
        TransferDao dao = null;
        try {
            BITransferFactory f = new BITransferFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchTransferItems(this.transferModel.getTranferId());
            return list;
        } catch (DatabaseException e) {
            logger.error(
                    "Error fetching BI Transfer SKU items from the database", e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Creates a specific ColumnDefinition stucture that is used to construct
     * and layout the Data Grid component for BI Transfer Details List to be
     * presented in this window.
     * 
     * @param list
     *            a List of {@link TransferSkuItem} objects
     * @return an instance of {@link ScrollableDataGrid}
     * @throws NotFoundException
     *             the column difinition component was invalid or not setup
     *             properly.
     */
    @Override
    protected ScrollableDataGrid createDataGrid(List<TransferSkuItem> list)
            throws NotFoundException {
        // Setup column definitions for SI Transfer header list
        this.colDefs = new ArrayList<ColumnDefinition>();
        colDefs.add(new ColumnDefinition("sSku", "SKU", 0, 125));
        colDefs.add(new ColumnDefinition("dept", "Dept", 1, 50));
        colDefs.add(new ColumnDefinition("clazz", "Class", 2, 50));
        colDefs.add(new ColumnDefinition("vendor", "Vendor", 3, 60));
        colDefs.add(new ColumnDefinition("style", "Style", 4, 60));
        colDefs.add(new ColumnDefinition("color", "Color", 5, 50));
        colDefs.add(new ColumnDefinition("size", "Size", 6, 50));
        colDefs.add(new ColumnDefinition("retailStr", "Price", 7, 80));

        // Dynamically display either quantity sent or recevied based on tranfer
        // status
        if (this.transferModel.getStatus().equalsIgnoreCase(
                TransferConstants.STATUS_DETAIL_RECV)) {
            colDefs.add(new ColumnDefinition("qtyRecv", "Recv", 8, 80));
            colDefs.add(new ColumnDefinition("sendByDateStr", "Send By", 9, 110));
        }
        else {
            colDefs.add(new ColumnDefinition("qtyReq", "Req", 8, 80));
            colDefs.add(new ColumnDefinition("qtySent", "Sent", 9, 80));
            colDefs.add(new ColumnDefinition("sendByDateStr", "Send By", 10,
                    110));
        }

        try {
            return super.createDataGrid(list);
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "BI Transfer Admin", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    /**
     * Opens the Transfer specific Modify Quantity dialog to accept the user
     * input for changing the quantity sent for the specified SKU item,
     * <i>item</i>.
     * <p>
     * Before allowing the user to modify the record, the status of the header
     * is verified in order to determine if it is okay to modify the sent
     * quantiy of a transfer detail record. The applicable statuses that will
     * cause a BI Transfer detail record to be denied are <i>Available</i>,
     * <i>Detail Recevied</i>, and <i>Summary Received</i> in which their codes
     * are, "A", "B", or "R", respectively. When one of these codes are
     * encounterd, an appropriate message is displayed to the user in the form
     * of a popup window. Next, the Quantity Sent edit diaolg will fail to
     * display, and lastly, control is returned to the caller. Otherwise, the
     * user is allowed to edit the quantity sent property of the BI Transfer
     * detail record.
     * 
     * @param item
     *            the sku as an instance of {@link TransferSkuItem}
     * @return the quantity entered by the user, -1 when the transfer header is
     *         still in work, or -2 when the transfer is considered to be
     *         inbound.
     * @throws LaunchPadException
     *             Error during the time of opening or processing the
     *             TransferQtySentDialog dialog.
     */
    @Override
    protected Integer openQtyUpdateDialog(TransferSkuItem item)
            throws LaunchPadException {
        String errorTitle = this.getTitle() + " Error";
        if (this.transferModel.getStatus().equalsIgnoreCase(
                TransferConstants.STATUS_AVAILABLE)) {
            this.msg = "The BI Transfer is still in work, and the sent quantity of the selected detail record cannot be edited at this time.";
            JOptionPane.showMessageDialog(this, this.msg, errorTitle,
                    JOptionPane.ERROR_MESSAGE);
            throw new LaunchPadException(this.msg);
        }
        item.setTransferType(TransferSkuItem.TRANS_TYPE_BI);
        return super.openQtyUpdateDialog(item);
    }

}

package modules.transfer.si;

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
 * List the SKU item details for a given SI Transfer number.
 * 
 * @author rterrell
 *
 */
public class SITransferDetailListDialog extends CommonTransferDetailListDialog {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(SITransferDetailListDialog.class);

    private Transfer transferModel;

    /**
     * Create a SITransferDetailListDialog object initailized with a parent
     * object, the transfer header model item, the window size, the window
     * postion and window title.
     * 
     * @param owner
     *            the parent window reference
     * @param item
     *            The SI Transfer Header model as an instance of
     *            {@link Transfer}.
     * @param size
     *            The window size coordinates
     * @param pos
     *            The window position coordinates
     * @param winTitle
     *            The window title.
     */
    public SITransferDetailListDialog(Frame owner, Transfer item,
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
        int numericCols[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
        this.grid.setNumericColumnSorter(numericCols);

        // Modify window title to include the transfer number.
        String appTitle = this.getTitle();
        appTitle += this.transferModel.getTranferId();
        this.setTitle(appTitle);
    }

    /**
     * Fetches one or more sku items related to a SI transfer number.
     * <p>
     * The SI transfer number is stored and managed internally at the ancestor.
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
            SITransferFactory f = new SITransferFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchTransferItems(this.transferModel.getTranferId());
            return list;
        } catch (DatabaseException e) {
            logger.error(
                    "Error fetching SI Transfer SKU items from the database", e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Creates a SI specific ColumnDefinition stucture that is used to construct
     * and layout the Data Grid component for SI Transfer Details List to be
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
        colDefs.add(new ColumnDefinition("sSku", "SKU", 0, 120));
        colDefs.add(new ColumnDefinition("dept", "Dept", 1, 50));
        colDefs.add(new ColumnDefinition("clazz", "Class", 2, 50));
        colDefs.add(new ColumnDefinition("vendor", "Vendor", 3, 60));
        colDefs.add(new ColumnDefinition("style", "Style", 4, 60));
        colDefs.add(new ColumnDefinition("color", "Color", 5, 50));
        colDefs.add(new ColumnDefinition("size", "Size", 6, 50));
        colDefs.add(new ColumnDefinition("retailStr", "Price", 7, 50));

        // Dynamically display either quantity sent or recevied based on tranfer
        // status
        if (this.transferModel.getStatus().equalsIgnoreCase(
                TransferConstants.STATUS_DETAIL_RECV)) {
            colDefs.add(new ColumnDefinition("qtyRecv", "Qty Recv", 8, 80));
        }
        else {
            colDefs.add(new ColumnDefinition("qtySent", "Qty Sent", 8, 80));
        }

        try {
            return super.createDataGrid(list);
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "SI Transfer Admin", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * modules.transfer.CommonTransferDetailListDialog#openQtySentUpdateDialog
     * (modules.model.TransferSkuItem)
     */
    @Override
    protected Integer openQtyUpdateDialog(TransferSkuItem item)
            throws LaunchPadException {
        item.setTransferType(TransferSkuItem.TRANS_TYPE_SI);
        return super.openQtyUpdateDialog(item);
    }

}

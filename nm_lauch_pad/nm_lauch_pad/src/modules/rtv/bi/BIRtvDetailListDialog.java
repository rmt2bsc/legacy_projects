package modules.rtv.bi;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modules.model.RtvHeader;
import modules.model.RtvItem;

import modules.rtv.CommonRtvDetailListDialog;
import modules.rtv.RtvConstants;
import modules.rtv.RtvDao;

import org.apache.log4j.Logger;

import com.NotFoundException;

import com.nv.db.DatabaseException;

import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;

import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * List the SKU item details for a given BI RTV header record.
 * 
 * @author rterrell
 *
 */
public class BIRtvDetailListDialog extends CommonRtvDetailListDialog {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(BIRtvDetailListDialog.class);

    private RtvHeader headerModel;

    // private int qtySumUpdate;

    /**
     * Create a BIRtvDetailListDialog object initailized with a parent object,
     * the price change model item, the window size, the window postion and
     * window title.
     * 
     * @param owner
     *            the parent window reference
     * @param item
     *            The RTV Header model as an instance of
     *            {@link modules.model.RtvHeader}
     * @param size
     *            The window size coordinates
     * @param pos
     *            The window position coordinates
     * @param winTitle
     *            The window title.
     */
    public BIRtvDetailListDialog(Frame owner, RtvHeader item, Dimension size,
            Point pos, String winTitle) {
        super(owner, item, size, pos, winTitle);
    }

    /**
     * Performs specific initialization tasks for this dialog.
     * <p>
     * The incoming arbitrary data, <i>item</i>, is captured as an instance of
     * {@link RtvHeader}. Another, initialization task is a row sorter is
     * created for those header columns containing numeric data. Lastly, the
     * transfer number is appended to the window's title.
     */
    @Override
    protected void initDialog() {
        this.headerModel = (RtvHeader) this.inData;
        super.initDialog();

        // Identify those columns that need to use special comparators for
        // sorting.
        // This is good place for this event since the the grid is setup from
        // the ancestor
        int numericCols[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        this.grid.setNumericColumnSorter(numericCols);

        // Modify window title to include the transfer number.
        String appTitle = this.getTitle();
        appTitle += this.headerModel.getRtvNo();
        this.setTitle(appTitle);
    }

    /**
     * Fetches one or more sku items related to a BI RTV Header record.
     * <p>
     * The SI RTV number is stored and managed internally at the ancestor.
     * 
     * @return a List of {@link RtvItem} objects
     * @throws DatabaseException
     *             general database errors.
     */
    @Override
    protected List<RtvItem> doQuery() throws DatabaseException {
        List<RtvItem> list = null;
        RtvDao dao = null;
        try {
            BIRTVFactory f = new BIRTVFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchItems(this.headerModel.getRtvNo());
            return list;
        } catch (DatabaseException e) {
            logger.error("Error fetching BI RTV SKU items from the database", e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Creates a BI specific ColumnDefinition stucture that is used to construct
     * and layout the Data Grid component for SI RTV Details List to be
     * presented in this window.
     * 
     * @param list
     *            a List of {@link RtvItem} objects
     * @return an instance of {@link ScrollableDataGrid}
     * @throws NotFoundException
     *             the column difinition component was invalid or not setup
     *             properly.
     */
    @Override
    protected ScrollableDataGrid createDataGrid(List<RtvItem> list)
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
        colDefs.add(new ColumnDefinition("origRetailStr", "Price", 7, 80));
        colDefs.add(new ColumnDefinition("qty", "Qty Sent", 8, 80));
        colDefs.add(new ColumnDefinition("expectedQty", "Qty Reqd", 9, 80));

        try {
            return super.createDataGrid(list);
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "BI RTV Admin",
                    JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    /**
     * Verifies if it is okay to update the quantity for the selected BI RTV
     * detail record.
     * <p>
     * An error message is displayed via a popup window explaining that the user
     * is required to reoopen the RTV before changing the quantity.
     * 
     * @param evt
     *            an instance of {@link ComponentItemDoubleClickedEvent}
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        String stat = this.headerModel.getStatus().trim();
        if (stat.equalsIgnoreCase(RtvConstants.STATUS_CLOSED)) {
            this.msg = "BI RTV has been zero posted, must re-open to modify quantity";
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new RuntimeException(this.msg);
        }
        super.handleDoubleClickedRow(evt);
    }

}

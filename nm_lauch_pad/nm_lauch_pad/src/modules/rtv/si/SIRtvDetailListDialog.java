package modules.rtv.si;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modules.model.RtvHeader;
import modules.model.RtvItem;

import modules.rtv.CommonRtvDetailListDialog;
import modules.rtv.RtvDao;

import org.apache.log4j.Logger;

import com.NotFoundException;

import com.nv.db.DatabaseException;

import com.nv.security.UserSecurityManager;

import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * List the SKU item details for a given SI RTV header record.
 * 
 * @author rterrell
 *
 */
public class SIRtvDetailListDialog extends CommonRtvDetailListDialog {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(SIRtvDetailListDialog.class);

    private RtvHeader headerModel;

    /**
     * Create a SIRtvDetailListDialog object initailized with a parent object,
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
    public SIRtvDetailListDialog(Frame owner, RtvHeader item, Dimension size,
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
        int numericCols[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
        this.grid.setNumericColumnSorter(numericCols);

        // Modify window title to include the transfer number.
        String appTitle = this.getTitle();
        appTitle += this.headerModel.getRtvNo();
        this.setTitle(appTitle);
    }

    /**
     * Fetches one or more sku items related to a SI RTV Header record.
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
            SIRTVFactory f = new SIRTVFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchItems(this.headerModel.getRtvNo());
            return list;
        } catch (DatabaseException e) {
            logger.error("Error fetching SI RTV SKU items from the database", e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Creates a SI specific ColumnDefinition stucture that is used to construct
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
        colDefs.add(new ColumnDefinition("origRetailStr", "Price", 7, 50));
        colDefs.add(new ColumnDefinition("qty", "Qty Sent", 8, 80));

        try {
            return super.createDataGrid(list);
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "SI RTV Admin",
                    JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

}

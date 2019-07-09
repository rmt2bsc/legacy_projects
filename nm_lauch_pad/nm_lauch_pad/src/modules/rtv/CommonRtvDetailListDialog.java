package modules.rtv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import modules.LaunchPadException;

import modules.model.RtvHeader;
import modules.model.RtvItem;

import modules.rtv.bi.BIRTVFactory;

import modules.rtv.si.SIRTVFactory;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import com.InvalidDataException;
import com.NotFoundException;

import com.nv.db.DatabaseException;

import com.nv.security.UserSecurityManager;

import com.nv.util.AppUtil;

import com.ui.AbstractModalWindow;

import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.event.ComponentItemSelectedEvent;
import com.ui.event.ComponentSelectionListener;

import com.ui.table.ColumnDefinition;
import com.ui.table.DataGrid;
import com.ui.table.ScrollableDataGrid;

/**
 * A common dialog for listing the SKU Item details for a related RTV header
 * record.
 * <p>
 * Also, this dialog allows the user to change the quantity sent for a selected
 * SKU item.
 * 
 * @author rterrell
 *
 */
public class CommonRtvDetailListDialog extends AbstractModalWindow implements
        ComponentSelectionListener {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(CommonRtvDetailListDialog.class);

    private RtvHeader header;

    private int currentRow;

    /**
     * The data grid component.
     */
    protected ScrollableDataGrid grid;

    /**
     * The data grid's column definition component.
     */
    protected List<ColumnDefinition> colDefs;

    /**
     * Create a CommonRtvDetailListDialog object initailized with a parent
     * object, the RTV Header model, the window size, the window postion and
     * window title.
     * 
     * @param owner
     *            the parent window reference
     * @param item
     *            The RTV Header record as an instance of {@link RtvHeader}
     * @param size
     *            The window size coordinates
     * @param pos
     *            The window position coordinates
     * @param winTitle
     *            The window title.
     */
    public CommonRtvDetailListDialog(Frame owner, RtvHeader item,
            Dimension size, Point pos, String winTitle) {
        super(owner, item, size, pos, winTitle);
        this.header = item;
    }

    /**
     * Removes the ancestor "OK" button and changes the text of the cancel
     * button to "Exit Detail List".
     * <p>
     * The default ancestor intialization logic is performed as well.
     */
    @Override
    protected void initDialog() {
        super.initDialog();
        this.okButton.setVisible(false);
        this.cancelButton.setText("Exit Detail List");
    }

    /**
     * Stub method for performing the query to obtain data for the data grid.
     * <p>
     * The descendent should provide logic to statisfy specific data retrieval
     * requirements for this dialog.
     * 
     * @return always returns null
     * @throws DatabaseException
     */
    protected List<RtvItem> doQuery() throws DatabaseException {
        return null;
    }

    /**
     * Creates the structure of the ScrollableDataGrid based on the List of data
     * items that are of the datatype, RtvItem.
     * <p>
     * The column definitions are required to be setup at the descendant object.
     * 
     * @param list
     *            a List of {@link RtvItem} objects
     * @return an instance of {@link ScrollableDataGrid}
     * @throws NotFoundException
     *             the column difinition component was invalid or not setup
     *             properly.
     */
    protected ScrollableDataGrid createDataGrid(List<RtvItem> list)
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
        List<RtvItem> list;
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
     * Stub method.
     * 
     * @return always returns null
     * @throws InvalidDataException
     *             N/A
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        return null;
    }

    /**
     * Stub method.
     * 
     * @param data
     *            an instance of {@link RtvHeader} for the descendant to
     *            manipulate.
     * @return always returns null
     * @throws InvalidDataException
     *             N/A
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        return null;
    }

    /**
     * Inovkes the handler for updating the quantity sent for a RTV Header's sku
     * item.
     * 
     * @param evt
     *            an instance of {@link ComponentItemDoubleClickedEvent}
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        RtvItem item = (RtvItem) evt.getSelectedItem();
        try {
            DataGrid t = (DataGrid) evt.getSource();
            this.currentRow = t.getSelectedRow();
            this.updateSkuQtySent(item);
        } catch (LaunchPadException e) {
            logger.error(e);
        }
    }

    /**
     * Stub method
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        return;
    }

    /**
     * Updates the quantity sent for a RTV SKU item.
     * <p>
     * A dialog is used to obtain the user input of the quatnity sent value.
     * Subsequently, the input value is used to update the sku items quantity
     * sent for the selected header.
     * 
     * @param item
     *            an instance of {@link RtvItem}
     */
    protected void updateSkuQtySent(RtvItem item) throws LaunchPadException {
        Integer qty = 0;
        try {
            qty = this.openQtySentUpdateDialog(item);
        } catch (LaunchPadException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        // The user cancelled the diaolog if qty is null
        if (qty == null) {
            return;
        }

        // Apply update to the database with the data we've gathered thus far.
        RtvDao dao = null;
        int rowsModified = 0;
        try {
            if (this.header.getRtvType().equalsIgnoreCase(
                    RtvConstants.RTV_TYPE_SI)) {
                SIRTVFactory f = new SIRTVFactory();
                dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            }
            else if (this.header.getRtvType().equalsIgnoreCase(
                    RtvConstants.RTV_TYPE_BI)) {
                BIRTVFactory f = new BIRTVFactory();
                dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            }
            else {
                StringBuffer buf = new StringBuffer();
                buf.append("Unable to update RTV [");
                buf.append(item.getRtvNo());
                buf.append("] SKU Detail record [");
                buf.append(item.getsSku());
                buf.append("].  Could not determine whether header type is SI or BI.");
                buf.append("\n\nPlease contact the support team for assistance.");
                this.msg = buf.toString();
                JOptionPane
                        .showMessageDialog(
                                this,
                                this.msg,
                                "Database Error for Transfer SKU Detail Update Operation",
                                JOptionPane.ERROR_MESSAGE);
                throw new LaunchPadException(this.msg);
            }

            rowsModified = dao.updateSkuITem(item, qty.intValue());
            logger.info("Total rows effected by RTV SKU quantity sent update operation: "
                    + rowsModified);
            this.msg = "The quantity sent for SKU, " + item.getsSku()
                    + ", was successfully updated to the amount of " + qty;
            logger.info(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, false);
        } catch (DatabaseException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("Error occured applying SKU quantity sent update for SKU [");
            buf.append(item.getsSku());
            buf.append("] of RTV {");
            buf.append(this.header.getRtvNo());
            buf.append("].\n\nPlease contact the support team for assistance.");
            this.msg = buf.toString();
            JOptionPane
                    .showMessageDialog(
                            this,
                            this.msg,
                            "Database Error for RTV SKU Quantity Sent Update Operation",
                            JOptionPane.ERROR_MESSAGE);
            throw new LaunchPadException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
        }

        // Update the model with new quantity
        item.setQty(qty);
        this.grid.refreshGrid();
        this.grid.scrollToVisible(this.currentRow);
    }

    /**
     * Opens the RTV specific Modify Quantity dialog to accept the user input
     * for changing the quantity sent for the specified SKU item, <i>item</i>.
     * 
     * @param item
     *            the sku as an instance of {@link RtvItem}
     * @return the quantity entered by the user
     * @throws LaunchPadException
     *             Error during the time of opening or processing the
     *             TransferQtySentDialog dialog.
     */
    protected Integer openQtySentUpdateDialog(RtvItem item)
            throws LaunchPadException {
        item.getSku();
        String sku = item.getLongSku();
        String windowTitle = "Modify Quantity for Item: " + sku;
        String inputFieldLabel = "Quantity Sent:";
        try {
            RtvQtySentDialog dialog = new RtvQtySentDialog(this, new Dimension(
                    430, 150), new Point(800, 500), windowTitle,
                    inputFieldLabel, item.getQty());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            return dialog.getQty();
        } catch (Exception e) {
            this.msg = "An error occured opening the Modify Quantity Dialog window for a RTV.\n\nPlease consult the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg,
                    "Modify RTV Item Quatnity Input Dialog Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg, e);
            throw new LaunchPadException(e);
        }
    }

    /**
     * Displays the total number of detail records fetched from the database in
     * the message area for a header header records.
     */
    @Override
    protected void displayRecordCount() {
        super.displayRecordCount();

        // Dispaly record count
        this.msg = this.grid.getTableView().getRowCount() + " records found";
        AppUtil.showMessage(this.msgLbl, this.msg, false);
    }

}

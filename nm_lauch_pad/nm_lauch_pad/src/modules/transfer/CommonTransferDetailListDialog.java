package modules.transfer;

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
import modules.model.Transfer;
import modules.model.TransferSkuItem;
import modules.transfer.bi.BITransferFactory;
import modules.transfer.si.SITransferFactory;
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
 * A common dialog for listing the SKU Item details for a related Transfer
 * header record.
 * <p>
 * Also, this dialog allows the user to change the quantity sent for a selected
 * SKU item.
 * 
 * @author rterrell
 *
 */
public class CommonTransferDetailListDialog extends AbstractModalWindow
        implements ComponentSelectionListener {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(CommonTransferDetailListDialog.class);

    private Transfer transfer;

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
     * Create a CommonTransferDetailListDialog object initailized with a parent
     * object, the price change model transfer, the window size, the window
     * postion and window title.
     * 
     * @param owner
     *            the parent window reference
     * @param item
     *            The Transfer model as an instance of {@link Transfer}
     * @param size
     *            The window size coordinates
     * @param pos
     *            The window position coordinates
     * @param winTitle
     *            The window title.
     */
    public CommonTransferDetailListDialog(Frame owner, Transfer item,
            Dimension size, Point pos, String winTitle) {
        super(owner, item, size, pos, winTitle);
        this.transfer = item;
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
    protected List<TransferSkuItem> doQuery() throws DatabaseException {
        return null;
    }

    /**
     * Creates the structure of the ScrollableDataGrid based on the List of data
     * items that are of the datatype, TransferSkuItem.
     * <p>
     * The column definitions are required to be setup at the descendant object.
     * 
     * @param list
     *            a List of {@link TransferSkuItem} objects
     * @return an instance of {@link ScrollableDataGrid}
     * @throws NotFoundException
     *             the column difinition component was invalid or not setup
     *             properly.
     */
    protected ScrollableDataGrid createDataGrid(List<TransferSkuItem> list)
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
        List<TransferSkuItem> list;
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
     *            an instance of {@link Transfer} containing the header data to
     *            be applied to the database as an update.
     * @return always returns null
     * @throws InvalidDataException
     *             N/A
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        return null;
    }

    /**
     * Inovkes the handler for updating the quantity sent for a transfer's sku
     * item.
     * 
     * @param evt
     *            an instance of {@link ComponentItemDoubleClickedEvent}
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        TransferSkuItem item = (TransferSkuItem) evt.getSelectedItem();
        try {
            DataGrid t = (DataGrid) evt.getSource();
            this.currentRow = t.getSelectedRow();
            this.updateSkuQty(item);
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
     * Updates the quantity for a Transfer's SKU transfer.
     * <p>
     * A dialog is used to obtain the user input of the quatnity value.
     * Subsequently, the input value is used to update the sku items quantity
     * for the selected transfer. For SI transfers, the quantity updated is
     * quantity received. Quantity sent is updated for BI transfers.
     * 
     * @param item
     *            an instance of {@link TransferSkuItem}
     */
    protected void updateSkuQty(TransferSkuItem item) throws LaunchPadException {
        Integer qty = 0;
        try {
            qty = this.openQtyUpdateDialog(item);
        } catch (LaunchPadException e) {
            logger.error("Unable to update quantity sent value", e);
            throw new RuntimeException(e);
        }

        // The user cancelled the diaolog if qty is null
        if (qty == null) {
            return;
        }

        // Apply update to the database with the data we've gathered thus far.
        TransferDao dao = null;
        int rowsModified = 0;
        try {
            if (this.transfer.getTranferType() == TransferConstants.XFER_TYPE_SI) {
                SITransferFactory f = new SITransferFactory();
                dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            }
            else if (this.transfer.getTranferType() == TransferConstants.XFER_TYPE_BI) {
                BITransferFactory f = new BITransferFactory();
                dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            }
            else {
                StringBuffer buf = new StringBuffer();
                buf.append("Unable to update Transfer [");
                buf.append(item.getTranferId());
                buf.append("] SKU Detail record [");
                buf.append(item.getsSku());
                buf.append("].  Could not determine whether transfer type is SI or BI.");
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

            rowsModified = dao.updateTransferItemQty(
                    this.transfer.getTranferId(), this.transfer.getStatus(),
                    item.getSku(), item.getOverFlowSku(), qty.intValue());
            if (this.transfer.getStatus().equalsIgnoreCase(
                    TransferConstants.STATUS_DETAIL_RECV)) {
                logger.info("Total rows effected by updating the quantity received of the selected Transfer SKU: "
                        + rowsModified);
                this.msg = "The quantity received for SKU, " + item.getsSku()
                        + ", was successfully updated to the amount of " + qty;
                item.setQtyRecv(qty);
            }
            else {
                logger.info("Total rows effected by updating the quantity sent of the selected Transfer SKU: "
                        + rowsModified);
                this.msg = "The quantity sent for SKU, " + item.getsSku()
                        + ", was successfully updated to the amount of " + qty;
                item.setQtySent(qty);
            }

            logger.info(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, false);
        } catch (DatabaseException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("Error occured applying SKU quantity sent update for SKU [");
            buf.append(item.getsSku());
            buf.append("] of Transfer {");
            buf.append(this.transfer.getTranferId());
            buf.append("].\n\nPlease contact the support team for assistance.");
            this.msg = buf.toString();
            JOptionPane
                    .showMessageDialog(
                            this,
                            this.msg,
                            "Database Error for Transfer SKU Quantity Sent Update Operation",
                            JOptionPane.ERROR_MESSAGE);
            throw new LaunchPadException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
        }

        // Update the model with new quantity
        this.grid.refreshGrid();
        this.grid.scrollToVisible(this.currentRow);
    }

    /**
     * Opens the Transfer specific Modify Quantity dialog to accept the user
     * input for changing the quantity of a specified SKU item, <i>item</i>,
     * regarding the BI or SI transfer.
     * 
     * @param item
     *            the sku as an instance of {@link TransferSkuItem}
     * @return the quantity entered by the user
     * @throws LaunchPadException
     *             Error during the time of opening or processing the
     *             TransferQtySentDialog dialog.
     */
    protected Integer openQtyUpdateDialog(TransferSkuItem item)
            throws LaunchPadException {
        String windowTitle = "Modify Quantity for Item " + item.getsSku();
        String inputFieldLabel = "Quantity ";
        Integer qty = null;
        if (this.transfer.getStatus().equalsIgnoreCase(
                TransferConstants.STATUS_DETAIL_RECV)) {
            inputFieldLabel += "Received:";
            qty = item.getQtyRecv();
        }
        else {
            inputFieldLabel += "Sent:";
            qty = item.getQtySent();
        }

        try {
            TransferQtySentDialog dialog = new TransferQtySentDialog(this,
                    new Dimension(370, 150), new Point(800, 500), windowTitle,
                    inputFieldLabel, qty);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            return dialog.getQty();
        } catch (Exception e) {
            this.msg = "An error occured opening the Modify Quantity Dialog window for a Transfer.\n\nPlease consult the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg,
                    "Modify Transfer Item Quatnity Input Dialog Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg, e);
            throw new LaunchPadException(e);
        }
    }

    /**
     * Displays the total number of detail records fetched from the database in
     * the message area for a transfer header records.
     */
    @Override
    protected void displayRecordCount() {
        super.displayRecordCount();

        // Dispaly record count
        this.msg = this.grid.getTableView().getRowCount() + " records found";
        AppUtil.showMessage(this.msgLbl, this.msg, false);
    }

}

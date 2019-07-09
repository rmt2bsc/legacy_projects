package modules.priceaudit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import modules.LaunchPadException;
import modules.model.PriceAuditSkuItem;
import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import com.InvalidDataException;
import com.nv.db.DatabaseException;
import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;
import com.ui.AbstractModelessWindow;
import com.ui.WindowProcessTemplateListener;
import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.event.ComponentItemSelectedEvent;
import com.ui.event.ComponentSelectionListener;
import com.ui.table.ColumnDefinition;
import com.ui.table.DataGrid;
import com.ui.table.ScrollableDataGrid;

/**
 * The main window frame for managaging Price Audit data that is stored in the
 * audit_item table.
 * 
 * @author rterrell
 *
 */
public class PriceAuditFrame extends AbstractModelessWindow implements
        ComponentSelectionListener {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(PriceAuditFrame.class);

    private int currentRow;

    /**
     * The action command name for the "Refresh List" button
     */
    public static final String ACTION_COMMAND_REFRESH = "REFRESH_LIST";

    private JButton refreshButton;

    private ScrollableDataGrid grid;

    private PriceAuditSkuItem selectedItem;

    /**
     * Create a PriceAuditFrame specifying the its size, position and window
     * title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public PriceAuditFrame(Dimension size, Point pos, String title) {
        super(size, pos, title);
    }

    /**
     * Performs specific constructor related tasks for this window.
     * <p>
     * Changes the text of the cancel and Ok buttons to "Exit PC List" and
     * "Close PC", respectively. Adds the refresh button and dht find sku button
     * and arranges the order which the buttons are displayed.
     */
    @Override
    protected void initFrame() {
        // Proceed with frame initialization
        super.initFrame();
        this.cancelButton.setText("Exit List");

        // Rename the label and set the command action name of the OK button.
        this.okButton.setText("Delete Entry");
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);
        this.okButton.setEnabled(false);

        this.refreshButton = new JButton("Refresh List");
        this.refreshButton
                .setActionCommand(PriceAuditFrame.ACTION_COMMAND_REFRESH);
        this.refreshButton.addActionListener(this);
        this.buttonPane.add(this.refreshButton);

        // Resequence the z-indexes of the buttons
        try {
            this.buttonPane.getComponentCount();
            this.buttonPane.setComponentZOrder(this.okButton, 0);
            this.buttonPane.setComponentZOrder(this.refreshButton, 1);
            this.buttonPane.setComponentZOrder(this.cancelButton, 2);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Fetches the active price audit data from the database.
     * 
     * @return a List of {@link PriceAuditSkuItem} objects
     * @throws DatabaseException
     */
    private List<PriceAuditSkuItem> doActivePriceAuditQuery()
            throws DatabaseException {
        List<PriceAuditSkuItem> list = null;
        PriceAuditDao dao = null;
        try {
            PriceAuditFactory f = new PriceAuditFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchAllActive();
            return list;
        } catch (DatabaseException e) {
            logger.error(
                    "Error fetching Active Price Audit list from the database",
                    e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Creates the structure of the ScrollableDataGrid based on the List of data
     * items representing the grid data to be displayed.
     * 
     * @param list
     *            the data to be displayed.
     * @return an instance of {@link ScrollableDataGrid}
     */
    private ScrollableDataGrid createDataGrid(List<PriceAuditSkuItem> list) {

        // Setup column definitions
        List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
        colDefs.add(new ColumnDefinition("sSku", "SKU", 0, 120));
        colDefs.add(new ColumnDefinition("dept", "Dept", 1, 50));
        colDefs.add(new ColumnDefinition("clazz", "Class", 2, 50));
        colDefs.add(new ColumnDefinition("vendor", "Vendor", 3, 60));
        colDefs.add(new ColumnDefinition("style", "Style", 4, 60));
        colDefs.add(new ColumnDefinition("color", "Color", 5, 50));
        colDefs.add(new ColumnDefinition("size", "Size", 6, 50));
        colDefs.add(new ColumnDefinition("oldRetailStr", "Old Price", 7, 90));
        colDefs.add(new ColumnDefinition("newRetailStr", "New Price", 8, 90));
        colDefs.add(new ColumnDefinition("markQty", "Qty", 9, 60));

        // Set the size of the data grid component
        Dimension size = new Dimension(750, 430);
        // Create ScrollableDataGrid grid component
        ScrollableDataGrid dg = new ScrollableDataGrid(list, colDefs,
                ListSelectionModel.SINGLE_SELECTION, size);
        dg.addItemSelectionListener(this);
        return dg;
    }

    /**
     * Uses the MigLayout to add the controls needed for this window.
     * 
     */
    @Override
    public JPanel createContentLayout() {
        // Get active price change info from the database
        List<PriceAuditSkuItem> list;
        try {
            list = this.doActivePriceAuditQuery();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        // Build grid with PC data
        this.grid = this.createDataGrid(list);

        // Identify those columns that need to use special comparators for
        // sorting
        int numericCols[] = { 0, 1, 2, 3, 4, 5, 6, 8, 9 };
        this.grid.setNumericColumnSorter(numericCols);

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
     * Implemented as a stub method.
     * 
     * @return always null
     * @throws InvalidDataException
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        return JOptionPane.showConfirmDialog(this,
                "Delete current row.  Are you sure?", "Price Audit Delete",
                JOptionPane.YES_NO_OPTION);
    }

    /**
     * Implement as a stub method.
     * 
     * @param data
     *            N/A
     * @return always null
     * @throws LaunchPadException
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        int answer = (Integer) data;
        if (answer == JOptionPane.NO_OPTION) {
            return -1;
        }

        int selectedRow = this.grid.getSelectedRow();
        PriceAuditDao dao = null;
        try {
            PriceAuditFactory f = new PriceAuditFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            int rc = dao.deleteItem(this.selectedItem);
            this.grid.removeGridRow(selectedRow);
            return rc;
        } catch (DatabaseException e) {
            this.msg = "Unable to delete selected price audit row";
            logger.error(this.msg, e);
            throw new LaunchPadException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Handles the click actions of the <i>Refresh List</i>, <i>Find Sku</i>,
     * <i>Open/Close PC</i> and <i>Exit PC List</i> buttons.
     * 
     * @param e
     *            instance of {@link ActionEvent}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getActionCommand().equals(PriceAuditFrame.ACTION_COMMAND_REFRESH)) {
            this.refreshDataGrid(null);
        }
    }

    /**
     * Refreshes the price audit grid with the most recent data changes from the
     * database
     * 
     * @throws RuntimeException
     *             Genereal database access errors.
     */
    protected void refreshDataGrid(String sku) {
        try {
            // Get active price change info from the database
            List<PriceAuditSkuItem> list = this.doActivePriceAuditQuery();
            // reload grid with new PC data list
            this.grid.loadView(list);
            this.displayRecordCount();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays the total price change records fetched from the database in the
     * message area.
     */
    @Override
    protected void displayRecordCount() {
        super.displayRecordCount();

        // Dispaly record count
        this.msg = this.grid.getTableView().getRowCount() + " records found";
        AppUtil.showMessage(this.msgLbl, this.msg, false);
    }

    /**
     * Impementation contains no functionality
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        PriceAuditSkuItem item = (PriceAuditSkuItem) evt.getSelectedItem();
        try {
            DataGrid t = (DataGrid) evt.getSource();
            this.currentRow = t.getSelectedRow();
            this.updateMarkedQty(item);
        } catch (LaunchPadException e) {
            logger.error(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ui.event.ComponentSelectionListener#handleSelectionChanged(com.ui
     * .event.ComponentItemSelectedEvent)
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        this.selectedItem = (PriceAuditSkuItem) evt.getSelectedItem();
        this.okButton.setEnabled(true);
        return;
    }

    /**
     * Updates the marked quantity for a Price Audit record.
     * <p>
     * A dialog is used to obtain the user input of the marked quatnity value.
     * Subsequently, the input value is used to update the mark quantity for the
     * selected price audit.
     * 
     * @param item
     *            an instance of {@link PriceAuditSkuItem}
     */
    protected void updateMarkedQty(PriceAuditSkuItem item)
            throws LaunchPadException {
        Integer qty = 0;
        try {
            qty = this.openMarkedQtyUpdateDialog(item);
        } catch (LaunchPadException e) {
            logger.error("Unable to update marked quantity value", e);
            throw new RuntimeException(e);
        }

        // The user cancelled the diaolog if qty is null
        if (qty == null) {
            return;
        }

        // Apply update to the database with the data we've gathered thus far.
        PriceAuditDao dao = null;
        int rowsModified = 0;
        try {
            PriceAuditFactory f = new PriceAuditFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            item.setMarkQty(qty);
            rowsModified = dao.updateItem(item);
            logger.info("Total rows effected by Price Audit marked quantity update operation: "
                    + rowsModified);
            this.msg = "The marked quantity for Price Audit SKU, "
                    + item.getsSku()
                    + ", was successfully updated to the amount of " + qty;
            logger.info(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, false);
        } catch (DatabaseException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("Error occured applying Price Audit marked quantity update for SKU [");
            buf.append(item.getsSku());
            buf.append("].\n\nPlease contact the support team for assistance.");
            this.msg = buf.toString();
            JOptionPane
                    .showMessageDialog(
                            this,
                            this.msg,
                            "Database Error for Price Audit marked quantity update operation",
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
     * Opens the Price Audit specific Modify Marked Quantity dialog to accept
     * the user input for changing the marked quantity for the specified SKU
     * item, <i>item</i>.
     * 
     * @param item
     *            the sku as an instance of {@link PriceAuditSkuItem}
     * @return the quantity entered by the user
     * @throws LaunchPadException
     *             Error during the time of opening or processing the
     *             PriceAuditMarkedQtyDialog dialog.
     */
    protected Integer openMarkedQtyUpdateDialog(PriceAuditSkuItem item)
            throws LaunchPadException {
        String windowTitle = "Modify Quantity for Item " + item.getsSku();
        String inputFieldLabel = "Marked Quantity:";
        try {
            PriceAuditMarkedQtyDialog dialog = new PriceAuditMarkedQtyDialog(
                    this, new Dimension(370, 150), new Point(800, 500),
                    windowTitle, inputFieldLabel, item.getMarkQty());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            return dialog.getQty();
        } catch (Exception e) {
            this.msg = "An error occured opening the Modify Marked Quantity Dialog window for a Price Audit.\n\nPlease consult the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg,
                    "Modify Marked Quatnity Input Dialog Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg, e);
            throw new LaunchPadException(e);
        }
    }

}

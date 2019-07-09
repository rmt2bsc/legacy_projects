package modules.pricechange;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import modules.GenericQtyInputDialog;
import modules.LaunchPadException;
import modules.model.PriceChange;
import modules.model.PriceChangeSkuItem;
import modules.model.SkuItem;
import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import com.InvalidDataException;

import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;
import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;
import com.ui.AbstractModalWindow;
import com.ui.WindowProcessTemplateListener;
import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.event.ComponentItemSelectedEvent;
import com.ui.event.ComponentSelectionListener;
import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * A Dialog component for displaying and modifying one or more SKUs (Lines)
 * related to a given Price Change Number.
 * <p>
 * Modifying a SKU's marked quantiy, closing an open SKU, and opening a closed
 * SKU are the most significant functions of this dialog.
 * 
 * @author rterrell
 *
 */
public class PriceChangeDetailEditDialog extends AbstractModalWindow implements
        ComponentSelectionListener {

    private static final long serialVersionUID = 2240204949920003193L;

    private static final Logger logger = Logger
            .getLogger(PriceChangeDetailEditDialog.class);

    private static final String BUTTON_TEXT_CLOSE = " Close Line(s)";

    private static final String BUTTON_TEXT_OPEN = " Open Line(s)";

    private static final String BUTTON_TEXT_MODIFY = "Modify Qty";

    /**
     * The action command name for the "Open Line(s)" button
     */
    public static final String ACTION_COMMAND_OPEN_ITEM = "OPEN_LINE";

    /**
     * The action command name for the "Close Line(s)" button
     */
    public static final String ACTION_COMMAND_CLOSE_ITEM = "CLOSE_LINE";

    /**
     * The action command name for the "Refresh List" button
     */
    public static final String ACTION_COMMAND_REFRESH = "REFRESH_LIST";

    private PriceChange pcItem;

    private PriceChangeSkuItem currentDtlItem;

    private JButton refreshButton;

    private JButton statusButton;

    private ScrollableDataGrid grid;

    /**
     * Create a PriceChangeDetailEditDialog object initailized with a parent
     * object, the price change model item, the window size, the window postion
     * and window title.
     * 
     * @param owner
     *            the parent window reference
     * @param pcItem
     *            The price change model containing the data to be updated.
     * @param size
     *            The window size coordinates
     * @param pos
     *            The window position coordinates
     * @param winTitle
     *            The window title.
     */
    public PriceChangeDetailEditDialog(Frame owner, PriceChange pcItem,
            Dimension size, Point pos, String winTitle) {
        super(owner, pcItem, size, pos, winTitle);
    }

    /**
     * Performs specific constructor related tasks for this window.
     * <p>
     * Changes the text of the cancel and Ok buttons to "Exit Detail List" and
     * "Close Line(s)", respectively,. Adds the "Refresh List" and "Modify Qty"
     * buttons and arranges the order which the buttons are displayed.
     */
    @Override
    protected void initDialog() {
        this.pcItem = (PriceChange) this.inData;
        super.initDialog();

        this.cancelButton.setText("Exit Detail List");

        // Rename the label and set the command action name of the OK button.
        this.okButton.setText(PriceChangeDetailEditDialog.BUTTON_TEXT_MODIFY);
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);
        this.okButton.setEnabled(true);

        this.refreshButton = new JButton("Refresh List");
        this.refreshButton
                .setActionCommand(PriceChangeDetailEditDialog.ACTION_COMMAND_REFRESH);
        this.refreshButton.addActionListener(this);
        this.buttonPane.add(this.refreshButton);

        this.statusButton = new JButton(
                PriceChangeDetailEditDialog.BUTTON_TEXT_CLOSE);
        this.statusButton
                .setActionCommand(PriceChangeDetailEditDialog.ACTION_COMMAND_CLOSE_ITEM);
        this.statusButton.addActionListener(this);
        this.buttonPane.add(this.statusButton);
        this.statusButton.setEnabled(false);

        // Resequence the z-indexes of the buttons
        try {
            this.buttonPane.getComponentCount();
            this.buttonPane.setComponentZOrder(this.refreshButton, 0);
            this.buttonPane.setComponentZOrder(this.okButton, 1);
            this.buttonPane.setComponentZOrder(this.statusButton, 2);
            this.buttonPane.setComponentZOrder(this.cancelButton, 3);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }

        String appTitle = this.getTitle();
        appTitle += this.pcItem.getId();
        this.setTitle(appTitle);
    }

    private List<PriceChangeSkuItem> fetchAllPriceChangeSkuQuery(PriceChange pc)
            throws DatabaseException {
        List<PriceChangeSkuItem> list = null;
        PriceChangeDao dao = null;
        try {
            PriceChangeFactory f = new PriceChangeFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchItemDetails(pc);
            return list;
        } catch (DatabaseException e) {
            logger.error(
                    "Error fetching all SKU's for Price Change #" + pc.getId(),
                    e);
            throw e;
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Creates the structure of the ScrollableDataGrid based on the List of data
     * items representing the grid data tobe displayed.
     * 
     * @param list
     *            the data to be displayed.
     * @return
     */
    private ScrollableDataGrid createDataGrid(List<PriceChangeSkuItem> list) {

        // Setup column definitions
        List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
        colDefs.add(new ColumnDefinition("sSku", "SKU", 0, 120));
        colDefs.add(new ColumnDefinition("dept", "Dept", 1, 50));
        colDefs.add(new ColumnDefinition("clazz", "Class", 2, 50));
        colDefs.add(new ColumnDefinition("vendor", "Vendor", 3, 60));
        colDefs.add(new ColumnDefinition("style", "Style", 4, 60));
        colDefs.add(new ColumnDefinition("color", "Color", 5, 50));
        colDefs.add(new ColumnDefinition("size", "Size", 6, 50));
        colDefs.add(new ColumnDefinition("status", "O/C", 7, 50));
        colDefs.add(new ColumnDefinition("oldRetailStr", "Old Price", 8, 90));
        colDefs.add(new ColumnDefinition("newRetailStr", "New Price", 9, 90));
        colDefs.add(new ColumnDefinition("markQty", "Qty", 10, 60));

        // Set the size of the data grid component
        Dimension size = new Dimension(750, 430);
        // Create ScrollableDataGrid grid component
        ScrollableDataGrid dg = new ScrollableDataGrid(list, colDefs,
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, size);
        dg.addItemSelectionListener(this);
        return dg;
    }

    /**
     * Uses the MigLayout to add the controls needed for this window.
     */
    @Override
    public JPanel createContentLayout() {
        List<PriceChangeSkuItem> items = null;
        try {
            items = this.fetchAllPriceChangeSkuQuery(this.pcItem);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        this.grid = this.createDataGrid(items);

        // Identify those columns that need to use special comparators for
        // sorting
        int numericCols[] = { 0, 1, 2, 3, 4, 5, 6, 8, 9, 10 };
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
     * Verifies that only a single SKU is selected from the data grid for
     * quantity updates.
     * <p>
     * If the user selectes multiple SKU's, the application displays an error
     * message stating that SKU quantity updates are applied on the basis of a
     * single SKU.
     * 
     * @return the selected SKU as an instance of {@link SkuItem}
     * @throws InvalidDataException
     *             When multiple or zero SKU's are selected from the data grid
     *             of SKU's.
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        List<SkuItem> selectedItems = this.grid.getSelectedItems();
        if (selectedItems == null || selectedItems.size() == 0) {
            this.msg = "A selection from the list of SKU's must be made before applying quantity updates";
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        if (selectedItems.size() > 1) {
            this.msg = "Must select a single SKU to perform quantity updates.  Multiple SKU's are not allowed.";
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        return selectedItems.get(0);
    }

    /**
     * Updates the marked quantity of a single selected SKU from the Data Grid
     * component.
     * <p>
     * First, the {@link GenericQtyInputDialog} dialog is opened to allow the
     * user to specify the SKU quantity. The validations for input SKU quantity
     * are handle from within the SkuQtyInputDialog only.
     * 
     * @param data
     *            N/A
     * @return The new quatity amount for the specified SKU
     * @throws LaunchPadException
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        if (data == null) {
            this.msg = "Error occurred accessing and/or identifying row selection for updating the SKU's marked quantity.  Please contact the support team for assistance.";
            JOptionPane.showMessageDialog(this, this.msg,
                    "Modify SKU Marked Quatnity Invalid Selection Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            throw new LaunchPadException(this.msg);
        }
        PriceChangeSkuItem modelItem = null;
        if (data instanceof SkuItem) {
            modelItem = (PriceChangeSkuItem) data;
        }

        // Get the quantity from input dialog
        Integer skuQty = this.openSkuMarkedQtyInputWindow(modelItem.getsSku());

        // Abort if user cancelled out of SKU Quantity dialog
        if (skuQty == null) {
            return null;
        }

        // Apply update to the database with the data we've gathered thus far.
        PriceChangeDao dao = null;
        int rowsModified = 0;
        try {
            PriceChangeFactory f = new PriceChangeFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            rowsModified = dao.updateItemDetailQuantity(modelItem.getPcNo(),
                    modelItem.getSku(), modelItem.getOverFlowSku(), skuQty);
            logger.info("Total rows effected by SKU marked quantity update operation: "
                    + rowsModified);
            this.msg = "The marked quantity for SKU, " + modelItem.getsSku()
                    + ", was successfully updated to the amount of " + skuQty;
            logger.info(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, false);
        } catch (DatabaseException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("Error occured applying SKU marked quantity updates for PC number [");
            buf.append(modelItem.getPcNo());
            buf.append("] and SKU {");
            buf.append(modelItem.getsSku());
            buf.append("].\n\nPlease contact the support team for assistance.");
            this.msg = buf.toString();
            JOptionPane.showMessageDialog(this, this.msg,
                    "Database Error for SKU Marked Quatnity Update Operation",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg, e);
            throw new LaunchPadException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
        }

        // Update the model with new quantity
        modelItem.setMarkQty(skuQty);
        this.grid.refreshGrid();
        return skuQty;
    }

    /**
     * Implemented as a stub method.
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        return;
    }

    /**
     * Dynamically manages the state of the SKU Open/Close Status button.
     * <p>
     * For all intents and purposes of this particular use case, this button
     * state includes 1) the text that is displayed and 2) whether or not the
     * button is enabled. The button's state change is based on the rows
     * currently selected in the data grid. The following rules apply for the
     * button's state changes:
     * <ol>
     * <li>When all selected SKUs have a status of <b>"O"</b> (Open), the button
     * should be active and display the text, "Close Line(s)"</li>
     * <li>When all selected SKUs have a status of <b>"C"</b> (Close), the
     * button should be active and display the text, "Open Line(s)"</li>
     * <li>When all selected SKUs have a mixture of both statuses, the button's
     * text will read "Close Line(s)" and the button will be disabled.</li>
     * </ol>
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        this.currentDtlItem = (PriceChangeSkuItem) evt.getSelectedItem();

        String selectedStatus = this.getSelectedStatus();
        if (selectedStatus == null) {
            this.statusButton
                    .setText(PriceChangeDetailEditDialog.BUTTON_TEXT_CLOSE);
            this.statusButton
                    .setActionCommand(PriceChangeDetailEditDialog.ACTION_COMMAND_CLOSE_ITEM);
            this.statusButton.setEnabled(false);
            return;
        }
        if (selectedStatus.equalsIgnoreCase("O")) {
            this.statusButton
                    .setText(PriceChangeDetailEditDialog.BUTTON_TEXT_CLOSE);
            this.statusButton
                    .setActionCommand(PriceChangeDetailEditDialog.ACTION_COMMAND_CLOSE_ITEM);
            this.statusButton.setEnabled(true);
        }
        if (selectedStatus.equalsIgnoreCase("C")) {
            this.statusButton
                    .setText(PriceChangeDetailEditDialog.BUTTON_TEXT_OPEN);
            this.statusButton.setEnabled(true);
            this.statusButton
                    .setActionCommand(PriceChangeDetailEditDialog.ACTION_COMMAND_OPEN_ITEM);
        }
    }

    /**
     * Returns the common status code of all selected SKU's from the data grid.
     * 
     * @return <ul>
     *         <li>"O" when the status values of all the selected sku's are
     *         equal "O".</li>
     *         <li>"C" when the status values of all the selectd sku's are equal
     *         "C".</li>
     *         <li>null when more than one sku is selected and the group
     *         contains both status values.</li>
     *         <ul>
     */
    private String getSelectedStatus() {
        List<SkuItem> list = this.grid.getSelectedItems();
        if (list == null) {
            return null;
        }
        String firstSelStatus = (list.size() > 0 ? list.get(0).getStatus()
                : null);
        if (firstSelStatus == null) {
            return null;
        }
        for (SkuItem item : list) {
            if (!item.getStatus().equalsIgnoreCase(firstSelStatus)) {
                return null;
            }
        }
        return firstSelStatus;
    }

    /**
     * Loads/Reloads the data grid with SKUs related to a given Price Change
     * Number.
     */
    protected void refreshDataGrid() {
        try {
            // Get active price change info from the database
            List<PriceChangeSkuItem> list = this
                    .fetchAllPriceChangeSkuQuery(this.pcItem);
            // reload grid with new PC data list
            this.grid.loadView(list);
            this.statusButton.setEnabled(false);
            this.displayRecordCount();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Applies status changes to the database for all SKU's that are selected in
     * the data grid.
     * 
     * @param newStatus
     *            The status the selected SKU's will be changed to. Valid values
     *            are "O" and "C".
     */
    protected void updateSelectedLineStatus(String newStatus) {
        if (newStatus == null) {
            return;
        }
        if (newStatus.equalsIgnoreCase("O") || newStatus.equalsIgnoreCase("C")) {
            // Construct confirmation message
            if (newStatus.equalsIgnoreCase("C")) {
                String confirmTitle = "Price Change Line Status Modification Confirmation";
                this.msg = "Close selected lines:  Are you sure?";
                // Open dialog to confirm user's intent to change status to
                // "Close"
                int confirm = JOptionPane.showConfirmDialog(this, this.msg,
                        confirmTitle, JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (confirm == 1) {
                    return;
                }
            }
        }
        else {
            return;
        }

        // User intent has been confirmed...prepare for status updates
        SecurityToken token = UserSecurityManager.getSecurityToken();
        PriceChangeFactory f = new PriceChangeFactory();
        PriceChangeDao dao = f.getDaoInstance(token);

        // Apply updates to the database.
        try {
            int rows = dao.updateItemDetailsBySku(this.pcItem.getId(),
                    newStatus, this.grid.getSelectedItems());
            logger.info("The status of " + rows + " SKU(s) were updated to "
                    + newStatus);
            // Update model selected item status so that it can be reflected in
            // the view
            List<SkuItem> pcItems = this.grid.getSelectedItems();
            for (SkuItem item : pcItems) {
                item.setStatus(newStatus);
            }
            this.grid.refreshGrid();
        } catch (DatabaseException e) {
            this.msg = "Error updating the status of one or more SKU lines to \'"
                    + newStatus
                    + "\' for price change number, "
                    + this.pcItem.getId();
            logger.error(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new RuntimeException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
            f = null;
        }
    }

    /**
     * Handles the click actions of the <i>Refresh List</i>, <i>Open
     * Line(s)C</i> and <i>Close Line(s)</i> buttons.
     * 
     * @param e
     *            instance of {@link ActionEvent}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getActionCommand().equals(
                PriceChangeDetailEditDialog.ACTION_COMMAND_REFRESH)) {
            this.refreshDataGrid();
        }
        if (e.getActionCommand().equals(
                PriceChangeDetailEditDialog.ACTION_COMMAND_OPEN_ITEM)) {
            this.updateSelectedLineStatus("O");
        }
        if (e.getActionCommand().equals(
                PriceChangeDetailEditDialog.ACTION_COMMAND_CLOSE_ITEM)) {
            this.updateSelectedLineStatus("C");
        }
    }

    /**
     * Opens the SKU Quantity Input dialog for the purpose of allowing the user
     * to key in a numeric value for SKU quantity.
     * <p>
     * This method is invoked when the user clicks the "Modify Qty" button. Upon
     * closing the SKU Quantity Input dialog, the value entered by the user is
     * returned to the caller.
     * 
     * @param sku
     *            the SKU number targeted for quantity change
     * @return the quantity change or null in the case when the user cancelled
     *         the SKU Quantity Input dialog.
     * @throws LaunchPadException
     *             An error occurred opening the Modify SKU Quantity Input
     *             Dialog
     */
    protected Integer openSkuMarkedQtyInputWindow(long sku)
            throws LaunchPadException {
        String windowTitle = "Modify Marked Quantity for Item " + sku;
        String inputFieldLabel = "Quantity Marked:";
        try {
            PriceChangeQtyInputDialog dialog = new PriceChangeQtyInputDialog(
                    this, new Dimension(370, 150), new Point(700, 300),
                    windowTitle, inputFieldLabel,
                    this.currentDtlItem.getMarkQty());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            return dialog.getQty();
        } catch (Exception e) {
            this.msg = "An error occured opening the MOdify SKU Quantity Dialog window.\n\nPlease consult the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg,
                    "Modify SKU Quatnity Input Dialog Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg, e);
            throw new LaunchPadException(e);
        }
    }

    /**
     * Displays the total number of detail records fetched from the database in
     * the message area for a given price change.
     */
    @Override
    protected void displayRecordCount() {
        super.displayRecordCount();

        // Dispaly record count
        this.msg = this.grid.getTableView().getRowCount() + " records found";
        AppUtil.showMessage(this.msgLbl, this.msg, false);
    }

}

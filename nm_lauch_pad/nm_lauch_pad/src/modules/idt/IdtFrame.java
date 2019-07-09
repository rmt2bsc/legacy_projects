package modules.idt;

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

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import modules.LaunchPadException;
import modules.SkuFinderDialog;
import modules.model.SkuItem;

import com.InvalidDataException;
import com.NotFoundException;

import com.nv.db.DatabaseException;

import com.nv.security.UserSecurityManager;

import com.nv.util.AppUtil;
import com.nv.util.GeneralUtil;

import com.ui.AbstractModelessWindow;
import com.ui.WindowProcessTemplateListener;

import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.event.ComponentItemSelectedEvent;
import com.ui.event.ComponentSelectionListener;

import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * The main window for the IDT administration module which allows the
 * administrator to modiy IDT reticketing recrods
 * 
 * @author rterrell
 *
 */
public class IdtFrame extends AbstractModelessWindow implements
        ComponentSelectionListener {

    private static final long serialVersionUID = -8144849251857507324L;

    private static final Logger logger = Logger.getLogger(IdtFrame.class);

    /**
     * The action command name for the "Refresh List" button
     */
    public static final String ACTION_COMMAND_REFRESH = "REFRESH_LIST";

    /**
     * The action command name for the "Find SKU" button
     */
    public static final String ACTION_COMMAND_FIND = "FIND_SKU";

    /**
     * The action command name for the "Filter" button
     */
    public static final String ACTION_COMMAND_FILTER = "FILTER";

    private JButton refreshButton;

    private JButton findButton;

    private JButton filterButton;

    private ScrollableDataGrid grid;

    private SkuItem selectedItem;

    /**
     * Create a IdtFrame specifying the its size, position and window title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public IdtFrame(Dimension size, Point pos, String title) {
        super(size, pos, title);
    }

    /**
     * Performs specific constructor related tasks for this window.
     * <p>
     * Changes the text of the cancel and Ok buttons to "Exit List" and
     * "Modify Qty", respectively. Adds the refresh button and dht find sku
     * button and arranges the order which the buttons are displayed.
     */
    @Override
    protected void initFrame() {
        // Proceed with frame initialization
        super.initFrame();
        this.cancelButton.setText("Exit List");

        // Rename the label and set the command action name of the OK button.
        this.okButton.setText("Modify Qty");
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);
        this.okButton.setEnabled(false);

        this.refreshButton = new JButton("Refresh List");
        this.refreshButton.setActionCommand(IdtFrame.ACTION_COMMAND_REFRESH);
        this.refreshButton.addActionListener(this);
        this.buttonPane.add(this.refreshButton);

        this.findButton = new JButton("Find SKU");
        this.findButton.setActionCommand(IdtFrame.ACTION_COMMAND_FIND);
        this.findButton.addActionListener(this);
        this.buttonPane.add(this.findButton);

        this.filterButton = new JButton("Filter");
        this.filterButton.setActionCommand(IdtFrame.ACTION_COMMAND_FILTER);
        this.filterButton.addActionListener(this);
        this.buttonPane.add(this.filterButton);

        // Resequence the z-indexes of the buttons
        try {
            this.buttonPane.getComponentCount();
            this.buttonPane.setComponentZOrder(this.refreshButton, 0);
            this.buttonPane.setComponentZOrder(this.okButton, 1);
            this.buttonPane.setComponentZOrder(this.findButton, 2);
            this.buttonPane.setComponentZOrder(this.filterButton, 3);
            this.buttonPane.setComponentZOrder(this.cancelButton, 4);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Fetches a list of IDT reticketing items from the database.
     * 
     * @param skuCriteria
     *            an instance of {@link SkuItem} containing optional selection
     *            criteria which includes the properties: department, class,
     *            vendor, style, color, and size.
     * @return a List of {@link SkuItem} objects
     * @throws DatabaseException
     */
    private List<SkuItem> doQuery(SkuItem skuCriteria) throws DatabaseException {
        List<SkuItem> list = null;
        IdtDao dao = null;
        try {
            IDTFactory f = new IDTFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchReticketHeader(skuCriteria);
            return list;
        } catch (DatabaseException e) {
            logger.error(
                    "Error fetching IDT reticketing items from the database", e);
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
    private ScrollableDataGrid createDataGrid(List<SkuItem> list) {

        // Setup column definitions
        List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
        colDefs.add(new ColumnDefinition("dept", "Dept", 0, 75));
        colDefs.add(new ColumnDefinition("clazz", "Class", 1, 75));
        colDefs.add(new ColumnDefinition("vendor", "Vendor", 2, 75));
        colDefs.add(new ColumnDefinition("style", "Style", 3, 75));
        colDefs.add(new ColumnDefinition("color", "Color", 4, 75));
        colDefs.add(new ColumnDefinition("size", "Size", 5, 75));
        colDefs.add(new ColumnDefinition("qty", "Qty", 6, 75));
        colDefs.add(new ColumnDefinition("priceStr", "Current Retail", 7, 100));

        // Set the size of the data grid component
        Double s = new Double(this.size.getWidth());
        Dimension size = new Dimension(s.intValue() - 10, 250);

        // Create ScrollableDataGrid grid component
        ScrollableDataGrid dg = new ScrollableDataGrid(list, colDefs,
                ListSelectionModel.SINGLE_SELECTION, size);
        dg.addItemSelectionListener(this);
        return dg;
    }

    /**
     * Uses the MigLayout to add the controls needed for this window.
     */
    @Override
    public JPanel createContentLayout() {
        // Get active price change info from the database
        List<SkuItem> list;
        try {
            list = this.doQuery(null);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        // Build grid with PC data
        this.grid = this.createDataGrid(list);

        // Identify those columns that need to use special comparators for
        // sorting
        int numericCols[] = { 0, 1, 2, 3, 4, 5, 6, 7 };
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
     * Repopulates the data grid by querying the database for all active IDT
     * reticketing SKUs.
     * <p>
     * Any filters that were previously applied will be ignored.
     * 
     * @throws DatabaseException
     *             Genreal database access errors.
     */
    protected void refreshDataGrid() {
        try {
            this.refreshDataGrid(null);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Repopulates the data grid by querying the database for a subset of
     * reticketing SKU's using <i>item</i> as query selection criteria.
     * <p>
     * The properties of <i>item</i> used to filter the query results are
     * optional and include department, class, vendor, style, color, and size.
     * Only those properties containing a non-null value are applied to the
     * query's where clause. In order to return all of the reticketing items,
     * all qualifying properties must be set to null values.
     * 
     * @param item
     *            An instance of {@link SkuItem} providing selection criteria.
     *            The properties targeted as selection criteria items are
     *            department, class, vendor, style, color, and size.
     * @throws DatabaseException
     *             General database access errors.
     * @throws NotFoundException
     *             When the query returns an empty data set as a result of
     *             selection criteria, <i>item</i>
     */
    protected void refreshDataGrid(SkuItem item) throws DatabaseException,
            NotFoundException {
        // Get active price change info from the database
        List<SkuItem> list = this.doQuery(item);
        if (list == null) {
            StringBuffer buf = new StringBuffer();
            buf.append("The following IDT selection criteria returned an empty result set:\n");
            buf.append("\tDepartment - ");
            buf.append(item.getDept());
            buf.append("\n");
            buf.append("\tClass - ");
            buf.append(item.getClazz());
            buf.append("\n");
            buf.append("\tVendor - ");
            buf.append(item.getVendor());
            buf.append("\n");
            buf.append("\tStyle - ");
            buf.append(item.getStyle());
            buf.append("\n");
            buf.append("\tColor - ");
            buf.append(item.getColor());
            buf.append("\n");
            buf.append("\tSize - ");
            buf.append(item.getSize());
            buf.append("\n");
            throw new NotFoundException(buf.toString());
        }
        // reload grid with new PC data list
        this.grid.loadView(list);
        this.displayRecordCount();
    }

    /**
     * Fetches all IDT reticketing items that are associated with a given SKU
     * number.
     * <p>
     * Before any database fetching is performed, the SKU number is obtained
     * from an input dialog. Once the SKU is provided, an existence check of the
     * SKU is performed against the idt_sku table, and if valid, the data grid
     * is refreshed with IDT reticketing data as it relates to the SKU.
     */
    protected void filterDataBySkuNo() {
        String skuStr = null;
        try {
            SkuFinderDialog dialog = new SkuFinderDialog(this, new Dimension(
                    370, 150), new Point(700, 300), "Find SKU");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            skuStr = dialog.getSku();
        } catch (Exception e) {
            throw new InvalidDataException(e);
        }

        // Abort if user cancelled dialog
        if (skuStr == null) {
            return;
        }

        SkuItem targetSku = null;
        long skuNo = 0;

        // Fetch all reticketed items if user did not enter anything for sku
        // criteria.
        if (skuStr.equals("")) {
            targetSku = new SkuItem();
        }
        else if (GeneralUtil.isNumeric(skuStr)) {
            // logic to validate SKU's existence.
            skuNo = Long.parseLong(skuStr);
            IdtDao dao = null;
            try {
                IDTFactory f = new IDTFactory();
                dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
                targetSku = dao.fetchSku(skuNo);
                if (targetSku == null) {
                    this.msg = "No long SKU match found for SKU " + skuNo;
                    logger.error(this.msg);
                    AppUtil.showMessage(this.msgLbl, this.msg, true);
                    throw new RuntimeException(this.msg);
                }
            } catch (DatabaseException e) {
                this.msg = e.getMessage()
                        + "\n\nContact the support team for assistance";
                JOptionPane.showMessageDialog(this, this.msg,
                        "Launch Pad Error", JOptionPane.ERROR_MESSAGE);
                logger.error(this.msg);
                throw new RuntimeException(this.msg, e);
            } finally {
                dao.close();
                dao = null;
            }
        }

        // Filter the data with the specified sku
        try {
            this.refreshDataGrid(targetSku);
        } catch (Exception e) {
            this.msg = "Problem occurred filtering data.  " + e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg, e);
            throw new RuntimeException(this.msg, e);
        }
    }

    /**
     * Fetches IDT reticketing items that matches one or more SKU selection
     * criteria: department, class, vendor, style, color and/or size.
     * <p>
     * Before any database fetching is performed, the SKU criteria details are
     * gathered from an input dialog. Once the detail SKU criteria is provided,
     * the data grid is refreshed with IDT reticketing data.
     */
    protected void filterDataBySkuDetails() {
        SkuItem criteria;
        try {
            IdtSkuFilterInputDialog dialog = new IdtSkuFilterInputDialog(this,
                    new Dimension(370, 250), new Point(700, 300),
                    "IDT Filter Screen");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            criteria = dialog.getCriteria();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Abort if user cancelled dialog
        if (criteria == null) {
            return;
        }

        try {
            this.refreshDataGrid(criteria);
        } catch (NotFoundException e) {
            this.msg = "No IDT found with that criteria";
            JOptionPane.showMessageDialog(this, this.msg,
                    "IDT Administration SKU Search",
                    JOptionPane.INFORMATION_MESSAGE);
            logger.warn(this.msg, e);
            throw new RuntimeException(this.msg, e);
        } catch (DatabaseException e) {
            this.msg = "Problem occurred filtering data.  " + e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg, e);
            throw new RuntimeException(this.msg, e);
        }
    }

    /**
     * Opens an input dialog for gathering the Quantity Sent value and returns
     * the value to the caller.
     * <p>
     * This method is triggered by the user clicking the <i>Modify Qty</i>
     * button.
     * 
     * @return Integer as the Quantity Sent entered by the user.
     * @throws InvalidDataException
     *             Invalid data errors recognized by the dialog.
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        StringBuffer dialogTitle = new StringBuffer();
        dialogTitle.append("Modify Quantity for Item ");
        dialogTitle.append(this.selectedItem.getDept());
        dialogTitle.append(" ");
        dialogTitle.append(this.selectedItem.getClazz());
        dialogTitle.append(" ");
        dialogTitle.append(this.selectedItem.getVendor());
        dialogTitle.append(" ");
        dialogTitle.append(this.selectedItem.getStyle());
        dialogTitle.append(" ");
        dialogTitle.append(this.selectedItem.getColor());
        dialogTitle.append(" ");
        dialogTitle.append(this.selectedItem.getSize());

        try {
            IdtModifyQtyInputDialog dialog = new IdtModifyQtyInputDialog(this,
                    new Dimension(390, 150), new Point(700, 300),
                    dialogTitle.toString(), "Quantity Sent:",
                    this.selectedItem.getQty());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            return dialog.getQty();
        } catch (Exception e) {
            throw new InvalidDataException(e);
        }
    }

    /**
     * Updates the quantity sent of a reticketing item using value entered by
     * the users in the method, {@link IdtFrame#getInputData() getInputData()}.
     * <p>
     * If the quantity sent value is greater than zero, the reticketed item's
     * quantity should be update in the idt_retick table. If the quantity sent
     * is equal to zero, then the reticketing item be deleted from the database.
     * <p>
     * Before a record is deleted, a dialog is displayed forcing the user to
     * confirm his/her intent to delete. Upon delete confirmation, the delete
     * operation entails the removal of the reticketing item from the idt_retick
     * and idt_sku tables.
     * 
     * @param data
     *            The quantity sent value entered by the user, which its runtime
     *            value should evaluate to an Integer.
     * @return The total number of rows effected by the update/delete operation.
     * @throws LaunchPadException
     *             General database access errors.
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        // Abort if user cancelled Modify Quantiy Input Dialog or if input is
        // something other than a number greater than or equal to zero.
        if (data == null) {
            return null;
        }
        Integer qty = (Integer) data;
        if (qty < 0) {
            return null;
        }

        // At this point, we are ready to apply changes to the database.
        IdtDao dao = null;
        int rows = 0;
        try {
            IDTFactory f = new IDTFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());

            if (qty > 0) {
                // Logic to modify SKU quantity sent changes.
                rows = dao.updateSkuQty(this.selectedItem, qty);
                logger.info(rows
                        + " were effected by the changing the quantity sent to "
                        + qty);
            }
            else {
                // Logic to delete record since user okayed the operation.
                this.msg = "Changing the quantity to zero will delete this record.  Are you sure?";
                String title = "IDT Quantity Update Validation";
                int rc = JOptionPane.showConfirmDialog(this, this.msg, title,
                        JOptionPane.OK_CANCEL_OPTION);
                System.out.println(rc);
                if (rc == 0) {
                    rows = dao.deleteSku(this.selectedItem);
                    logger.info(rows
                            + " were effected by the changing the quantity sent to zero");
                }
            }
        } catch (DatabaseException e) {
            this.msg = "Problem occurred updating the quantity sent for the selected row.  "
                    + e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg, e);
            throw new LaunchPadException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
        }

        this.selectedItem.setQty(qty);
        int currentRow = this.grid.getSelectedRow();
        this.grid.refreshGrid();
        this.grid.scrollToVisible(currentRow);

        return rows;
    }

    /**
     * Inovkes the process that displays the short SKU number related to the
     * selected reticketing item.
     * 
     * @param evt
     *            an instance of {@link ComponentItemDoubleClickedEvent}
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        this.showSelectedShortSku();
        return;
    }

    /**
     * Dynamically manages the state of the <i>Modify Qty</i> button.
     * <p>
     * The Modify button is enabled only when a row int he data grid is
     * selected.
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        this.selectedItem = (SkuItem) evt.getSelectedItem();
        this.okButton.setEnabled(true);
        return;
    }

    /**
     * Opens a dialog that displays the short SKU number associated with the
     * selected reticketing item. If the SKU is not found, a message stating the
     * obvious is displayed instead.
     */
    protected void showSelectedShortSku() {
        // fetch the short sku for the selected reticket item
        Long sku = 0L;
        IdtDao dao = null;
        try {
            IDTFactory f = new IDTFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            sku = dao.fetchShortSku(this.selectedItem);
            if (sku == null) {
                this.msg = "Unable to determine the short SKU for the selected row";
                JOptionPane.showMessageDialog(this, this.msg, "IDT Admin",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                this.msg = "The short SKU for the selected row: " + sku;
                JOptionPane.showMessageDialog(this, this.msg, "IDT Admin",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (DatabaseException e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            throw new RuntimeException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Displays the total reticketing item records fetched from the database in
     * the message area.
     */
    @Override
    protected void displayRecordCount() {
        super.displayRecordCount();

        // Dispaly record count
        this.msg = this.grid.getTableView().getRowCount() + " records found";
        AppUtil.showMessage(this.msgLbl, this.msg, false);
    }

    /**
     * Handles the click actions of the <i>Refresh List</i>, <i>Find Sku</i>,
     * and <i>Filter</i> buttons.
     * 
     * @param e
     *            instance of {@link ActionEvent}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        if (e.getActionCommand().equalsIgnoreCase(
                IdtFrame.ACTION_COMMAND_REFRESH)) {
            this.refreshDataGrid();
        }

        if (e.getActionCommand().equalsIgnoreCase(IdtFrame.ACTION_COMMAND_FIND)) {
            this.filterDataBySkuNo();
        }

        if (e.getActionCommand().equalsIgnoreCase(
                IdtFrame.ACTION_COMMAND_FILTER)) {
            this.filterDataBySkuDetails();
        }
    }

}

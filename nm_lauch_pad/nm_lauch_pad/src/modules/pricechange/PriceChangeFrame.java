package modules.pricechange;

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
import modules.SkuFinderDialog;
import modules.model.PriceChange;
import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import com.InvalidDataException;

import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;
import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;
import com.ui.AbstractModelessWindow;
import com.ui.WindowProcessTemplateListener;
import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.event.ComponentItemSelectedEvent;
import com.ui.event.ComponentSelectionListener;
import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * The main window frame for managaging Price Change data that is stored in the
 * tables pc_change (the header table) and pc_item (the detail table).
 * 
 * @author rterrell
 *
 */
public class PriceChangeFrame extends AbstractModelessWindow implements
        ComponentSelectionListener {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger
            .getLogger(PriceChangeFrame.class);

    /**
     * The title of the Detail for Price Change dialog
     */
    public static final String UI_DETAIL_TITLE = "Detail For Price Change #";

    /**
     * The action command name for the "Refresh List" button
     */
    public static final String ACTION_COMMAND_REFRESH = "REFRESH_LIST";

    /**
     * The action command name for the "Find SKU(s)" button
     */
    public static final String ACTION_COMMAND_FIND = "FIND_SKU";

    /**
     * The action command name for the "Open PC" button
     */
    public static final String ACTION_COMMAND_OPEN_ITEM = "OPEN_PC";

    /**
     * The action command name for the "Close PC" button
     */
    public static final String ACTION_COMMAND_CLOSE_ITEM = "CLOSE_PC";

    private static final String UI_SUBTITLE_ALL = " (All SKU's)";

    private static final String UI_SUBTITLE_SKU = " (SKU xxxx)";

    private JButton refreshButton;

    private JButton findButton;

    private ScrollableDataGrid grid;

    private PriceChange selectedItem;

    private String baseWinTitle;

    /**
     * Create a PriceChangeFrame specifying the its size, position and window
     * title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public PriceChangeFrame(Dimension size, Point pos, String title) {
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
        this.okButton.setText("Close PC");
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);
        this.okButton.setEnabled(false);

        this.refreshButton = new JButton("Refresh List");
        this.refreshButton
                .setActionCommand(PriceChangeFrame.ACTION_COMMAND_REFRESH);
        this.refreshButton.addActionListener(this);
        this.buttonPane.add(this.refreshButton);

        this.findButton = new JButton("Find PC(s)");
        this.findButton.setActionCommand(PriceChangeFrame.ACTION_COMMAND_FIND);
        this.findButton.addActionListener(this);
        this.buttonPane.add(this.findButton);

        // Resequence the z-indexes of the buttons
        try {
            this.buttonPane.getComponentCount();
            this.buttonPane.setComponentZOrder(this.refreshButton, 0);
            this.buttonPane.setComponentZOrder(this.findButton, 1);
            this.buttonPane.setComponentZOrder(this.okButton, 2);
            this.buttonPane.setComponentZOrder(this.cancelButton, 3);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }

        String appTitle = this.getTitle();
        this.baseWinTitle = appTitle;
        appTitle += PriceChangeFrame.UI_SUBTITLE_ALL;
        this.setTitle(appTitle);
    }

    /**
     * Fetches the active price change data from the database.
     * <p>
     * Either all active price change records are retrieved or a subset list of
     * records using a sku filter. The property, <i>skuCriteria</i>, is used as
     * the basis for determining whether or not the filter is applied. No filter
     * is applied when the property is set to null.
     * 
     * @return a List of {@link PriceChange} objects
     * @throws DatabaseException
     */
    private List<PriceChange> doActivePriceChangeQuery(String skuCriteria)
            throws DatabaseException {
        List<PriceChange> list = null;
        PriceChangeDao dao = null;
        try {
            PriceChangeFactory f = new PriceChangeFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            if (skuCriteria == null) {
                list = dao.fetchAllActive();
            }
            else {
                list = dao.fetchActiveBySku(skuCriteria);
            }
            return list;
        } catch (DatabaseException e) {
            logger.error(
                    "Error fetching Active Price Change list from the database",
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
    private ScrollableDataGrid createDataGrid(List<PriceChange> list) {

        // Setup column definitions
        List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
        colDefs.add(new ColumnDefinition("id", "Price Chg#", 0, 100));
        colDefs.add(new ColumnDefinition("dept", "Dept", 1, 75));
        colDefs.add(new ColumnDefinition("effDateStr", "Effective", 2, 100));
        colDefs.add(new ColumnDefinition("reasonCd", "RC", 3, 50));
        colDefs.add(new ColumnDefinition("status", "O/C", 4, 50));
        colDefs.add(new ColumnDefinition("message", "Message", 5, 305));

        // Set the size of the data grid component
        Double s = new Double(this.size.getWidth());
        Dimension size = new Dimension(s.intValue() - 10, 250);
        // Dimension size = new Dimension(680, 250);
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
        List<PriceChange> list;
        try {
            list = this.doActivePriceChangeQuery(null);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        // Build grid with PC data
        this.grid = this.createDataGrid(list);

        // Identify those columns that need to use special comparators for
        // sorting
        int numericCols[] = { 0, 1, 3 };
        this.grid.setNumericColumnSorter(numericCols);
        int dateCols[] = { 2 };
        this.grid.setDateColumnSorter(dateCols);

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
        return null;
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
        return null;
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
        if (e.getActionCommand().equals(PriceChangeFrame.ACTION_COMMAND_FIND)) {
            this.openSkuFinderWindow();
        }
        if (e.getActionCommand()
                .equals(PriceChangeFrame.ACTION_COMMAND_REFRESH)) {
            this.refreshDataGrid(null);
        }
        if (e.getActionCommand().equals(
                PriceChangeFrame.ACTION_COMMAND_OPEN_ITEM)) {
            this.updateSelectedItemStatus("O");
        }
        if (e.getActionCommand().equals(
                PriceChangeFrame.ACTION_COMMAND_CLOSE_ITEM)) {
            this.updateSelectedItemStatus("C");
        }
    }

    /**
     * Opens the SKU Finder filter dialog in order to filter the result set by a
     * specific SKU of the price change data grid.
     * <p>
     * This method is invoked when the user clicks the "Find PC(s)" button. Upon
     * closing the SKU Finder dialog, a SKU is returned and a database query is
     * performed to obtain a list of price changes pertaining the the said SKU.
     */
    protected void openSkuFinderWindow() {
        try {
            SkuFinderDialog dialog = new SkuFinderDialog(this, new Dimension(
                    370, 150), new Point(700, 300), "Find SKU");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            String skuCriteria = dialog.getSku();

            // Abort if user cancelled dialog
            if (skuCriteria == null) {
                return;
            }
            // Fetch all PC's if user did not enter anything for sku criteria.
            if (skuCriteria.equals("")) {
                skuCriteria = null;
            }
            // Get active price change info from the database
            this.refreshDataGrid(skuCriteria);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Queries the database for price changes records based on a specific SKU
     * number and updates the price change grid with the results.
     * <p>
     * If <i>sku</i> is equal to null, then all active price change records are
     * fetched. Nonetheless, the data price change grid is refreshed/reloaded
     * with the results of the query. The window title will display differently
     * based on the value of <i>sku</i>. When <i>sku</i> is null, the window
     * title should display "Price Change List (All SKU's)". When <i>sku</i>
     * contains a sku value, the title should display
     * "Price Change List (SKU <sku number>)".
     * 
     * @param sku
     *            the sku number to fetch related price change records. Can be
     *            null which is indicative to return all price change records
     * @throws DatabaseException
     *             Genereal database access errors.
     */
    protected void refreshDataGrid(String sku) {
        try {
            // Get active price change info from the database
            List<PriceChange> list = this.doActivePriceChangeQuery(sku);
            // reload grid with new PC data list
            this.grid.loadView(list);

            // Change the window title
            if (sku == null) {
                this.setTitle(this.baseWinTitle
                        + PriceChangeFrame.UI_SUBTITLE_ALL);
            }
            else {
                this.setTitle(this.baseWinTitle
                        + PriceChangeFrame.UI_SUBTITLE_SKU.replaceAll("xxxx",
                                sku));
            }
            this.displayRecordCount();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Changes the status of a price change record to <i>newStatus</i>.
     * <p>
     * The change is based on the selected item in the price change data grid.
     * The value of <i>newStatus</i> must be either "O" or "C" which means
     * <i>reoopen</i> and <i>close</i>, respectively. Otherwise, the method
     * returns control to the caller without any actiion taken place.
     * <p>
     * Before the change request is performed, a popup dialog is displayed for
     * the purpose of confirming the intent of the user. For example, when
     * reopening a price change, the popup message will ask the user is he/she
     * sure about reopening change #nnnnnnnn. Likewise, a similar message is
     * shown for the case of closing a price change.
     * <p>
     * The status of a price change record can be changed to either "Open" or
     * "Closed". If the selected record has a status of Open, then the new
     * status will be "Closed". If the selected record has status of Closed,
     * then the new status will be "Open".
     * <p>
     * Once the status change is made in the database, the list's data model
     * will reflect the change immediately and the effected row will remain
     * highlighted.
     * 
     * @param newStatus
     *            the status to change the price change record to in which its
     *            valid values are "O" and "C" representing Open and Closed,
     *            respectively.
     */
    protected void updateSelectedItemStatus(String newStatus) {
        if (newStatus == null) {
            return;
        }
        if (newStatus.equalsIgnoreCase("O") || newStatus.equalsIgnoreCase("C")) {
            // Construct confirmation message
            String confirmTitle = "Price Change Status Confirmation";

            // Open dialog to confirm user's intent to change status to "Close"
            if (newStatus.equalsIgnoreCase("C")) {
                this.msg = "Close price change #" + this.selectedItem.getId();
            }
            else {
                this.msg = "Open price change #" + this.selectedItem.getId();
            }
            int confirm = JOptionPane.showConfirmDialog(this, this.msg,
                    confirmTitle, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.NO_OPTION) {
                return;
            }
        }
        else {
            return;
        }

        // User confirmed his intent to change the status.
        int rowIndex = this.grid.getSelectedRow();
        SecurityToken token = UserSecurityManager.getSecurityToken();
        PriceChangeFactory f = new PriceChangeFactory();
        PriceChangeDao dao = f.getDaoInstance(token);

        try {
            dao.updateStatus(this.selectedItem.getId(), newStatus);
            // Update model selected item status so that it can be reflected in
            // the view
            this.selectedItem.setStatus(newStatus);
            this.grid.refreshGrid();
            this.grid.scrollToVisible(rowIndex);
        } catch (DatabaseException e) {
            this.msg = "Error updating status to \'" + newStatus
                    + "\' for price change number, "
                    + this.selectedItem.getId();
            logger.error(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new RuntimeException(this.msg, e);
        } finally {
            dao.close();
            dao = null;
            f = null;
        }
    }

    protected void openDetailWindow(PriceChange item) {
        try {
            PriceChangeDetailEditDialog dialog = new PriceChangeDetailEditDialog(
                    this, item, new Dimension(780, 450), new Point(600, 360),
                    PriceChangeFrame.UI_DETAIL_TITLE);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inovkes the Price Change Detail Edit process for the selected price
     * change number.
     * 
     * @param evt
     *            an instance of {@link ComponentItemDoubleClickedEvent}
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        PriceChange item = (PriceChange) evt.getSelectedItem();
        this.openDetailWindow(item);
        return;
    }

    /**
     * Dynamically manages the state of the Price Change Status button.
     * <p>
     * For all intents and purposes of this particular use case, this button
     * state includes 1) the text that is displayed and 2) whether or not the
     * button is enabled. The button's state change is based on the row
     * currently selected in the data grid. The following rules apply for the
     * button's state changes:
     * <ol>
     * <li>When the selected record has a status of <b>"O"</b> (Open), the
     * button's text will read "Close PC"</li>
     * <li>When the selected record has a status of <b>"C"</b> (Closed), the
     * button's text will read "Open PC"</li>
     * <li>When the selected record has a status of <b>"A"</b> (Store
     * Initiated), the button's text will read "Close PC" and the button will be
     * disabled.</li>
     * </ol>
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        PriceChange item = null;
        if (evt.getSelectedItem() == null) {
            return;
        }
        if (evt.getSelectedItem() instanceof PriceChange) {
            item = (PriceChange) evt.getSelectedItem();
        }
        if (item.getStatus().equalsIgnoreCase("O")
                || item.getStatus().equalsIgnoreCase("A")) {
            this.okButton.setText("Close PC");
            this.okButton
                    .setActionCommand(PriceChangeFrame.ACTION_COMMAND_CLOSE_ITEM);
            this.okButton.setEnabled(true);
            if (item.getStatus().equalsIgnoreCase("A")) {
                this.okButton.setEnabled(false);
            }
        }
        if (item.getStatus().equalsIgnoreCase("C")) {
            this.okButton.setText("Open PC");
            this.okButton.setEnabled(true);
            this.okButton
                    .setActionCommand(PriceChangeFrame.ACTION_COMMAND_OPEN_ITEM);
        }
        this.selectedItem = item;
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
}

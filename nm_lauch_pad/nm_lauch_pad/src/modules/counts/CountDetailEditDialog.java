package modules.counts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import modules.GenericDateInputDialog;
import modules.LaunchPadException;

import modules.model.Count;
import modules.model.CountDetail;

import com.InvalidDataException;

import com.nv.db.DatabaseException;

import com.nv.security.SecurityToken;
import com.nv.security.UserSecurityManager;

import com.nv.util.AppUtil;
import com.nv.util.GeneralUtil;

import com.ui.AbstractModalWindow;
import com.ui.WindowProcessTemplateListener;

import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.event.ComponentItemSelectedEvent;
import com.ui.event.ComponentSelectionListener;

import com.ui.table.ColumnDefinition;
import com.ui.table.DataGrid;
import com.ui.table.ScrollableDataGrid;

/**
 * A window component capable of managing Count Detail records realted to a
 * particular Count Header.
 * 
 * @author rterrell
 *
 */
public class CountDetailEditDialog extends AbstractModalWindow implements
        ComponentSelectionListener {

    private static final long serialVersionUID = 944000419956402561L;

    private static final Logger logger = Logger
            .getLogger(CountDetailEditDialog.class);

    private static final String BUTTON_TEXT_REFRESH = "Refresh List";

    private static final String BUTTON_TEXT_DELETE = "Delete Count";

    private static final String BUTTON_TEXT_EDIT = "Edit Header";

    private static final String BUTTON_TEXT_EXIT = "Exit List";

    /**
     * The action command name for the "Delete Count" button
     */
    public static final String ACTION_COMMAND_DELETE_COUNT = "DELETE_COUNT";

    /**
     * The action command name for the "Refresh List" button
     */
    public static final String ACTION_COMMAND_REFRESH = "REFRESH_LIST";

    private ScrollableDataGrid grid;

    private Count header;

    private CountDetail detail;

    private JButton refreshButton;

    private JButton deleteButton;

    private int currentRow;

    /**
     * Create a CountDetailEditDialog object initailized with a parent object,
     * the Count header model item, the window size, the window postion and
     * window title.
     * 
     * @param owner
     *            the parent window reference
     * @param item
     *            The count header model containing the data to be updated.
     * @param size
     *            The window size coordinates
     * @param pos
     *            The window position coordinates
     * @param winTitle
     *            The window title.
     */
    public CountDetailEditDialog(Frame owner, Count item, Dimension size,
            Point pos, String winTitle) {
        super(owner, item, size, pos, winTitle);
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
        this.header = (Count) this.inData;
        super.initDialog();

        this.cancelButton.setText(CountDetailEditDialog.BUTTON_TEXT_EXIT);

        // Rename the label and set the command action name of the OK button.
        this.okButton.setText(CountDetailEditDialog.BUTTON_TEXT_EDIT);
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);
        this.okButton.setEnabled(false);

        this.refreshButton = new JButton(
                CountDetailEditDialog.BUTTON_TEXT_REFRESH);
        this.refreshButton
                .setActionCommand(CountDetailEditDialog.ACTION_COMMAND_REFRESH);
        this.refreshButton.addActionListener(this);
        this.buttonPane.add(this.refreshButton);

        this.deleteButton = new JButton(
                CountDetailEditDialog.BUTTON_TEXT_DELETE);
        this.deleteButton
                .setActionCommand(CountDetailEditDialog.ACTION_COMMAND_DELETE_COUNT);
        this.deleteButton.addActionListener(this);
        this.buttonPane.add(this.deleteButton);
        this.deleteButton.setEnabled(false);

        // Resequence the z-indexes of the buttons
        try {
            this.buttonPane.getComponentCount();
            this.buttonPane.setComponentZOrder(this.refreshButton, 0);
            this.buttonPane.setComponentZOrder(this.deleteButton, 1);
            this.buttonPane.setComponentZOrder(this.okButton, 2);
            this.buttonPane.setComponentZOrder(this.cancelButton, 3);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }

        // Modidfy window title to include header id
        String appTitle = this.getTitle();
        appTitle += " for Header Item " + this.header.getHdrId();
        this.setTitle(appTitle);
    }

    /**
     * Performs a database query to obtain a list of Counts Detail records
     * related to a header id.
     * 
     * @param headerId
     *            the Counts header id
     * @return a List of {@link CountDetail} objects
     * @throws DatabaseException
     *             Genreal database errors.
     */
    protected List<CountDetail> doQuery(int headerId) throws DatabaseException {
        List<CountDetail> list = null;
        CountsDao dao = null;
        try {
            CountsFactory f = new CountsFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchHeaderDetails(headerId);
            return list;
        } catch (DatabaseException e) {
            logger.error("Error fetching detail records for Counts header #"
                    + headerId, e);
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
    private ScrollableDataGrid createDataGrid(List<CountDetail> list) {

        // Setup column definitions
        List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
        colDefs.add(new ColumnDefinition("assocNo", "PIN", 0, 80));
        colDefs.add(new ColumnDefinition("division", "Division", 1, 80));
        colDefs.add(new ColumnDefinition("inventoryDateStr", "Inventory Date",
                2, 120));
        colDefs.add(new ColumnDefinition("dtlStatus", "Status", 3, 70));
        colDefs.add(new ColumnDefinition("qtySum", "Qty", 4, 80));

        // Set the size of the data grid component
        Dimension size = new Dimension(450, 430);
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
        List<CountDetail> items = null;
        try {
            items = this.doQuery(this.header.getHdrId());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        this.grid = this.createDataGrid(items);

        // Identify those columns that need to use special comparators for
        // sorting
        int numericCols[] = { 0, 1, 4 };
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
     * Displays a input dialog used for gathering the new inventory date from
     * the user.
     * 
     * @return {@link Date} as the new inventory date
     * @throws InvalidDataException
     *             General errors.
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        try {
            GenericDateInputDialog dialog = new GenericDateInputDialog(this,
                    new Dimension(390, 160), new Point(700, 300),
                    "Counts Detail Inventory Date Input", "Inv Date");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            Date invDate = dialog.getDate();
            return invDate;
        } catch (Exception e) {
            throw new InvalidDataException(e);
        }
    }

    /**
     * Invokes the Counts process responsible for applying udpates to the Counts
     * header and detail records based on a new inventory date as input.
     * <p>
     * Only highlighted rows that have a detail status of "F" and the associated
     * header status is "A" or allowed to be changed.
     * 
     * @return the header id that was updated or created
     * @throws LaunchPadException
     *             General database access errors.
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        // Abort is date is null which indicates the user cancelled the
        // Inventory Date Input dialog
        if (data == null) {
            return null;
        }

        SecurityToken token = UserSecurityManager.getSecurityToken();
        int hdrId = this.header.getHdrId();
        int div = this.detail.getDivision();
        int assocNo = this.detail.getAssocNo();
        int userId = token.getUserId();
        Date newInvDate = (Date) data;
        StringBuffer buf = new StringBuffer();

        // Apply updates
        CountsFactory f = new CountsFactory();
        CountsDao dao = f.getDaoInstance(token);
        try {
            Integer newHeaderId = dao.update(hdrId, div, newInvDate, assocNo,
                    userId);

            // Update the model with new quantity
            this.detail.setInventoryDate(newInvDate);
            this.grid.refreshGrid();
            this.grid.scrollToVisible(this.currentRow);

            buf.append("Counts Header Detail update was successful [header id=");
            buf.append(hdrId);
            buf.append(", new inventory date=");
            buf.append(newInvDate.toString());
            buf.append("]");
            this.msg = buf.toString();
            logger.info(this.msg);
            return newHeaderId;
        } catch (DatabaseException e) {
            buf.append("Error updating Counts Header detail [header id=");
            buf.append(hdrId);
            buf.append(", new inventory date=");
            buf.append(newInvDate.toString());
            buf.append("]");
            this.msg = buf.toString();
            logger.error(this.msg, e);
            throw new LaunchPadException(this.msg, e);
        }
    }

    /**
     * Invokes the Counts process responsible for deleting Counts data based on
     * the header id and the associate id.
     * <p>
     * Only highlighted rows that have a detail status of "F" and the associated
     * header status is "A" or allowed to be deleted.
     * 
     * @throws RuntimeException
     */
    protected void deleteCount() throws RuntimeException {
        // Can only delete those details that have a status of "F".
        if (!this.detail.getDtlStatus().equalsIgnoreCase(
                CountsConstants.STATUS_FINISHED)) {
            this.msg = "The Count is still in work and can not be deleted until the count is in finished status";
            JOptionPane.showMessageDialog(this, this.msg,
                    "Counts Delete Error", JOptionPane.OK_OPTION);
            throw new RuntimeException(this.msg);
        }
        StringBuffer buf = new StringBuffer();
        SecurityToken token = UserSecurityManager.getSecurityToken();
        int hdrId = this.header.getHdrId();
        int div = this.detail.getDivision();
        int assocNo = this.detail.getAssocNo();
        String invDate = "[Unknown]";
        try {
            invDate = GeneralUtil.formatDate(this.detail.getInventoryDate(),
                    "MM-dd-yyyy");
        } catch (Exception e) {
            logger.error(e);
        }

        buf.append("Are you sure you want to delete PIN ");
        buf.append(assocNo);
        buf.append(", Division ");
        buf.append(div);
        buf.append(", Inventory Date ");
        buf.append(invDate);
        buf.append("?");
        this.msg = buf.toString();
        int rc = JOptionPane.showConfirmDialog(this, this.msg,
                "Counts Delete Confirmation", JOptionPane.YES_NO_OPTION);
        if (rc == JOptionPane.NO_OPTION) {
            return;
        }
        buf = new StringBuffer();

        // Apply updates
        CountsFactory f = new CountsFactory();
        CountsDao dao = f.getDaoInstance(token);
        try {
            dao.delete(hdrId, assocNo);
            this.refreshList();

            buf.append("Counts Header Detail delete was successful [header id=");
            buf.append(hdrId);
            buf.append(", PIN=");
            buf.append(assocNo);
            buf.append(", Division=");
            buf.append(div);
            buf.append(", inventory date=");
            buf.append(invDate);
            buf.append("]");
            this.msg = buf.toString();
            logger.info(this.msg);
            return;
        } catch (DatabaseException e) {
            buf.append("Error deleting Counts Header detail [header id=");
            buf.append(hdrId);
            buf.append(", PIN=");
            buf.append(assocNo);
            buf.append(", Division=");
            buf.append(div);
            buf.append(", inventory date=");
            buf.append(invDate);
            buf.append("]");
            this.msg = buf.toString();
            logger.error(this.msg, e);
            throw new RuntimeException(this.msg, e);
        }
    }

    /**
     * Refreshes the data grid with Counts Detail records queried from the
     * database by the current header id.
     * 
     * @throws RuntimeException
     *             General database errors
     */
    protected void refreshList() {
        List<CountDetail> items = null;
        try {
            items = this.doQuery(this.header.getHdrId());
            this.grid.loadView(items);
            this.displayRecordCount();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stub method.
     * 
     * @param evt
     *            an instance of {@link ComponentItemDoubleClickedEvent}
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        return;
    }

    /**
     * Dynamically manages the state of the <i>Delete Count</i> and <i>Edit
     * Header</i>status buttons.
     * <p>
     * if the highlighted row has a detail status of "F", and the assoicated
     * header s tatus is "A", then both buttons are enabled. Otherwise, these
     * buttons are disabled.
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        DataGrid t = (DataGrid) evt.getSource();
        this.currentRow = t.getSelectedRow();

        CountDetail item = null;
        if (evt.getSelectedItem() == null) {
            return;
        }
        if (evt.getSelectedItem() instanceof Count) {
            item = (CountDetail) evt.getSelectedItem();
        }
        else {
            return;
        }

        // Enable/Disable "Delete Count" and "Edit Header" buttons.
        boolean okayToEdit = (item.getHdrStatus().equalsIgnoreCase(
                CountsConstants.STATUS_AVAIL) || item.getDtlStatus()
                .equalsIgnoreCase(CountsConstants.STATUS_FINISHED));
        this.deleteButton.setEnabled(okayToEdit);
        this.okButton.setEnabled(okayToEdit);

        this.detail = item;
    }

    /**
     * Handles the click actions of the <i>Refresh List</i>, and the <i>Delete
     * Count</i> buttons.
     * 
     * @param e
     *            instance of {@link ActionEvent}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getActionCommand().equalsIgnoreCase(
                CountDetailEditDialog.ACTION_COMMAND_REFRESH)) {
            this.refreshList();
        }
        if (e.getActionCommand().equalsIgnoreCase(
                CountDetailEditDialog.ACTION_COMMAND_DELETE_COUNT)) {
            this.deleteCount();
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

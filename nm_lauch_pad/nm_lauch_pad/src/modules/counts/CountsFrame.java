package modules.counts;

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
import modules.model.Count;

import com.InvalidDataException;
import com.nv.db.DatabaseException;
import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;
import com.ui.AbstractModelessWindow;
import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.event.ComponentItemSelectedEvent;
import com.ui.event.ComponentSelectionListener;
import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * The main window for managing Counts data.
 * 
 * @author rterrell
 *
 */
public class CountsFrame extends AbstractModelessWindow implements
        ComponentSelectionListener {

    private static final long serialVersionUID = -3455540655171396255L;

    private static final Logger logger = Logger.getLogger(CountsFrame.class);

    private static final String UI_DETAIL_TITLE = "Counts Detail";

    private static final String UI_DAYSBACK_TITLE = "Enter Days Back";

    private static final String UI_STATUS_TEXT_SEND = "Send";

    private static final String UI_STATUS_TEXT_REOPEN = "Reopen";

    private static final int STATUS_CHANGE_TYPE_SEND = 1;

    private static final int STATUS_CHANGE_TYPE_REOPEN = 2;

    /**
     * The action command name for the "Refresh List" button
     */
    public static final String ACTION_COMMAND_REFRESH = "REFRESH_LIST";

    /**
     * The action command name for the "Show X-mitted" button
     */
    public static final String ACTION_COMMAND_SHOWXMITT = "SHOWXMITT";

    /**
     * The action command name for the "Send" button
     */
    public static final String ACTION_COMMAND_SEND_STATUS = "SEND_STATUS";

    /**
     * The action command name for the "Reopen" button
     */
    public static final String ACTION_COMMAND_REOPEN_STATUS = "REOPEN_STATUS";

    private JButton refreshButton;

    private JButton statusButton;

    private ScrollableDataGrid grid;

    private Count selectedItem;

    /**
     * Create a CountsFrame specifying the its size, position and window title.
     * 
     * @param size
     *            the width and height of the window
     * @param pos
     *            the window's X/Y postion coordinates
     * @param title
     *            the window's title.
     */
    public CountsFrame(Dimension size, Point pos, String title) {
        super(size, pos, title);
    }

    /**
     * Performs specific constructor related tasks for this window.
     * <p>
     * Changes the text of the cancel and Ok buttons to "Exit PC List" and
     * "Close PC", respectively,. Adds the refresh button and dht find sku
     * button and arranges the order which the buttons are displayed.
     */
    @Override
    protected void initFrame() {
        // Proceed with frame initialization
        super.initFrame();
        this.cancelButton.setText("Exit List");

        // Rename the label and set the command action name of the OK button.
        this.okButton.setText("Show X-mitted");
        this.okButton.setActionCommand(CountsFrame.ACTION_COMMAND_SHOWXMITT);
        this.okButton.setEnabled(true);

        this.refreshButton = new JButton("Refresh List");
        this.refreshButton.setActionCommand(CountsFrame.ACTION_COMMAND_REFRESH);
        this.refreshButton.addActionListener(this);
        this.buttonPane.add(this.refreshButton);

        this.statusButton = new JButton(CountsFrame.UI_STATUS_TEXT_SEND);
        this.statusButton
                .setActionCommand(CountsFrame.ACTION_COMMAND_SEND_STATUS);
        this.statusButton.addActionListener(this);
        this.buttonPane.add(this.statusButton);

        // Resequence the z-indexes of the buttons
        try {
            this.buttonPane.getComponentCount();
            this.buttonPane.setComponentZOrder(this.refreshButton, 0);
            this.buttonPane.setComponentZOrder(this.statusButton, 1);
            this.buttonPane.setComponentZOrder(this.okButton, 2);
            this.buttonPane.setComponentZOrder(this.cancelButton, 3);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Query request to get all count headers as a List.
     * 
     * @return List of {@link Count}objects
     * @throws DatabaseException
     */
    protected List<Count> doQuery() throws DatabaseException {
        List<Count> list = null;
        CountsDao dao = null;
        try {
            CountsFactory f = new CountsFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchAllHeaders();
            return list;
        } catch (DatabaseException e) {
            logger.error(
                    "Error fetching All Count Header records from the database",
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
    private ScrollableDataGrid createDataGrid(List<Count> list) {

        // Setup column definitions
        List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
        colDefs.add(new ColumnDefinition("division", "Division", 0, 100));
        colDefs.add(new ColumnDefinition("inventoryDateStr", "Inventory Date",
                1, 100));
        colDefs.add(new ColumnDefinition("status", "Status", 2, 180));

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
        List<Count> list = null;
        try {
            list = this.doQuery();
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Build grid with PC data
        this.grid = this.createDataGrid(list);

        // Identify those columns that need to use special comparators for
        // sorting
        int numericCols[] = { 0 };
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
     * Implemented as a stub method.
     * 
     * @return always null
     * @throws InvalidDataException
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        return null;
    }

    /**
     * Inovkes the Counts Detail process that allows the user to Delete and Edit
     * count data.
     * <p>
     * If the program is in "show transmitted counts" mode, this action is
     * ignored.
     * 
     * @param evt
     *            an instance of {@link ComponentItemDoubleClickedEvent}
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        Count item = null;
        if (evt.getSelectedItem() == null) {
            return;
        }
        if (evt.getSelectedItem() instanceof Count) {
            item = (Count) evt.getSelectedItem();
        }
        else {
            return;
        }
        // Abort if we are in show transmit mode
        if (item.getStatus().equalsIgnoreCase("X")) {
            return;
        }

        this.openDetailWindow(item);
    }

    /**
     * Dynamically manages the state of the status button.
     * <p>
     * Based on the status of the selected row, the button's text is changed as
     * well as its action command. The following status change rules apply:
     * <ol>
     * <li>When the selected record has a status of <b>"A"</b>, the status
     * button's text will read "Send", and the button's action command is
     * changed appropriately.</li>
     * <li>When the selected record has a status of <b>"C"</b>, the status
     * button's text will read "Reopen", and the button's action command is
     * changed appropriately.</li>
     * <li>When the selected record has a status of <b>"X"</b>, the status
     * button will be disabled.</li>
     * </ol>
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        Count item = null;
        if (evt.getSelectedItem() == null) {
            return;
        }
        if (evt.getSelectedItem() instanceof Count) {
            item = (Count) evt.getSelectedItem();
        }
        else {
            return;
        }

        if (item.getStatus().equalsIgnoreCase("A")) {
            this.statusButton.setText(CountsFrame.UI_STATUS_TEXT_SEND);
            this.statusButton
                    .setActionCommand(CountsFrame.ACTION_COMMAND_SEND_STATUS);
        }
        if (item.getStatus().equalsIgnoreCase("S")) {
            this.statusButton.setText(CountsFrame.UI_STATUS_TEXT_REOPEN);
            this.statusButton
                    .setActionCommand(CountsFrame.ACTION_COMMAND_REOPEN_STATUS);
        }

        this.statusButton.setEnabled(!item.getStatus().equalsIgnoreCase(
                CountsConstants.STATUS_TRANSMIT));

        this.selectedItem = item;

    }

    /**
     * Handles the click actions of the <i>Refresh List</i>, <i>Send</i>,
     * <i>Show X-mitted</i> and <i>Exit List</i> buttons.
     * 
     * @param e
     *            instance of {@link ActionEvent}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getActionCommand().equals(CountsFrame.ACTION_COMMAND_SEND_STATUS)) {
            this.changeStatus(CountsFrame.STATUS_CHANGE_TYPE_SEND);
        }
        if (e.getActionCommand().equals(
                CountsFrame.ACTION_COMMAND_REOPEN_STATUS)) {
            this.changeStatus(CountsFrame.STATUS_CHANGE_TYPE_REOPEN);
        }
        if (e.getActionCommand().equals(CountsFrame.ACTION_COMMAND_SHOWXMITT)) {
            this.showTransmittedHeaders();
        }
        if (e.getActionCommand().equals(CountsFrame.ACTION_COMMAND_REFRESH)) {
            this.refreshList();
        }
    }

    /**
     * Handles the user's request to change the status of a selected Count
     * header.
     * <p>
     * A user message is displayed in black font stating that the status update
     * was a success. In cases regarding failures, a message is display in red
     * font to the user explaining the problem, and control is returned to the
     * Counts Admin window.
     * 
     * @param statusChangeType
     *            An int value representing the type of status change
     *            requrested. Equals "1" for send status changes and "2" for
     *            reopen status changes.
     */
    protected void changeStatus(int statusChangeType) {
        CountsDao dao = null;
        int targetRow = this.grid.getSelectedRow();
        int hdrId = this.selectedItem.getHdrId();
        String curStatus = this.selectedItem.getStatus();
        String newStatus = null;

        try {
            CountsFactory f = new CountsFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            switch (statusChangeType) {
                case CountsFrame.STATUS_CHANGE_TYPE_SEND:
                    newStatus = dao.changeStatusSend(hdrId);
                    break;

                case CountsFrame.STATUS_CHANGE_TYPE_REOPEN:
                    newStatus = dao.changeStatusReopen(hdrId);
                    break;
            }
            this.selectedItem.setStatus(newStatus);
            this.grid.refreshGrid();
            this.grid.scrollToVisible(targetRow);

            this.msg = "Status change was successful for Count Header, "
                    + hdrId;
            AppUtil.showMessage(this.msgLbl, this.msg, false);
            return;
        } catch (InvalidDataException e) {
            this.msg = e.getMessage();
            logger.error(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw e;
        } catch (DatabaseException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("Error changing status of Counts header [Header Id=");
            buf.append(hdrId);
            buf.append(", Current Status=");
            buf.append(curStatus);
            buf.append("]");
            buf.append("\n\nContact the support team for assistance");
            this.msg = buf.toString();
            logger.error(this.msg, e);
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(this.msg);
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Reloads the data grid with a list of Counts with transmitted status.
     * <p>
     * Before the list is reloaded, a dialog is displayed to allow the user to
     * enter the total number of days back from the current date in which the
     * query should pull transmitted Counts.
     */
    protected void showTransmittedHeaders() {
        Integer daysBack = 0;
        daysBack = this.openDaysBackInputDialog();

        // The user cancelled the diaolog if days is null
        if (daysBack == null) {
            return;
        }

        CountsDao dao = null;
        List<Count> list = null;
        try {
            CountsFactory f = new CountsFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchTransmittedHeaders(daysBack);
            if (list == null || list.size() == 0) {
                this.msg = "No extracted counts found in that range";
                JOptionPane.showMessageDialog(this, this.msg, "Data Not Found",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            this.grid.loadView(list);
            this.displayRecordCount();
        } catch (DatabaseException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("Error displaying Transmitted Count Header records");
            buf.append("\n\nContact the support team for assistance");
            this.msg = buf.toString();
            logger.error(this.msg, e);
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(this.msg);
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * Opens the Enter Days Back dialog for the purpose of obtaining the total
     * number of days to go back from the current date.
     * 
     * @return integer value representing the number of days
     * @throws RuntimeException
     *             Error during the time of opening or processing the
     *             TransmitDaysBackInputDialog dialog.
     */
    protected Integer openDaysBackInputDialog() {
        String inputFieldLabel = "How many days back:";
        try {
            TransmitDaysBackInputDialog dialog = new TransmitDaysBackInputDialog(
                    this, new Dimension(370, 150), new Point(800, 500),
                    CountsFrame.UI_DAYSBACK_TITLE, inputFieldLabel);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            return dialog.getQty();
        } catch (Exception e) {
            this.msg = "An error occured opening the \"Enter Days Back\" Dialog window.\n\nPlease consult the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg,
                    "\"Enter Days Back\" Input Dialog Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays the Counts Details window rendering a list of detail records
     * related to the header, <i>item</i>.
     * 
     * @param item
     *            an instance of {@link Count} representing the Counts header.
     */
    protected void openDetailWindow(Count item) {
        try {
            CountDetailEditDialog dialog = new CountDetailEditDialog(this,
                    item, new Dimension(420, 450), new Point(600, 360),
                    CountsFrame.UI_DETAIL_TITLE);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes the data grid with Counts queried from the database.
     * 
     * @throws RuntimeException
     *             General database errors
     */
    protected void refreshList() {
        List<Count> list = null;
        try {
            list = this.doQuery();
            this.grid.loadView(list);
            this.displayRecordCount();
        } catch (DatabaseException e) {
            this.msg = "An error occured refreshing the list with Count header data.\n\nPlease consult the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg,
                    "Launch Pad Input Dialog Error", JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg, e);
            throw new RuntimeException(this.msg, e);
        }
    }

    /**
     * Displays the total number of Count header records fetched from the
     * database in the message area.
     */
    @Override
    protected void displayRecordCount() {
        super.displayRecordCount();

        // Dispaly record count
        this.msg = this.grid.getTableView().getRowCount() + " records found";
        AppUtil.showMessage(this.msgLbl, this.msg, false);
    }

}

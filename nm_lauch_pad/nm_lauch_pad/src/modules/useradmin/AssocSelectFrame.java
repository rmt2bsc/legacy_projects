package modules.useradmin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import modules.LaunchPadException;

import modules.model.Assoc;

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
import com.ui.table.ScrollableDataGrid;

/**
 * The main User Aministration window providing the ability to add, modify, and
 * delete associates.
 * <p>
 * This frame utilizies a grid to present a list of all users. from there, the
 * user has the option to edit a user by double clicking a row, add a user by
 * clicking the "Add User" button, or delete a user by clicking the
 * "Delete User" button.
 * 
 * @author rterrell
 *
 */
public class AssocSelectFrame extends AbstractModelessWindow implements
        ComponentSelectionListener {

    private static final long serialVersionUID = 6321124929118717964L;

    private static final Logger logger = Logger
            .getLogger(AssocSelectFrame.class);

    public static final String ACTION_COMMAND_DELETE = "DELETE_USER";

    public static final String ACTION_COMMAND_ADD = "ADD_USER";

    public static final String UI_EDIT_TITLE = " User Information";

    private ScrollableDataGrid grid;

    protected JButton addButton;

    protected JButton deleteButton;

    protected JButton exitButton;

    /**
     * Create the frame.
     */
    public AssocSelectFrame(Dimension size, Point pos, String title) {
        super(size, pos, title);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ui.NmAbstractModelessUI#initFrame()
     */
    @Override
    protected void initFrame() {
        super.initFrame();
        this.cancelButton.setText("Exit User List");

        // Rename the label and set the command action name of the OK button.
        this.okButton.setText("Delete User");
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);

        this.addButton = new JButton("Add User");
        addButton.setActionCommand(AssocSelectFrame.ACTION_COMMAND_ADD);
        addButton.addActionListener(this);
        buttonPane.add(addButton);
        // buttonPane.add(addButton, 1);

        // Resequence the z-indexes of the buttons
        try {
            this.buttonPane.getComponentCount();
            this.buttonPane.setComponentZOrder(this.okButton, 0);
            this.buttonPane.setComponentZOrder(this.addButton, 1);
            this.buttonPane.setComponentZOrder(this.cancelButton, 2);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
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
    private ScrollableDataGrid createDataGrid() {
        List<Assoc> list = null;
        AssocDao dao = null;
        try {
            AssocDaoFactory f = new AssocDaoFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            list = dao.fetchAllUsers();
        } catch (DatabaseException e) {
            logger.error("Error fetching user list from the database", e);
        } finally {
            dao.close();
            dao = null;
        }

        // Setup column definitions
        List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
        colDefs.add(new ColumnDefinition("id", "PIN", 0, 100));
        colDefs.add(new ColumnDefinition("lastName", "Last Name", 1, 250));
        colDefs.add(new ColumnDefinition("firstName", "First Name", 2, 250));
        colDefs.add(new ColumnDefinition("securityLevel", "Level", 3, 50));

        // Set the size of the data grid component
        Dimension size = new Dimension(680, 250);
        // Create ScrollableDataGrid grid component
        ScrollableDataGrid dg = new ScrollableDataGrid(list, colDefs,
                ListSelectionModel.SINGLE_SELECTION, size);
        dg.addItemSelectionListener(this);
        return dg;
    }

    /**
     * Uses the MigLayout to add a scrollable data grid and a message area.
     */
    @Override
    public JPanel createContentLayout() {
        this.grid = this.createDataGrid();

        // Identify those columns that need to use special comparators for
        // sorting
        int numericCols[] = { 0, 3 };
        this.grid.setNumericColumnSorter(numericCols);

        // Use the MigLayout to setup window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[]", "[]20[]"));

        // Add data grid
        mainPanel.add(this.grid, "dock center, wrap");

        // Add message area
        this.msgLbl = new JLabel();
        this.msgLbl.setForeground(Color.RED);
        this.msgLbl.setVisible(false);
        mainPanel.add(this.msgLbl);

        return mainPanel;
    }

    /**
     * Obtains and validates the selected assoicate targeted for deletion.
     * <p>
     * In terms of validations, it is required that a row is selected, and the
     * data type of the object associated with the selected row is {@link Assoc}.
     * 
     * @return an instance of {@link Assoc}
     * @throws InvalidDataException
     *             The user did not select an associate profile or the data
     *             object associated with the selected is not of type,
     *             {@link Assoc}.
     */
    @Override
    public Assoc getInputData() throws InvalidDataException {
        Object item = this.grid.getSelectedItem();
        // Ensure that a row was selected.
        if (item == null) {
            this.msg = "The User Delete operation requires an associate profile to be selected from list";
            logger.error(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new InvalidDataException(this.msg);
        }

        // Verify that the selected model object is of type Assoc.
        if (item instanceof Assoc) {
            return (Assoc) item;
        }
        else {
            StringBuffer buf = new StringBuffer();
            buf.append("Error deleting Associate profile due the selected model item is of the wrong data type.   Found an instance of ");
            buf.append(item.getClass().getName());
            buf.append("; expected an instance of ");
            buf.append(Assoc.class.getName());
            buf.append(".  Contact the technical support team for assistence");
            this.msg = buf.toString();
            logger.error(this.msg);
            JOptionPane.showMessageDialog(this, this.msg, this.getTitle()
                    + " Error Message", JOptionPane.ERROR_MESSAGE);
            throw new InvalidDataException(this.msg);
        }
    }

    /**
     * Deletes the assoicate profile from the database.
     * <p>
     * The user is required to select an associate from the list and click the
     * “Delete User” button. If the selected associate's <i>assoc_type</i> field
     * equals "S", then a popup window is displayed with the message, “Cannot
     * Delete A System User”. Once the user acknowleges the popup message, the
     * user is returned to the User Admin window. Otherwise, the program will
     * ask "are you sure about the deletion?". If the user selects the “No”
     * button, the program returns control to the Associate User Admin window
     * with the previously selected record still highlighted. If the user clicks
     * the “Yes” button, the program will delete the selected associate, and
     * refresh the User Admin. At this point no rows should be highlighted.
     * 
     * @param data
     *            an instance of {@link Assoc} representing the associate
     *            profile to delete.
     * @return the deleted assoicate profile which is an instance of
     *         {@link Assoc}
     * @throws LaunchPadException
     */
    @Override
    public Assoc processData(Object data) throws LaunchPadException {
        Assoc user = (Assoc) data;
        int selectedRow = this.grid.getSelectedRow();

        // You cannot delete an associate's account that is currently logged in
        int sessionAssocId = UserSecurityManager.getSecurityToken().getUserId();
        if (user.getId() == sessionAssocId) {
            this.msg = "Cannot delete the account of an associate who is currently logged into the system";
            JOptionPane.showMessageDialog(this, this.msg, this.getTitle()
                    + " Error Message", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // System user cannot be deleted.
        if (user.getAssocType() != null
                && user.getAssocType().equalsIgnoreCase("S")) {
            this.msg = "Cannot delete a system user";
            JOptionPane.showMessageDialog(this, this.msg, this.getTitle()
                    + " Error Message", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        this.msg = "Assocate " + user.getId()
                + " is selected to be deleted.  Are you sure?";
        String title = this.getTitle() + " Delete Confirmation";
        int rc = JOptionPane.showConfirmDialog(this, this.msg, title,
                JOptionPane.YES_NO_OPTION);
        if (rc == 0) {
            AssocDao dao = null;
            try {
                AssocDaoFactory f = new AssocDaoFactory();
                dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
                int rows = dao.deleteUser(user);
                this.grid.removeGridRow(selectedRow);
                logger.info("Total number of rows effected by User Admin Delete operation: "
                        + rows);
            } catch (DatabaseException e) {
                this.msg = "Unable to add user to the system due to database access error";
                throw new LaunchPadException(this.msg, e);
            }
        }
        return null;
    }

    /**
     * Handles the click actions of the <i>Delete User</i> <i>Add User</i> and
     * <i>Exut User List</i> buttons.
     * 
     * @param e
     *            instance of {@link ActionEvent}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getActionCommand().equals(AssocSelectFrame.ACTION_COMMAND_ADD)) {
            Assoc newUser = new Assoc();
            this.openAssoicateEditWindow(newUser);
        }
    }

    /**
     * Prepare to modify an existing associate profile as a result of the user
     * double clicking a data grid row.
     * 
     * @param evt
     *            an instance of {@link ComponentItemDoubleClickedEvent}
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        Assoc user = null;
        if (evt.getSelectedItem() instanceof Assoc) {
            user = (Assoc) evt.getSelectedItem();
            this.openAssoicateEditWindow(user);
        }
        else {
            StringBuffer buf = new StringBuffer();
            buf.append("Error editing Associate profile due the selected model item is of the wrong data type.   Found an instance of ");
            buf.append(evt.getSelectedItem().getClass().getName());
            buf.append("; expected an instance of ");
            buf.append(Assoc.class.getName());
            buf.append(".  Contact the technical support team for assistence");
            this.msg = buf.toString();
            logger.error(this.msg);
            JOptionPane.showMessageDialog(this, this.msg, this.getTitle()
                    + " Error Message", JOptionPane.ERROR_MESSAGE);
            throw new InvalidDataException(this.msg);
        }
        this.msgLbl.setVisible(false);
    }

    /**
     * Opens the {@link AssocEditDialog} for the purpose of adding a new or
     * modifying an existing associate.
     * <p>
     * When the id property of <i>user</i> is equal to zero, the dialog is
     * opened "add" mode. When the id property is greater than zero, the dialog
     * is opened in "edit" mode.
     * 
     * @param user
     *            the object model representing the user selected from list
     */
    private void openAssoicateEditWindow(Assoc user) {
        String editTitle = null;
        boolean editMode = true;
        if (user.getId() == 0) {
            editTitle = "Add " + AssocSelectFrame.UI_EDIT_TITLE;
            editMode = false;
        }
        else {
            editTitle = "Edit" + AssocSelectFrame.UI_EDIT_TITLE;
        }
        try {
            AssocEditDialog dialog = new AssocEditDialog(this, user,
                    new Dimension(400, 250), new Point(700, 300), editTitle);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setEditMode(editMode);
            dialog.setVisible(true);

            Assoc modUser = dialog.getModifiedUser();
            // Abort if user cancelled dialog
            if (modUser == null) {
                return;
            }
            if (editMode) {
                // Now, we can update the model since the input data passed
                // validations and was successfully updated.
                user.setId(modUser.getNewId());
                user.setFirstName(modUser.getFirstName());
                user.setMidInit(modUser.getMidInit());
                user.setLastName(modUser.getLastName());
                user.setSecurityLevel(modUser.getSecurityLevel());
                user.setPassword(modUser.getPassword());
                int rowIndex = this.grid.getSelectedRow();
                this.grid.refreshGrid();
                this.grid.scrollToVisible(rowIndex);
            }
            else {
                modUser.setId(modUser.getNewId());
                this.grid.addGridRow(modUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Implemented as a stub method.
     * 
     * @param evt
     *            an instance of {@link ComponentItemSelectedEvent}
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        return;
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

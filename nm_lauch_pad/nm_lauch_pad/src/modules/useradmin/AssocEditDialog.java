package modules.useradmin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modules.LaunchPadException;
import modules.model.Assoc;
import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import com.DuplicateKeyException;
import com.InvalidDataException;

import com.nv.db.DatabaseException;
import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;
import com.nv.util.GeneralUtil;
import com.ui.AbstractModalWindow;
import com.ui.WindowProcessTemplateListener;

/**
 * Dialog for creating new or modifying existing Associates.
 * 
 * @author rterrell
 *
 */
public class AssocEditDialog extends AbstractModalWindow {

    private static final long serialVersionUID = -4321899934172262013L;

    private static final Logger logger = Logger
            .getLogger(AssocEditDialog.class);

    private boolean editMode;

    private Assoc model;

    private JTextField pinTxt;

    private JTextField fnTxt;

    private JTextField midInitTxt;

    private JTextField lnTxt;

    private JTextField accessLvlTxt;

    private JTextField pwTxt;

    private Assoc modifiedUser;

    /**
     * Create a AssocEditDialog with know parent widnow, the assoicated object
     * to edit, the dialog size dimensions, the dialog position coordinates, and
     * the dialog title.
     * 
     * @param owner
     *            the parent window reference
     * @param user
     *            The Associate to add or change.
     * @param size
     *            The window size coordinates
     * @param pos
     *            The window position coordinates
     * @param winTitle
     *            The window title.
     */
    public AssocEditDialog(Frame owner, Assoc user, Dimension size, Point pos,
            String winTitle) {
        super(owner, user, size, pos, winTitle);
    }

    /**
     * Performs specific initializations for this dialog which maintains the
     * default OK and Cancel buttons.
     * <p>
     * By default, this dialog is in edit mode.
     */
    @Override
    protected void initDialog() {
        super.initDialog();
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS_CLOSE);
        this.okButton.setText("Update");
        this.editMode = false;
        this.setResizable(false);
    }

    /**
     * Uses the MigLayout to create the Assocate form containing labels and
     * assocated text input fields for editing PIN, first name, last name,
     * middle intial, access level, passoword, and a message area.
     * <p>
     * 
     */
    @Override
    public JPanel createContentLayout() {
        this.model = (Assoc) this.inData;
        // Use the MigLayout to setup window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[][]", "[][][][][][][]20[]"));

        JLabel pinLbl = new JLabel("Associate PIN:");
        this.pinTxt = GeneralUtil.createMaskedTextField("######");
        if (this.model.getId() > 0) {
            this.pinTxt.setText(String.valueOf(this.model.getId()));
        }
        mainPanel.add(pinLbl);
        mainPanel.add(this.pinTxt, "width 180:180:180, wrap");

        JLabel fnLbl = new JLabel("First Name:");
        this.fnTxt = GeneralUtil.createMaskedTextField("***************");
        this.fnTxt.setText(this.model.getFirstName() == null ? null
                : this.model.getFirstName().trim());
        mainPanel.add(fnLbl);
        mainPanel.add(this.fnTxt, "width 180:180:180, wrap");

        JLabel midInitLbl = new JLabel("Middle Initial:");
        this.midInitTxt = GeneralUtil.createMaskedTextField("A");
        this.midInitTxt.setText(this.model.getMidInit() == null ? null
                : this.model.getMidInit().trim());
        mainPanel.add(midInitLbl);
        mainPanel.add(this.midInitTxt, "width 180:180:180, wrap");

        JLabel lnLbl = new JLabel("Last Name:");
        this.lnTxt = GeneralUtil.createMaskedTextField("********************");
        this.lnTxt.setText(this.model.getLastName() == null ? null : this.model
                .getLastName().trim());
        mainPanel.add(lnLbl);
        mainPanel.add(this.lnTxt, "width 180:180:180, wrap");

        JLabel accessLvlLbl = new JLabel("Access Level:");
        this.accessLvlTxt = GeneralUtil.createMaskedTextField("#");
        this.accessLvlTxt.setText(this.model.getSecurityLevel() == 0 ? ""
                : String.valueOf(this.model.getSecurityLevel()));
        mainPanel.add(accessLvlLbl);
        mainPanel.add(this.accessLvlTxt, "width 180:180:180, wrap");

        JLabel pwLbl = new JLabel("Password:");
        this.pwTxt = GeneralUtil.createMaskedTextField("######");
        this.pwTxt.setText(this.model.getPassword() == null ? null : this.model
                .getPassword().trim());
        mainPanel.add(pwLbl);
        mainPanel.add(this.pwTxt, "width 180:180:180, wrap");

        this.msgLbl = new JLabel();
        this.msgLbl.setForeground(Color.RED);
        mainPanel.add(msgLbl, "span 2, wrap");

        return mainPanel;
    }

    /**
     * Accepts the input field values, validates the input data, packages and
     * retuns the input as a Assoc object.
     * <p>
     * This window is capable of handling validation errors and general database
     * errors. The user is notified of validation errors in the message area,
     * and the database errors are displayed via a popup dialog.
     * 
     * @return an instance of {@link Assoc} containing the validated input data.
     * @throws InvalidDataException
     *             validation errors
     * @throws RuntimeException
     *             general database errors.
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        // Use try/catch to capture validation errors and
        // present to the user via a component on the display.
        try {
            Assoc vo = this.validateInputData();
            return vo;
        } catch (InvalidDataException e) {
            AppUtil.showMessage(this.msgLbl, e.getMessage(), true);
            throw e;
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "User Admin Validateion Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates the input data.
     * 
     * @return an instance of {@link Assoc} containing the validated input
     *         values.
     * @throws InvalidDataException
     *             a validation error was found.
     * @throws DatabaseException
     *             database access error
     */
    private Assoc validateInputData() throws InvalidDataException,
            DatabaseException {
        String intStr;
        int pin = 0;
        String fn = null;
        String ln = null;
        String pw = null;
        int secureLevel = 0;

        // Validate PIN
        try {
            intStr = this.pinTxt.getText();
            if (intStr == null || intStr.equals("")
                    || intStr.equals(GeneralUtil.spaces(intStr.length()))) {
                this.msg = "Assoicate PIN is required";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }
            pin = Integer.parseInt(this.pinTxt.getText());
            if (this.pinTxt.getText().length() != 6) {
                this.msg = "Assoicate PIN must be a 6 digit number";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }

        } catch (NumberFormatException e) {
            this.msg = "Assoicate PIN must be numeric";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }

        // Validate First Name
        fn = this.fnTxt.getText();
        if (fn == null || fn.equals("")
                || fn.equals(GeneralUtil.spaces(fn.length()))) {
            this.msg = "Assoicate first name is required";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }

        // Validate Last Name
        ln = this.lnTxt.getText();
        if (ln == null || ln.equals("")
                || ln.equals(GeneralUtil.spaces(ln.length()))) {
            this.msg = "Assoicate last name is required";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }

        // Validate security level
        try {
            intStr = this.accessLvlTxt.getText();
            if (intStr == null || intStr.equals("")
                    || intStr.equals(GeneralUtil.spaces(intStr.length()))) {
                this.msg = "Assoicate security level is required";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }
            secureLevel = Integer.parseInt(this.accessLvlTxt.getText());
            if (secureLevel < 0 || secureLevel > 3) {
                this.msg = "Assoicate security level must be a value between 0 and 3";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }
        } catch (NumberFormatException e) {
            this.msg = "Assoicate security level must be numeric";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }

        // Validate password
        pw = this.pwTxt.getText();
        if (pw == null || pw.equals("")
                || pw.equals(GeneralUtil.spaces(pw.length()))) {
            this.msg = "Assoicate password is required";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        if (pw.length() < 4 || pw.length() > 6) {
            this.msg = "Assoicate password must contain 4 to 6 alphanumeric characters";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }

        // Veify that PIN is unigue in both edit and add scenarios
        Assoc user = new Assoc();
        user.setId(this.model.getId());
        int uniquePin = 0;
        try {
            user.setNewId(pin);
            uniquePin = this.doPinDuplicateCheck(user);
            user.setNewId(uniquePin);
        } catch (DuplicateKeyException e) {
            throw new InvalidDataException(e.getMessage(), e);
        }

        // Add values to model object and return to caller for further
        // processing
        user.setFirstName(fn);
        user.setMidInit(this.midInitTxt.getText());
        user.setLastName(ln);
        user.setSecurityLevel(secureLevel);
        user.setPassword(pw);
        return user;
    }

    /**
     * This method persists the changes of an associate to the database.
     * <p>
     * Modifications of an existing associate as well as the creation of a new
     * associate are handled here. Once the database changes have been persisted
     * successfully, the model data object is updated with changes in order to
     * be reflected in the parent view.
     * 
     * @param data
     *            an arbitrary object acting as the input data to be processed.
     * @return an arbitary object representing the results of processing input
     *         data or null if there is nothing to be returned.
     * @throws LaunchPadException
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        // Save the data
        Assoc user = (Assoc) data;
        AssocDao dao = null;
        try {
            AssocDaoFactory f = new AssocDaoFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());

            if (this.editMode) {
                int rows = dao.modifyUser(user);
                logger.info("Total number of rows effected by Assoicate update operation: "
                        + rows);
            }
            else {
                int newId = dao.addUser(user);
                logger.info("New Assoicate was added sucessfully.  Assoicate Id:"
                        + newId);
            }
        } catch (DatabaseException e) {
            this.msg = "Unable to add user to the system due to database access error";
            throw new LaunchPadException(this.msg, e);
        }

        this.modifiedUser = user;

        // // Now, we can update the model since the input data passed
        // validations and was successfully updated.
        // this.model.setId(user.getNewId());
        // this.model.setFirstName(user.getFirstName());
        // this.model.setMidInit(user.getMidInit());
        // this.model.setLastName(user.getLastName());
        // this.model.setSecurityLevel(user.getSecurityLevel());
        // this.model.setPassword(user.getPassword());

        return user;
    }

    /**
     * Verifies whether or not the PIN is unique to the those that exist in the
     * database.
     * 
     * @param user
     *            The user containing the PIN targeted for verification.
     * @return an value greater than zero indicating the PIN is unique
     * @throws DuplicateKeyException
     *             the PIN is not unique
     * @throws DatabaseException
     *             database access errors.
     */
    private int doPinDuplicateCheck(Assoc user) throws DuplicateKeyException,
            DatabaseException {
        int pinToCheck = 0;
        if (this.editMode) {
            if (user.getId() == user.getNewId()) {
                // PIN was not changed...no need to perform dup check.
                return user.getId();
            }
            else {
                // PIN was changed...dup check required
                pinToCheck = user.getNewId();
            }
        }
        else {
            // Always perform a dup check when adding an associate
            pinToCheck = user.getNewId();
        }

        // Perform the actual dup check
        AssocDaoFactory f = new AssocDaoFactory();
        AssocDao dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
        try {
            if (dao.fetch(pinToCheck) != null) {
                this.msg = "The associate PIN, " + pinToCheck
                        + ", already exists";
                logger.error(this.msg);
                throw new DuplicateKeyException(this.msg);
            }
        } finally {
            dao.close();
            dao = null;
        }

        return pinToCheck;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ui.NmAbstractModalUI#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    /**
     * @return the editMode
     */
    public boolean isEditMode() {
        return editMode;
    }

    /**
     * @param editMode
     *            the editMode to set
     */
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    /**
     * @return the modifiedUser
     */
    public Assoc getModifiedUser() {
        return modifiedUser;
    }

}

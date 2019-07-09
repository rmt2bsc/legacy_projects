package modules.authentication;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import com.InvalidDataException;

import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;
import com.ui.AbstractModalWindow;
import com.ui.WindowProcessTemplateListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modules.LaunchPadException;
import modules.model.Assoc;
import modules.useradmin.AssocDao;
import modules.useradmin.AssocDaoFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.JPasswordField;

import org.apache.log4j.Logger;

/**
 * This is the user login screen.
 * <p>
 * Before allowing the associate to perform user administration, the program
 * must validate supervisor credentials via a login dialog box. The user must
 * provide a supervisor-level ID and password to access this functionality. The
 * dialog box displays the Associate ID as it is being typed, and a series of
 * asterisks for each character input for the password.
 * 
 * @author rterrell
 *
 */
// public class UserLoginDialog extends AbstractModalWindow implements
// KeyListener, CaretListener {
public class UserLoginDialog extends AbstractModalWindow {

    private static final long serialVersionUID = 1062235214399626383L;

    private static final Logger logger = Logger
            .getLogger(UserLoginDialog.class);

    private static final String PROP_MIN_SECURE_LEVEL = "minSecurityLevel";

    public static final int PASSWORD_MAX_LEN = 6;

    private JTextField userIdField;

    private JPasswordField passwordField;

    private int minSecurityLevel;

    /**
     * Cerate a UserLoginDialog object with a known parent, window size
     * properties, window location coordinates, and the window title.
     * 
     * @param owner
     *            the parent window
     * @param size
     *            the size coordinates
     * @param pos
     *            the location coordinates
     * @param winTitle
     *            the title of the window.
     */
    public UserLoginDialog(Frame owner, Dimension size, Point pos,
            String winTitle) {
        super(owner, null, size, pos, winTitle);
        return;
    }

    /**
     * Obtains the minimum security level setting fromteh application.properties
     * and perorms common and specific intialization tasks.
     * 
     * @throws InvalidDataException
     *             problem arises application configuration or the minimum
     *             security level setting.
     */
    @Override
    protected void initDialog() {
        super.initDialog();

        okButton.setText("Login");
        okButton.setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS_CLOSE);

        // Get minimum security level from application.properties
        try {
            String temp = AppUtil
                    .getAppProperty(UserLoginDialog.PROP_MIN_SECURE_LEVEL);
            this.minSecurityLevel = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            StringBuffer buf = new StringBuffer();
            buf.append("Invalid value found for minimumn security level property, ");
            buf.append(UserLoginDialog.PROP_MIN_SECURE_LEVEL);
            buf.append(".\nThis property must exist in the configuation, application.properties, and it must be numeric!");
            buf.append("\n\nPlease contact the support team for assistence.");
            this.msg = buf.toString();
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }

        this.setResizable(false);
        return;
    }

    /**
     * Uses the MigLayout to setup the dialog controls
     */
    @Override
    public JPanel createContentLayout() {
        // Use the MigLayout to setup window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[][][grow]", "[][]15[]"));
        JLabel lblNewLabel = new JLabel("User Id");
        mainPanel.add(lblNewLabel, "cell 1 0,alignx right");

        this.userIdField = new JTextField();
        mainPanel.add(this.userIdField, "cell 2 0,growx");

        JLabel lblNewLabel_1 = new JLabel("Password");
        mainPanel.add(lblNewLabel_1, "cell 1 1,alignx right");
        this.passwordField = new JPasswordField();
        mainPanel.add(this.passwordField, "cell 2 1,growx");

        this.msgLbl = new JLabel("Error Message Area");
        this.msgLbl.setVisible(false);
        mainPanel.add(this.msgLbl, "cell 1 2 3 1");
        return mainPanel;
    }

    /**
     * Accepts, validates, packages, and returns the input login id and password
     * as a Assoc object.
     * <p>
     * Validation errors are commumicated to the user in the message area.
     * 
     * @return an instance of {@link Assoc} containing the user's login id and
     *         password.
     * @throws InvalidDataException
     *             validation failure
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        try {
            this.validateInput();
        } catch (InvalidDataException e) {
            AppUtil.showMessage(this.msgLbl, e.getMessage(), true);
            logger.error("Login validation errors were found", e);
            throw e;
        }
        Assoc vo = new Assoc();
        int userId = Integer.parseInt(this.userIdField.getText());
        vo.setId(userId);
        String pw = String.valueOf(this.passwordField.getPassword());
        vo.setPassword(pw);
        return vo;
    }

    /**
     * Authenticates the user by querying the database with the user's
     * credentials stored in <i>data</i>.
     * <p>
     * 
     * @param data
     *            an instance of {@link Assoc} at runtime.
     * @return the security level of the user
     * @throws LaunchPadException
     *             when the user's security level is not equivalent to a
     *             supervisor or the user's login and/or password is invalid.
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        Assoc user = (Assoc) data;
        Integer lvl = null;
        AssocDaoFactory f = null;
        AssocDao dao = null;
        try {
            f = new AssocDaoFactory();
            dao = f.getDaoInstance(UserSecurityManager.getSecurityToken());
            lvl = dao.authenticateUser(user);
            // Only RF Admins and supervisors are allowed to access this
            // application
            if (lvl != this.minSecurityLevel) {
                this.msg = "You must have supervisor rights to access this application";
                logger.error(this.msg);
                AppUtil.showMessage(this.msgLbl, this.msg, true);
                throw new LaunchPadException(this.msg);
            }
        } catch (UserAuthenticationException e) {
            this.msg = e.getMessage();
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            logger.error("Login authentication errors were found", e);
            throw new LaunchPadException(e);
        } finally {
            dao.close();
            dao = null;
            f = null;
        }

        // User was verified successfully. Now update the security token of
        // valid user
        user.setSecurityLevel(lvl);
        UserSecurityManager s = new UserSecurityManager();
        s.updateSecurityToken(user);

        return lvl;
    }

    /**
     * Validates the user id and password input values.
     * 
     * @throws InvalidDataException
     *             <ul>
     *             <li>User id is not entered</li>
     *             <li>User id exceeds the maximum number of 6 characters</li>
     *             <li>User id is not numeric</li>
     *             <li>Password is not entered</li>
     *             <li>Password exceeds the maximum number of 6 characters</li>
     *             </ul>
     * 
     */
    private void validateInput() throws InvalidDataException {
        if (this.userIdField.getText() == null
                || this.userIdField.getText().length() == 0) {
            this.msg = "User Id is required";
            UserLoginDialog.logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        if (this.userIdField.getText().length() > 6) {
            this.msg = "User Id is required to contain a maximum of 6 characters";
            UserLoginDialog.logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }

        try {
            Integer.parseInt(this.userIdField.getText());
        } catch (NumberFormatException e) {
            this.msg = "User Id must be numeric";
            UserLoginDialog.logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }

        String pw = String.valueOf(this.passwordField.getPassword());
        if (pw == null || pw.equals("")) {
            this.msg = "Password is required";
            UserLoginDialog.logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        if (pw.length() > 6) {
            this.msg = "Password is required to contain a maximum of 6 characters";
            UserLoginDialog.logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        return;
    }

}

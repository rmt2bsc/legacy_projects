package modules;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.SQLException;

import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

import javax.swing.border.EmptyBorder;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modules.authentication.UserLoginDialog;

import modules.counts.CountsFrame;

import modules.idt.IdtFrame;

import modules.priceaudit.PriceAuditFrame;
import modules.pricechange.PriceChangeFrame;

import modules.report.ReportRequestFrame;

import modules.rtv.bi.BIRtvFrame;

import modules.rtv.si.SIRtvFrame;

import modules.transfer.bi.BITransferFrame;

import modules.transfer.si.SITransferFrame;

import modules.useradmin.AssocSelectFrame;

import org.apache.log4j.Logger;

import com.InvalidDataException;
import com.TooManyInstancesException;

import com.ibatis.sqlmap.client.SqlMapClient;

import com.nv.db.DatabaseException;
import com.nv.security.UserSecurityException;
import com.nv.security.UserSecurityManager;

import com.nv.util.AppInitException;
import com.nv.util.AppUtil;

/**
 * This is the main class and the main entry point of the Launch Pad
 * application.
 * <p>
 * The main purpose of this class is to provide the user a main menu to navigate
 * and manage the system. Upon startup, it is the main resposibility of this
 * object to initialize the application environment, select a store/server from
 * the store list in the event the site manages multiple stores, establish a
 * connection to the database, and verify that the database connection is in
 * deed valid.
 * <p>
 * Each menu item is internally idenified by a process id.
 * 
 * @author rterrell
 *
 */
public class LaunchPad extends JFrame implements ListSelectionListener {

    private static final long serialVersionUID = 7379495677481266380L;

    private static final String UI_TITLE_USERADMIN = "User Admin";

    private static final String UI_TITLE_PRICECHANGE = "Price Change List";

    private static final String UI_TITLE_PRICEAUDIT = "Price Audit Admin";

    private static final String UI_TITLE_SITRANSFER = "SI Transfer Admin";

    private static final String UI_TITLE_BITRANSFER = "BI Transfer Admin";

    private static final String UI_TITLE_BIRTV = "BI RTV Admin";

    private static final String UI_TITLE_SIRTV = "SI RTV Admin";

    private static final String UI_TITLE_COUNTS = "Counts Admin";

    private static final String UI_TITLE_IDT = "IDT Admin";

    private static final String UI_TITLE_REPORTS = "Reprint Reports";

    private static final int FRAME_X_VALUE = 300;

    /**
     * The id of the Exit menu item
     */
    public static final int PROCESS_EXIT = -1;

    /**
     * The id of the Login menu item
     */
    public static final int PROCESS_LOGIN = 100;

    /**
     * The id of the User Administration menu item.
     */
    public static final int PROCESS_USER = 101;

    /**
     * The id of the Price Change Administration menu item
     */
    public static final int PROCESS_PRICE_CHG = 102;

    /**
     * The id of the Price Audit Administration menu item
     */
    public static final int PROCESS_PRICE_AUDIT = 103;

    /**
     * The id of the Store Initiated Transaction Administration menu item
     */
    public static final int PROCESS_SI_TRANS = 104;

    /**
     * The id of the Buyer Initiated Transaction Administration menu item
     */
    public static final int PROCESS_BI_TRANS = 105;

    /**
     * The id of the Store Initiated RTV Administration menu item
     */
    public static final int PROCESS_SI_RTV = 106;

    /**
     * The id of the Buyer Initiated RTV Administration menu item
     */
    public static final int PROCESS_BI_RTV = 107;

    /**
     * The id of the Print/Reprint Reports menu item
     */
    public static final int PROCESS_REPORTS = 108;

    /**
     * The id of the Count Administration menu item
     */
    public static final int PROCESS_COUNTS = 109;

    /**
     * The id of the IDT Administration menu item
     */
    public static final int PROCESS_IDT = 110;

    /**
     * The id of the Load Telnet Hex File menu item
     */
    public static final int PROCESS_HEX_FILE = 111;

    /**
     * The id of the User Logout menu item
     */
    public static final int PROCESS_LOGOUT = 112;

    private static Logger logger;

    private JPanel contentPane;

    private JList menu;

    private AppUtil app;

    private String msg;

    private MainMenuItem selMenuItem;

    private DatabaseVerificationWorker worker;

    /**
     * A Worker Thread inner class used to verify the database connection of the
     * selected server for the site.
     * 
     * @author rterrell
     *
     */
    private class DatabaseVerificationWorker extends SwingWorker<Void, Void> {
        private LaunchPad parent;

        protected DatabaseVerificationWorker(LaunchPad frame) {
            this.parent = frame;
        }

        @Override
        protected Void doInBackground() throws Exception {
            SqlMapClient con = UserSecurityManager.getSecurityToken()
                    .getDbCon();
            try {
                con.queryForObject("UserAdmin.userCount");
                LaunchPad.logger
                        .info("The database connection of the selected store was verified successfully");
            } catch (SQLException e) {
                StringBuffer buf = new StringBuffer();
                buf.append("An error occurred during an attempt to verify the database connection of the selected store.\n");
                buf.append("If this is a multi-server site and the server was user-selected, try selecting the correct server.  ");
                buf.append("Otherwise consult the application support team.\n\n");
                buf.append("Additional technical information:\n");
                buf.append(e.getMessage());
                LaunchPad.logger
                        .fatal("Launch Pad Application failed to start due to error verifying database connectivity!");
                LaunchPad.logger.fatal(buf, e);
                JOptionPane.showMessageDialog(this.parent, buf.toString(),
                        "Launch Pad Error Message", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

            // Get IDT Administration flag from the tran_ctr table.
            try {
                LaunchPad.logger
                        .info("Get IDT Administration permissions flag from the tran_ctr table");
                GeneralDao dao = new GeneralDaoImpl(
                        UserSecurityManager.getSecurityToken());
                boolean idtExist = dao
                        .hasIdtAdminAccess(GeneralDao.IDT_ADMIN_FLAG_VALUE);
                if (idtExist) {
                    AppUtil.addAppProperty(GeneralDao.IDT_ADMIN_FLAG_VALUE,
                            "true");
                }
            } catch (DatabaseException e) {
                StringBuffer buf = new StringBuffer();
                buf.append("Error occurred trying to obtain the IDT Adminsitration permissions from the tran_ctr table.\n\n");
                buf.append("Consult the application support team.\n\n");
                buf.append("Additional technical information:\n");
                buf.append(e.getMessage());
                JOptionPane.showMessageDialog(this.parent, buf.toString(),
                        "Launch Pad Error Message", JOptionPane.ERROR_MESSAGE);

                LaunchPad.logger
                        .error("Launch Pad Application failed to start due to error verifying IDT Administration permissions!");
                System.exit(1);
            }
            return null;
        }

    }

    /**
     * Method for launching the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                String message = null;
                ;
                boolean error = false;
                LaunchPad frame = new LaunchPad();
                frame.setTitle("Launch Pad");
                frame.setVisible(true);
                try {
                    frame.initializedApp();
                } catch (LaunchPadException e) {
                    message = e.getMessage();
                    error = true;
                    e.printStackTrace();
                    if (LaunchPad.logger != null) {
                        LaunchPad.logger.error(message, e);
                    }
                    else {
                        System.out.println(message);
                    }
                    JOptionPane.showMessageDialog(frame, message,
                            "Launch Pad Error Message",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    if (error) {
                        LaunchPad.logger
                                .error("Launch Pad Application failed to start!");
                        System.exit(1);
                    }
                    else {
                        LaunchPad.logger
                                .info("Launch Pad Application started successfully!");
                        frame.pack();
                    }
                }
            }
        });
    }

    /**
     * Default constructor which creates the main frame.
     */
    public LaunchPad() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        setIconImage(AppUtil.getAppIcon().getImage());
    }

    /**
     * Performs initialization in terms of the logger, setting up environment
     * variables, creating security token, and intializing iBatis.
     * 
     * @throws LaunchPadException
     *             When any phase of the intialization process errors.
     */
    protected void initializedApp() throws LaunchPadException {
        this.app = new AppUtil();
        try {
            // init logger
            this.app.initLogger();
            LaunchPad.logger = Logger.getLogger(LaunchPad.class);
            LaunchPad.logger.info("Logger initialization completed");

            // Get list of store number/server name entries
            this.app.initStoreList();
            LaunchPad.logger.info("Fetched store list successfully");

            // Get the site server name
            String serverName = this.getSiteServerName();
            LaunchPad.logger.info("The selected server: " + serverName);

            // Create selected site's configuration properties file
            this.createSiteConfig(serverName);
            LaunchPad.logger.info("Site configuration created successfully");

            // Render Main Menu
            ListModel model = new MainMenuListModelImpl();
            MainMenuCellRenderer cellRenderer = new MainMenuCellRenderer();
            this.menu = new JList(model);
            ListSelectionModel selModel = new DefaultListSelectionModel();
            selModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.menu.setSelectionModel(selModel);
            this.menu.setCellRenderer(cellRenderer);
            this.menu.addListSelectionListener(this);
            this.menu.addMouseListener(new MainMenuMouseAdapter());

            // Add menu to main panel
            this.contentPane.add(this.menu, BorderLayout.CENTER);

            // Set application title.
            String appTitle = AppUtil.getAppProperty(AppUtil.PROP_APP_TITLE);
            String server = UserSecurityManager.getSecurityToken().getServer();
            appTitle += " " + server.toUpperCase();
            this.setTitle(appTitle);

            // Validate database connection
            this.worker = new DatabaseVerificationWorker(this);
            this.worker.execute();
        } catch (AppInitException e) {
            logger.error("Unable to start Launch Pad Application due to pre initialization errors");
            msg = e.getMessage()
                    + "\nConsult application support team and logs";
            throw new LaunchPadException(msg, e);
        }
    }

    /**
     * Retreives the name of the server for the site.
     * <p>
     * The source of this information is required to exist as a static
     * List<StoreListItem> variable in the AppUtil class. This listing can
     * contain one to many entries which each entry is comprised of a store
     * number and server name. When a single entry is found, that entry will be
     * used by default. When multiple entries exist, a UI dialog is presented
     * for the purpose of requiring the user to select a store/server.
     * 
     * @throws LaunchPadException
     *             Any application errors that can be corrected by the user.
     * @throws AppInitException
     *             Any system related errors that are beyond the user's control
     *             and requires support team involvment.
     */
    protected String getSiteServerName() throws LaunchPadException,
            AppInitException {
        String serverName = null;
        List<StoreListItem> list = this.app.getStoreList();

        // Invoke UI that will force user to select a store server when more
        // than one exists
        StoreListItem item = null;
        if (list.size() > 1) {
            StoreListDialog dialog = null;
            dialog = new StoreListDialog("Launch Pad - Store Selection Form",
                    list);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(300, 200);
            dialog.setModal(true);
            dialog.setVisible(true);
            item = dialog.getSelection();
            if (item == null) {
                msg = "The program is aborting since the current site is configured for multiple stores and a store was not selected.";
                logger.error(msg);
                throw new LaunchPadException(msg);
            }
            serverName = item.getServer();
        }
        else if (list.size() == 1) {
            item = list.get(0);
            serverName = item.getServer();
        }

        if (serverName == null) {
            this.msg = "Initialization process was not able to determine stie server";
            logger.error(this.msg);
        }
        else {
            this.msg = "The store/server selected: [Store=" + item.getStoreNo()
                    + "] [Server=" + item.getServer() + "]";
            logger.info(msg);
        }
        return serverName;
    }

    /**
     * 
     * @param serverName
     * @throws LaunchPadException
     * @throws AppInitException
     */
    private void createSiteConfig(String serverName) throws LaunchPadException,
            AppInitException {
        try {
            this.app.initSiteConfig(serverName);
            UserSecurityManager.initUserSecurity();
            UserSecurityManager mgr = new UserSecurityManager();
            mgr.setServer(serverName);
            return;
        } catch (UserSecurityException e) {
            msg = "Error occured initializing the security environment and/or the database connection";
            throw new LaunchPadException(msg, e);
        }
    }

    /**
     * 
     * @param processId
     */
    private void invokeSelecteProcess(int processId) {
        switch (processId) {
            case LaunchPad.PROCESS_LOGIN:
                this.openLoginWindow();
                // Reset menu
                this.resetMenu();
                break;

            case LaunchPad.PROCESS_USER:
                this.openUserAdmin();
                break;

            case LaunchPad.PROCESS_PRICE_CHG:
                this.openPriceChangeAdmin();
                break;

            case LaunchPad.PROCESS_PRICE_AUDIT:
                this.openPriceAuditAdmin();
                break;

            case LaunchPad.PROCESS_SI_TRANS:
                this.openSITransferAdmin();
                break;

            case LaunchPad.PROCESS_BI_TRANS:
                this.openBITransferAdmin();
                break;

            case LaunchPad.PROCESS_SI_RTV:
                this.openSIRtvAdmin();
                break;

            case LaunchPad.PROCESS_BI_RTV:
                this.openBIRtvAdmin();
                break;

            case LaunchPad.PROCESS_COUNTS:
                this.openCountsAdmin();
                break;

            case LaunchPad.PROCESS_IDT:
                this.openIdtAdmin();
                break;

            case LaunchPad.PROCESS_REPORTS:
                this.openReportConsole();
                break;

            case LaunchPad.PROCESS_LOGOUT:
                this.logoutUser();
                // Reset menu
                this.resetMenu();
                break;

            case LaunchPad.PROCESS_EXIT:
                this.exitApplication();
                break;
            default:
                // do nothing
        }
    }

    /**
     * Re-render menu so that the appropriate menu items are displayed based on
     * the state of the securtity token.
     */
    private void resetMenu() {
        // Re-render the main menu.
        MainMenuCellRenderer c = new MainMenuCellRenderer();
        this.menu.setCellRenderer(c);
        this.pack();
        return;
    }

    /**
     * Log out user.
     * <p>
     * This method will reset the security token and re-render the main menu so
     * to display on those menu items that do not require security.
     */
    private void logoutUser() {
        // Reset the security token
        UserSecurityManager s = new UserSecurityManager();
        s.resetSecurityToken();
    }

    /**
     * Releases allocated resources and closes the application.
     */
    private void exitApplication() {
        this.logoutUser();
        this.dispose();
    }

    /**
     * Displays the User Login Window.
     */
    protected void openLoginWindow() {
        try {
            UserLoginDialog dialog = new UserLoginDialog(this, new Dimension(
                    380, 180), new Point(FRAME_X_VALUE, 300), "User Login");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (InvalidDataException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "User Login Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * Launch the User Administration Window.
     */
    protected void openUserAdmin() {
        try {
            AssocSelectFrame frame = new AssocSelectFrame(new Dimension(700,
                    300), new Point(FRAME_X_VALUE, 300),
                    LaunchPad.UI_TITLE_USERADMIN);
            frame.setVisible(true);
        } catch (TooManyInstancesException e) {
            return;
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * Launch Price Change Administration Window.
     */
    protected void openPriceChangeAdmin() {
        try {
            PriceChangeFrame frame = new PriceChangeFrame(new Dimension(730,
                    400), new Point(FRAME_X_VALUE, 300),
                    LaunchPad.UI_TITLE_PRICECHANGE);
            frame.setVisible(true);
        } catch (TooManyInstancesException e) {
            return;
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * Launch Price Change Administration Window.
     */
    protected void openPriceAuditAdmin() {
        try {
            PriceAuditFrame frame = new PriceAuditFrame(
                    new Dimension(730, 400), new Point(FRAME_X_VALUE, 300),
                    LaunchPad.UI_TITLE_PRICEAUDIT);
            frame.setVisible(true);
        } catch (TooManyInstancesException e) {
            return;
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * Launch SI Transfers Administration Window.
     */
    protected void openSITransferAdmin() {
        try {
            SITransferFrame frame = new SITransferFrame(
                    new Dimension(1140, 400), new Point(FRAME_X_VALUE, 300),
                    LaunchPad.UI_TITLE_SITRANSFER);
            frame.setVisible(true);
        } catch (TooManyInstancesException e) {
            return;
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * Launch BI Transfers Administration Window.
     */
    protected void openBITransferAdmin() {
        try {
            BITransferFrame frame = new BITransferFrame(
                    new Dimension(990, 400), new Point(FRAME_X_VALUE, 300),
                    LaunchPad.UI_TITLE_BITRANSFER);
            frame.setVisible(true);
        } catch (TooManyInstancesException e) {
            return;
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * Launch SI Transfers Administration Window.
     */
    protected void openSIRtvAdmin() {
        try {
            SIRtvFrame frame = new SIRtvFrame(new Dimension(590, 400),
                    new Point(FRAME_X_VALUE, 300), LaunchPad.UI_TITLE_SIRTV);
            frame.setVisible(true);
        } catch (TooManyInstancesException e) {
            return;
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * Launch BI Transfers Administration Window.
     */
    protected void openBIRtvAdmin() {
        try {
            BIRtvFrame frame = new BIRtvFrame(new Dimension(590, 400),
                    new Point(FRAME_X_VALUE, 300), LaunchPad.UI_TITLE_BIRTV);
            frame.setVisible(true);
        } catch (TooManyInstancesException e) {
            return;
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * Launch Counts Administration Window.
     */
    protected void openCountsAdmin() {
        try {
            CountsFrame frame = new CountsFrame(new Dimension(650, 400),
                    new Point(FRAME_X_VALUE, 300), LaunchPad.UI_TITLE_COUNTS);
            frame.setVisible(true);
        } catch (TooManyInstancesException e) {
            return;
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * Launch IDT Administration Window.
     */
    protected void openIdtAdmin() {
        try {
            IdtFrame frame = new IdtFrame(new Dimension(650, 400), new Point(
                    FRAME_X_VALUE, 300), LaunchPad.UI_TITLE_IDT);
            frame.setVisible(true);
        } catch (TooManyInstancesException e) {
            return;
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * Launch Reporting Module.
     */
    protected void openReportConsole() {
        try {
            ReportRequestFrame frame = new ReportRequestFrame(new Dimension(
                    660, 470), new Point(FRAME_X_VALUE, 300),
                    LaunchPad.UI_TITLE_REPORTS);
            frame.setVisible(true);
        } catch (TooManyInstancesException e) {
            return;
        } catch (Exception e) {
            this.msg = e.getMessage()
                    + "\n\nContact the support team for assistance";
            JOptionPane.showMessageDialog(this, this.msg, "Launch Pad Error",
                    JOptionPane.ERROR_MESSAGE);
            logger.error(this.msg);
            e.printStackTrace();
        }
    }

    /**
     * An event handler for caputuring the current main menu item when the user
     * navigates from one meun item to the next using the UP/DOWN arrow keys.
     * <p>
     * By no means does this event handler navigate the user to another window
     * component. It only identifies the current node and persist the current
     * selection to an instance variable. This process is performed on
     * subsequent selections.
     * 
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int ndx = this.menu.getSelectedIndex();
        this.selMenuItem = (MainMenuItem) this.menu.getModel()
                .getElementAt(ndx);
        logger.debug("Menu Item selected: "
                + this.selMenuItem.getDisplayValue());
        logger.debug("Menu Item Process Id: " + this.selMenuItem.getProcessId());
    }

    /**
     * Mouse adapter inner class that invokes the appropriate event handler as a
     * result of the user selecting a main menu item via the mouse pointing
     * device.
     * <p>
     * In most cases the user is navigated to another window. This is a
     * MouseListener implementation for the main menu component, which is a
     * derivative of JList. Its primary function is to capture the clicked event
     * of a selected main menu item triggered by the mouse.
     * 
     * @author rterrell
     *
     */
    public class MainMenuMouseAdapter extends MouseAdapter {
        /*
         * (non-Javadoc)
         * 
         * @see
         * java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            // Invoke the proper handler for user's menu selection
            invokeSelecteProcess(selMenuItem.getProcessId());
        }
    } // end MainMenuMouseAdapter

}

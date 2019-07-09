package com.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modules.LaunchPadException;

import org.apache.log4j.Logger;

import com.InvalidDataException;
import com.TooManyInstancesException;
import com.nv.util.AppUtil;

/**
 * A frame to be used throughout the Launch Pad application which pocesses
 * common funcationality for all its descendents.
 * <p>
 * The object is best used as the main application window or as a stand alone
 * modeless frame.
 * 
 * @author rterrell
 *
 */
public abstract class AbstractModelessWindow extends JFrame implements
        ActionListener, WindowListener, WindowProcessTemplateListener {

    private static final long serialVersionUID = 3113750021825665973L;

    private static final Logger logger = Logger
            .getLogger(AbstractModelessWindow.class);

    private static Map<String, String> SINGLE_FRAME_INSTANCES;

    /**
     * The main content panel component
     */
    protected JPanel contentPanel;

    /**
     * A panel component for managing command buttons.
     */
    protected JPanel buttonPane;

    /**
     * The OK Command button
     */
    protected JButton okButton;

    /**
     * The Cancel command button
     */
    protected JButton cancelButton;

    /**
     * The window title.
     */
    protected String winTitle;

    /**
     * A general variable for holding message text.
     */
    protected String msg;

    /**
     * A variable for holding the windows's postion coordinates
     */
    protected Point position;

    /**
     * A variable for holding the window's width and height
     */
    protected Dimension size;

    /**
     * A {@link JLabel} componenet serving as the window's message area.
     */
    protected JLabel msgLbl;

    /**
     * Controls whether a window can exists as multiple instances.
     * <p>
     * By default, this indicator is set to true. The descendent desires to
     * allow muliple instances, then this indicaator should be set prior to
     * invocation of the ancestor {@link AbstractModelessWindow#initFrame()
     * initFrame()}.
     */
    protected boolean singleton = true;

    /**
     * Creates a NmAbstractModelessUI without a sub title.
     * 
     * @throws HeadlessException
     */
    public AbstractModelessWindow() throws HeadlessException {
        super();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.initFrame();
    }

    /**
     * Creates a NmAbstractModelessUI with a sub title.
     * 
     * @param size
     *            the window's dimensions in terms of size and position
     * @param pos
     *            the location where the window is to be positioned.
     * @param winTitle
     *            a String that will be appended to the title of the window.
     * @throws HeadlessException
     */
    public AbstractModelessWindow(Dimension size, Point pos, String winTitle)
            throws HeadlessException {
        super();
        this.size = size;
        this.position = pos;
        this.winTitle = winTitle;
        this.initFrame();
        this.setIconImage(AppUtil.getAppIcon().getImage());
    }

    /**
     * Perform common frame initializations.
     * <p>
     * Currently, the frame's title is set here. At the descendent level,
     * override this method to perform more specific constructor related
     * initialization tasks.
     */
    protected void initFrame() {
        this.setResizable(true);

        // Initialize message label
        this.msgLbl = new JLabel();

        // Abort initialization in the event an instance of this window already
        // exits
        if (this.singleton && this.instanceExists()) {
            this.msg = "Launch Pad does not allow multiple version of this window to be opened concurrently";
            logger.fatal(this.msg);
            JOptionPane.showMessageDialog(this, this.msg, this.getTitle()
                    + " Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            throw new TooManyInstancesException(this.msg);
        }

        // Manage window size and postion
        int posX = 100;
        int posY = 100;
        int locW = 450;
        int locH = 300;
        if (this.position != null) {
            posX = this.position.x;
            posY = this.position.y;
        }
        if (this.size != null) {
            locW = this.size.width;
            locH = this.size.height;
        }
        this.setBounds(posX, posY, locW, locH);

        this.contentPanel = this.createContentLayout();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Build button panel
        buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

        okButton = new JButton("OK");
        okButton.setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS);
        okButton.addActionListener(this);
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(this.okButton);

        cancelButton = new JButton("Cancel");
        cancelButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_CANCEL);
        cancelButton.addActionListener(this);
        buttonPane.add(cancelButton);

        // Add button panel to the main content pane
        this.getContentPane().add(this.buttonPane, BorderLayout.SOUTH);

        // Set window title.
        String appTitle = AppUtil.getAppProperty(AppUtil.PROP_APP_TITLE);
        if (this.winTitle != null) {
            appTitle = this.winTitle;
        }
        this.setTitle(appTitle);

        // Add window to open instances list
        this.addInstance();

        // Show total records retrieved count
        this.displayRecordCount();

        this.addWindowListener(this);

        // Center Window
        this.setLocationRelativeTo(null);

        logger.info("Base Frame initialized");
    }

    /**
     * Handles the click actions of the OK and Cancel JButtons
     * 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.msgLbl != null) {
            this.msgLbl.setVisible(false);
        }
        String command = e.getActionCommand();
        if (command
                .equals(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS)) {
            this.processAction(e);
            return;
        }
        if (command.equals(WindowProcessTemplateListener.ACTION_COMMAND_CANCEL)) {
            this.closeWithoutProcessingAction(e);
            return;
        }
        return;
    }

    /**
     * Handler for the OK Button.
     * 
     * @param e
     */
    protected Object processAction(ActionEvent e) throws RuntimeException {
        try {
            Object data = this.getInputData();
            Object results = this.processData(data);
            return results;
        } catch (InvalidDataException e1) {
            this.msg = "Invalid data error occured while gathering input data from window";
            logger.error(this.msg, e1);
            throw new RuntimeException(this.msg, e1);
        } catch (LaunchPadException e1) {
            this.msg = "Error occured processing window data";
            logger.error(this.msg, e1);
            throw new RuntimeException(this.msg, e1);
        }
    }

    /**
     * Handler for the Cancel button.
     * 
     * @param e
     */
    protected void closeWithoutProcessingAction(ActionEvent e) {
        // Remove window from the open instance list
        this.removeInstance();
        this.dispose();
        return;
    }

    /**
     * Adds this window to the open instance list.
     */
    private void addInstance() {
        if (AbstractModelessWindow.SINGLE_FRAME_INSTANCES == null) {
            AbstractModelessWindow.SINGLE_FRAME_INSTANCES = new HashMap<String, String>();
        }
        String winName = this.getClass().getSimpleName();
        AbstractModelessWindow.SINGLE_FRAME_INSTANCES.put(winName, winName);
    }

    /**
     * Removes this window from the open instance list
     * 
     * @return true if this window was found and removed from the open instance
     *         list and false when the window does not exist in the open
     *         instance list.
     */
    private boolean removeInstance() {
        if (AbstractModelessWindow.SINGLE_FRAME_INSTANCES != null) {
            String winName = AbstractModelessWindow.SINGLE_FRAME_INSTANCES
                    .remove(this.getClass().getSimpleName());
            return (winName != null);
        }
        return false;
    }

    /**
     * Verifies if this window instance is already open and visible to the user.
     * 
     * @return true if the window is open and visible to the user or false if
     *         the window has yet to be launched.
     */
    protected boolean instanceExists() {
        if (AbstractModelessWindow.SINGLE_FRAME_INSTANCES != null) {
            String winName = AbstractModelessWindow.SINGLE_FRAME_INSTANCES
                    .get(this.getClass().getSimpleName());
            return (winName != null);
        }
        return false;
    }

    /**
     * A stub method for displaying a message concerning the total number of
     * records retrieved.
     * <p>
     * When implementing this method, be aware that its invocation is at the end
     * of the initFrame method.
     */
    protected void displayRecordCount() {
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("WindowListener method called: windowOpened.");
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("WindowListener method called: windowClosing.");
        this.closeWithoutProcessingAction(null);
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("WindowListener method called: windowClosed.");
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("WindowListener method called: windowIconified.");
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent
     * )
     */
    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("WindowListener method called: windowDeiconified.");
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println("WindowListener method called: windowActivated.");
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent
     * )
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("WindowListener method called: windowDeactivated.");
        return;
    }

    /**
     * @return the singleton
     */
    public boolean isSingleton() {
        return singleton;
    }

    /**
     * @param singleton
     *            the singleton to set
     */
    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }
}

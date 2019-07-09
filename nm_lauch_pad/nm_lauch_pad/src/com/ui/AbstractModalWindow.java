package com.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modules.LaunchPadException;

import org.apache.log4j.Logger;

import com.InvalidDataException;
import com.nv.util.AppUtil;

/**
 * A dialog to be used throughout the Launch Pad application which pocesses
 * common funcationality for all its descendents.
 * <p>
 * The most outstanding feature of this abstraction is its modal
 * characteristics. This object is best used as a window for displaying form
 * that captures the user's input blocking the application from continuing until
 * the object (window) is closed., hence modal type.
 * 
 * @author rterrell
 *
 */
public abstract class AbstractModalWindow extends JDialog implements
        ActionListener, WindowListener, WindowProcessTemplateListener {

    private static final long serialVersionUID = -2001475361405763220L;

    private static final Logger logger = Logger
            .getLogger(AbstractModalWindow.class);

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
     * A variable for holding the data captured from the user's input.
     */
    protected Object inData;

    /**
     * A {@link JLabel} componenet serving as the window's message area.
     */
    protected JLabel msgLbl;

    /**
     * Creates a NmAbstractModalUI without a owner, without any size and
     * positioning, and without a sub title.
     */
    public AbstractModalWindow() {
        super();
        this.initDialog();
    }

    /**
     * Creates a NmAbstractModalUI with an owner Dialog and without a sub title.
     * 
     * @param owner
     *            a instance of Dialog.
     * @param data
     *            an arbitrary object representing the data that is to displayed
     *            and maniupulated by the dialog.
     * @param size
     *            the window's dimensions in terms of size and position
     * @param pos
     *            the location where the window is to be positioned.
     */
    public AbstractModalWindow(Dialog owner, Object data, Dimension size,
            Point pos) {
        super(owner);
        this.size = size;
        this.position = pos;
        this.inData = data;
        this.initDialog();
    }

    /**
     * Creates a NmAbstractModalUI with a owner Dialog and with a sub title.
     * 
     * @param owner
     *            a instance of Dialog.
     * @param data
     *            an arbitrary object representing the data that is to displayed
     *            and maniupulated by the dialog.
     * @param size
     *            the window's dimensions in terms of size and position
     * @param pos
     *            the location where the window is to be positioned.
     * @param winTitle
     *            a String to append to the title of the window.
     */
    public AbstractModalWindow(Dialog owner, Object data, Dimension size,
            Point pos, String winTitle) {
        super(owner);
        this.winTitle = winTitle;
        this.size = size;
        this.position = pos;
        this.inData = data;
        this.initDialog();
    }

    /**
     * Creates a NmAbstractModalUI with an owner Frame and without a sub title.
     * 
     * @param owner
     *            an instance of Frame
     * @param data
     *            an arbitrary object representing the data that is to displayed
     *            and maniupulated by the dialog.
     * @param size
     *            the window's dimensions in terms of size and position
     * @param pos
     *            the location where the window is to be positioned.
     */
    public AbstractModalWindow(Frame owner, Object data, Dimension size,
            Point pos) {
        super(owner);
        this.size = size;
        this.position = pos;
        this.inData = data;
        this.initDialog();
    }

    /**
     * Creates a NmAbstractModalUI with a owner Frame and with a sub title.
     * 
     * @param owner
     *            an instance of Frame
     * @param data
     *            an arbitrary object representing the data that is to displayed
     *            and maniupulated by the dialog.
     * @param size
     *            the window's dimensions in terms of size and position
     * @param pos
     *            the location where the window is to be positioned.
     * @param winTitle
     *            a String to append to the title of the window.
     */
    public AbstractModalWindow(Frame owner, Object data, Dimension size,
            Point pos, String winTitle) {
        super(owner);
        this.winTitle = winTitle;
        this.size = size;
        this.position = pos;
        this.inData = data;
        this.initDialog();
    }

    /**
     * Creates a NmAbstractModalUI with an owner Window and without a sub title.
     * 
     * @param owner
     *            an instance of Window
     * @param data
     *            an arbitrary object representing the data that is to displayed
     *            and maniupulated by the dialog.
     * @param size
     *            the window's dimensions in terms of size and position
     * @param pos
     *            the location where the window is to be positioned.
     */
    public AbstractModalWindow(Window owner, Object data, Dimension size,
            Point pos) {
        super(owner);
        this.size = size;
        this.position = pos;
        this.inData = data;
        this.initDialog();
    }

    /**
     * Creates a NmAbstractModalUI with a owner Window and with a sub title.
     * 
     * @param owner
     *            an instance of Window
     * @param data
     *            an arbitrary object representing the data that is to displayed
     *            and maniupulated by the dialog.
     * @param size
     *            the window's dimensions in terms of size and position
     * @param pos
     *            the location where the window is to be positioned.
     * @param winTitle
     *            a String to append to the title of the window.
     */
    public AbstractModalWindow(Window owner, Object data, Dimension size,
            Point pos, String winTitle) {
        super(owner);
        this.winTitle = winTitle;
        this.size = size;
        this.position = pos;
        this.inData = data;
        this.initDialog();
    }

    /**
     * Performs common constructor related initialization tasks.
     */
    protected void initDialog() {
        this.setResizable(true);

        // Initialize message label
        this.msgLbl = new JLabel();

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

        this.getContentPane().setLayout(new BorderLayout());

        this.contentPanel = this.createContentLayout();

        // The content panel could potentially be null if createContentDisplay
        // is not properly implemented
        if (this.contentPanel == null) {
            this.msg = "The createContentDisplay method of "
                    + this.getClass().getName()
                    + " must be implemented in such a way as to initialize the main content panel of the framework with a valid instance of JPanel";
            logger.error(this.msg);
            throw new RuntimeException(this.msg);
        }
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Build Button panel
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

        this.setModal(true);

        this.addWindowListener(this);

        // Show total records retrieved count
        this.displayRecordCount();

        // Center Window
        this.setLocationRelativeTo(null);

        logger.info("Base Modal Dialog initialized");
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
                .equals(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS)
                || command
                        .equals(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS_CLOSE)) {
            this.processAction(e);
            // Window processing was successful.
            if (command
                    .equals(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS_CLOSE)) {
                this.dispose();
            }
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
    protected Object processAction(ActionEvent e) {
        try {
            Object data = this.getInputData();
            Object results = this.processData(data);
            return results;
        } catch (InvalidDataException e1) {
            this.msg = "Invalid data error occured while gathering input data from window";
            logger.error(this.msg, e1);
            e1.printStackTrace();
            throw new RuntimeException(this.msg, e1);
        } catch (LaunchPadException e1) {
            this.msg = "General error occured while processng window data";
            logger.error(this.msg, e1);
            e1.printStackTrace();
            throw new RuntimeException(this.msg, e1);
        }
    }

    /**
     * Handler for the Cancel button.
     * 
     * @param e
     */
    protected void closeWithoutProcessingAction(ActionEvent e) {
        this.dispose();
        return;
    }

    /**
     * A stub method for displaying a message concerning the total number of
     * records retrieved.
     * <p>
     * When implementing this method, be aware that its invocation is at the end
     * of the initDialog method.
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
        return;
    }

}

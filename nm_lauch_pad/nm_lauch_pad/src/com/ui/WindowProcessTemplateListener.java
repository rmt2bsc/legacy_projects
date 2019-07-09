package com.ui;

import javax.swing.JPanel;

import modules.LaunchPadException;

import com.InvalidDataException;

/**
 * Interface for processing the data belonging to a Launch Pad UI.
 * <p>
 * This interface is most beneficial for UI's that requires the gathering and
 * processing of data before disposing of the UI. For example, a dialog that
 * accepts input from the user, and subsequently the input data is saved to a
 * database.
 * 
 * @author rterrell
 *
 */
public interface WindowProcessTemplateListener {

    /**
     * The action command to save the contents of window.
     */
    static final String ACTION_COMMAND_PROCESS = "PROCESS_WINDOW";

    /**
     * The action command tosave th contents of the winodow and close
     * immediately after success.
     */
    static final String ACTION_COMMAND_PROCESS_CLOSE = "PROCESS_WINDOW_AND_CLOsE";

    /**
     * The action command to close the window without saving.
     */
    static final String ACTION_COMMAND_CANCEL = "CANCEL_WINDOW";

    /**
     * Implementation of this method should provide logic to create intial
     * presentation layout container and return that container as an instance of
     * JPanel.
     */
    JPanel createContentLayout();

    /**
     * Implement this method for the purpose of gathering input information from
     * a UI.
     * 
     * @return an arbitrary object as the input data
     * @throws InvalidDataException
     */
    Object getInputData() throws InvalidDataException;

    /**
     * Implement this method to process the data a UI contains.
     * 
     * @param data
     *            an arbitrary object acting as the input data to be processed.
     * @return an arbitary object representing the results of processing input
     *         data or null if there is nothing to be returned.
     * @throws LaunchPadException
     */
    Object processData(Object data) throws LaunchPadException;
}

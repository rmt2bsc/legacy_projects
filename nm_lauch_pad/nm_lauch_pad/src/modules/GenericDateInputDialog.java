package modules;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.InvalidDataException;

import com.nv.util.AppUtil;
import com.nv.util.GeneralUtil;
import com.ui.AbstractModalWindow;
import com.ui.WindowProcessTemplateListener;

/**
 * Common modal dialog to capture the user's input regarding a date value.
 * <p>
 * The value entered is required to be a valid date. This dialog is equipped
 * with an input text field and two command buttons (OK and Cancel). The OK
 * button accepts, validates, and returns the quantity value to the caller.
 * 
 * @author rterrell
 *
 */
public class GenericDateInputDialog extends AbstractModalWindow {

    private static final long serialVersionUID = -2684554944089338953L;

    /**
     * A holder varibale for storing user's quantity input.
     */
    protected Date date;

    /**
     * The label component describing the text input field
     */
    protected JLabel dateLbl;

    /**
     * A text input field for capturing the user's input of the date.
     */
    protected JTextField dateTxt;

    /**
     * Creates a GenericQtyInputDialog initialized with its parent, size,
     * position, window title, and the text for the label component.
     * 
     * @param owner
     *            the parent object of this window.
     * @param size
     *            the width and height of this window
     * @param pos
     *            the X/Y position coordinates of this window
     * @param winTitle
     *            thet window title.
     * @param inputLabel
     *            the text to populate the label decribing the text input field.
     */
    public GenericDateInputDialog(Window owner, Dimension size, Point pos,
            String winTitle, String inputLabel) {
        super(owner, null, size, pos, winTitle);
        this.dateLbl.setText(inputLabel);
    }

    /**
     * Performs specific initializations for this dialog.
     */
    @Override
    protected void initDialog() {
        super.initDialog();
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS_CLOSE);
        this.date = null;
        this.setResizable(false);
    }

    /**
     * Uses the MigLayout to add a text input field, message area, and two
     * command buttons.
     */
    @Override
    public JPanel createContentLayout() {
        // Use the MigLayout to setup window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[][]", "[]20[]"));

        this.dateLbl = new JLabel();
        this.dateTxt = new JTextField();

        mainPanel.add(this.dateLbl);
        mainPanel.add(this.dateTxt, "width 180:180:180, wrap");

        this.msgLbl = new JLabel();
        this.msgLbl.setForeground(Color.RED);
        mainPanel.add(msgLbl, "span 2, wrap");

        return mainPanel;
    }

    /**
     * Accepts the input date and validates it when the user clicks the "OK"
     * button.
     * <p>
     * In terms of validations, an input value is required and must be a valid
     * date These validations are only inforces when the user clicks the "OK"
     * button.
     * 
     * @return the date entered by the user as a {@link Date}
     * @throws InvalidDataException
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        this.date = null;
        String inDate = this.dateTxt.getText();
        if (inDate == null || inDate.equals("")) {
            this.msg = "The Date value is required";
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new InvalidDataException(this.msg);
        }

        try {
            Date dt = GeneralUtil.validateLaunchPadDateEdits(
                    this.msgLbl.getText(), inDate);
            return dt;
        } catch (Exception e) {
            this.msg = AppMessages.MSG_INVALID_DATE;
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new InvalidDataException(AppMessages.MSG_INVALID_DATE, e);
        }

        // try {
        // Date dateVal = this.date = GeneralUtil.stringToDate(inDate);
        // return dateVal;
        // }
        // catch (Exception e) {
        // this.msg = "The value entered must be a valid date";
        // AppUtil.showMessage(this.msgLbl, this.msg, true);
        // throw new InvalidDataException(this.msg);
        // }
    }

    /**
     * Processes the genreic data, <i>data</i>, and assigns it to the internal
     * class variable designated to hold the date value.
     * 
     * @param data
     *            an arbitrary object disguised as the input quantity value.
     * @return the actual input value representing quantity.
     * @throws LaunchPadException
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        // At this point it is understood that "data" is valid date
        this.date = (Date) data;
        return this.date;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

}

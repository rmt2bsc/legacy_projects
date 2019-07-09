package modules;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import net.miginfocom.swing.MigLayout;

import com.InvalidDataException;

import com.nv.util.AppUtil;
import com.nv.util.GeneralUtil;

import com.ui.AbstractModalWindow;
import com.ui.WindowProcessTemplateListener;

/**
 * Common modal dialog to capture the user's input regarding a simple quantity
 * value.
 * <p>
 * The value entered is required to be numeric. This dialog is equipped with an
 * input text field and two command buttons (OK and Cancel). The OK button
 * accepts, validates, and returns the quantity value to the caller.
 * 
 * @author rterrell
 *
 */
public class GenericQtyInputDialog extends AbstractModalWindow {

    private static final long serialVersionUID = -2684554944089338953L;

    private static final Logger logger = Logger
            .getLogger(GenericQtyInputDialog.class);

    /**
     * A holder varibale for storing user's quantity input.
     */
    protected Integer qty;

    /**
     * The label component describing the text input field
     */
    protected JLabel qtyLbl;

    /**
     * A text input field for capturing the user's input of quantity.
     */
    protected JTextField qtyTxt;

    private String mask;

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
    public GenericQtyInputDialog(Window owner, Dimension size, Point pos,
            String winTitle, String inputLabel) {
        super(owner, null, size, pos, winTitle);
        this.qtyLbl.setText(inputLabel);
    }

    /**
     * Creates a GenericQtyInputDialog initialized with its parent, size,
     * position, window title, the text for the label component, and the default
     * quantity to displayed.
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
     * @param defaultQty
     *            the quantity value to be displayed.
     */
    public GenericQtyInputDialog(Window owner, Dimension size, Point pos,
            String winTitle, String inputLabel, Integer defaultQty) {
        this(owner, size, pos, winTitle, inputLabel);
        this.qtyTxt.setText(defaultQty == null || defaultQty.equals("") ? "0"
                : defaultQty.toString());
    }

    /**
     * Performs specific initializations for this dialog.
     */
    @Override
    protected void initDialog() {
        super.initDialog();
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS_CLOSE);
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

        this.qtyLbl = new JLabel();
        this.qtyTxt = GeneralUtil.createMaskedTextField(this.mask);
        logger.info("The following format mask was applied to Input Quantity Text field:"
                + this.mask);

        mainPanel.add(this.qtyLbl);
        mainPanel.add(this.qtyTxt, "width 180:180:180, wrap");

        this.msgLbl = new JLabel();
        this.msgLbl.setForeground(Color.RED);
        mainPanel.add(msgLbl, "span 2, wrap");

        return mainPanel;
    }

    /**
     * Accepts the input quantity and validates it when the user clicks the "OK"
     * button.
     * <p>
     * In terms of validations, the sku quantity must be a numeric greater than
     * or equal to zero. These validations are only inforces when the user
     * clicks the "OK" button.
     * 
     * @return the quatnity entered by the user as a String
     * @throws InvalidDataException
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        String inQty = this.qtyTxt.getText().trim();
        if (inQty == null || inQty.equals("")) {
            this.msg = "The quantity value is required";
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new InvalidDataException(this.msg);
        }

        if (!GeneralUtil.isNumeric(inQty)) {
            this.msg = "Is required to be numeric";
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new InvalidDataException(this.msg);
        }

        boolean validVal = (Integer.parseInt(inQty) >= 0);
        if (!validVal) {
            this.msg = "The quantity entered must be a value greater than or equal to 0";
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new InvalidDataException(this.msg);
        }
        return inQty;
    }

    /**
     * Processes the genreic data, <i>data</i>, and assigns it to the internal
     * class variable designated to hold the quantity value.
     * 
     * @param data
     *            an arbitrary object disguised as the input quantity value.
     * @return the actual input value representing quantity.
     * @throws LaunchPadException
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        if (data != null) {
            this.qty = new Integer(data.toString());
        }
        return this.qty;
    }

    /**
     * Return the input quantity.
     * 
     * @return the qty
     */
    public Integer getQty() {
        return this.qty;
    }

    /**
     * @param mask
     *            the mask to set
     */
    public void setMask(String mask) {
        this.mask = mask;
    }
}

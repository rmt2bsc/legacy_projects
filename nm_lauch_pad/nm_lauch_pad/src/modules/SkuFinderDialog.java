package modules;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

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
 * Modal dialog functioning to capturing the user's input of a SKU number. The
 * SKU entered must be numeric and must contain a maximum of 13 digits. This
 * dialog is equipped with an input text field and two command buttons (OK and
 * Cancel). The OK button accepts, validates, and returns the SKU to the caller.
 * 
 * @author rterrell
 *
 */
public class SkuFinderDialog extends AbstractModalWindow {

    private static final long serialVersionUID = -2684554944089338953L;

    private JTextField skuTxt;

    private JLabel msgLbl;

    private String sku;

    /**
     * Creates a SkuFinderDialog initialized with its parent, size, postion, and
     * window title.
     * 
     * @param owner
     *            the parent object of this window.
     * @param size
     *            the width and height of this window
     * @param pos
     *            the X/Y position coordinates of this window
     * @param winTitle
     *            thet window title.
     */
    public SkuFinderDialog(Frame owner, Dimension size, Point pos,
            String winTitle) {
        super(owner, null, size, pos, winTitle);
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

        JLabel skuLbl = new JLabel("SKU (Blank for None):");
        this.skuTxt = new JTextField();

        mainPanel.add(skuLbl);
        mainPanel.add(this.skuTxt, "width 180:180:180, wrap");

        this.msgLbl = new JLabel();
        this.msgLbl.setForeground(Color.RED);
        mainPanel.add(msgLbl, "span 2, wrap");

        return mainPanel;
    }

    /**
     * Accepts the input SKU and validates it.
     * <p>
     * It is acceptable for the input sku to be "", null, or numeric value. To
     * pass validations, the sku must be numeric and must not exceed 13 digits.
     * 
     * @return the SKU entered or "" when the user did not specify a SKU.
     * @throws InvalidDataException
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        String inSku = this.skuTxt.getText();
        if (inSku.equals("")) {
            return inSku;
        }

        if (!GeneralUtil.isNumeric(inSku)) {
            this.msg = "SKU, " + inSku + ", is not a valid number";
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new InvalidDataException(this.msg);
        }

        boolean validLen = (inSku.length() >= 1 && inSku.length() <= 13);
        if (!validLen) {
            this.msg = "SKU, " + inSku
                    + ", must contain  1 to 13 numeric digits";
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new InvalidDataException(this.msg);
        }
        return inSku;
    }

    /**
     * Processes the genreic data, <i>data</i>, and assigns it to the internal
     * SKU class variable as a holder.
     * 
     * @param data
     *            an arbitrary object disguised as the input SKU number.
     * @return the actual input SKU as a String
     * @throws LaunchPadException
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        if (data != null) {
            this.sku = data.toString();
        }
        return data;
    }

    /**
     * Return the input SKU
     * 
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

}

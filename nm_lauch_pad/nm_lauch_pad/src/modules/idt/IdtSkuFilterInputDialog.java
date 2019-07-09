package modules.idt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modules.LaunchPadException;

import modules.model.SkuItem;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import com.InvalidDataException;

import com.nv.util.AppUtil;
import com.nv.util.GeneralUtil;

import com.ui.AbstractModalWindow;
import com.ui.WindowProcessTemplateListener;

/**
 * Dialog for inputing SKU filter data.
 * 
 * @author rterrell
 *
 */
public class IdtSkuFilterInputDialog extends AbstractModalWindow {

    private static final long serialVersionUID = -4321899934172262013L;

    private static final Logger logger = Logger
            .getLogger(IdtSkuFilterInputDialog.class);

    private SkuItem criteria;

    private JLabel deptLbl;

    private JLabel classLbl;

    private JLabel vendorLbl;

    private JLabel styleLbl;

    private JLabel colorLbl;

    private JLabel sizeLbl;

    private JTextField deptTxt;

    private JTextField classTxt;

    private JTextField vendorTxt;

    private JTextField styleTxt;

    private JTextField colorTxt;

    private JTextField sizeTxt;

    /**
     * Create a IdtSkuFilterInputDialog with known parent widnow, the assoicated
     * object to edit, the dialog size dimensions, the dialog position
     * coordinates, and the dialog title.
     * 
     * @param owner
     *            the parent window reference
     * @param size
     *            The window size coordinates
     * @param pos
     *            The window position coordinates
     * @param winTitle
     *            The window title.
     */
    public IdtSkuFilterInputDialog(Frame owner, Dimension size, Point pos,
            String winTitle) {
        super(owner, null, size, pos, winTitle);
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
        this.setResizable(false);
        this.criteria = null;
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
        // Use the MigLayout to setup window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[][]", "[][][][][][][]20[]"));

        deptLbl = new JLabel("Dept:");
        this.deptTxt = GeneralUtil.createMaskedTextField("****");
        mainPanel.add(deptLbl);
        mainPanel.add(this.deptTxt, "width 180:180:180, wrap");

        classLbl = new JLabel("Class:");
        this.classTxt = GeneralUtil.createMaskedTextField("***");
        mainPanel.add(classLbl);
        mainPanel.add(this.classTxt, "width 180:180:180, wrap");

        vendorLbl = new JLabel("Vendor:");
        this.vendorTxt = GeneralUtil.createMaskedTextField("******");
        mainPanel.add(vendorLbl);
        mainPanel.add(this.vendorTxt, "width 180:180:180, wrap");

        styleLbl = new JLabel("Style:");
        this.styleTxt = GeneralUtil.createMaskedTextField("******");
        mainPanel.add(styleLbl);
        mainPanel.add(this.styleTxt, "width 180:180:180, wrap");

        colorLbl = new JLabel("Color:");
        this.colorTxt = GeneralUtil.createMaskedTextField("****");
        mainPanel.add(colorLbl);
        mainPanel.add(this.colorTxt, "width 180:180:180, wrap");

        sizeLbl = new JLabel("Size:");
        this.sizeTxt = GeneralUtil.createMaskedTextField("******");
        mainPanel.add(sizeLbl);
        mainPanel.add(this.sizeTxt, "width 180:180:180, wrap");

        this.msgLbl = new JLabel();
        this.msgLbl.setForeground(Color.RED);
        mainPanel.add(msgLbl, "span 2, wrap");

        return mainPanel;
    }

    /**
     * Accepts the input field values, validates the input data, packages and
     * retuns the input as a SkuItem object.
     * 
     * @return an instance of {@link SkuItem} containing the validated input
     *         data.
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
            SkuItem vo = this.validateInputData();
            return vo;
        } catch (InvalidDataException e) {
            AppUtil.showMessage(this.msgLbl, e.getMessage(), true);
            throw e;
        }
    }

    /**
     * Validates the input data: dept, class, vendor, style, color, and size.
     * <p>
     * All fields are optional, but entered, must be numeric and in the range of
     * 0 to 99999. If a field is not populated with a value, then the
     * corresponding {@link SkuItem} property will be set to null.
     * 
     * @return an instance of {@link SkuItem} containing the validated input
     *         values.
     * @throws InvalidDataException
     *             a validation error was found.
     * @throws DatabaseException
     *             database access error
     */
    protected SkuItem validateInputData() throws InvalidDataException {
        SkuItem item = new SkuItem();
        Integer val = null;
        String valStr;

        valStr = this.deptTxt.getText();
        if (valStr == null
                || valStr.equals(GeneralUtil.spaces(valStr.length()))) {
            this.msg = "Department is required";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        val = this.validateInputValue(this.deptTxt, this.deptLbl);
        item.setDept(val);

        val = this.validateInputValue(this.classTxt, this.classLbl);
        item.setClazz(val);

        val = this.validateInputValue(this.vendorTxt, this.vendorLbl);
        item.setVendor(val);

        val = this.validateInputValue(this.styleTxt, this.styleLbl);
        item.setStyle(val);

        val = this.validateInputValue(this.colorTxt, this.colorLbl);
        item.setColor(val);

        val = this.validateInputValue(this.sizeTxt, this.sizeLbl);
        item.setSize(val);

        return item;
    }

    private Integer validateInputValue(JTextField comp, JLabel lbl)
            throws InvalidDataException {
        String val = comp.getText().trim();
        String lab = lbl.getText();
        lab = lab.replaceAll(":", "");
        Integer intVal = null;
        if (val != null && !val.equals("")
                && !val.equals(GeneralUtil.spaces(val.length()))) {
            try {
                intVal = Integer.parseInt(val);
            } catch (NumberFormatException e) {
                this.msg = lab + " must be a numeric value";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }
        }
        return intVal;
    }

    /**
     * This method captures and returns the SKU selection criteria object that
     * was constructed from the input data.
     * 
     * @param data
     *            an instance of {@link SkuItem}
     * @return an instance of {@link SkuItem}
     * @throws LaunchPadException
     *             N/A
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        this.criteria = (SkuItem) data;

        return this.criteria;
    }

    /**
     * Return the packaged selection criteria as an instance of SkuItem.
     * 
     * @return {@link SkuItem}
     */
    public SkuItem getCriteria() {
        return criteria;
    }

}

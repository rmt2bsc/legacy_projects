package modules.transfer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modules.AppMessages;
import modules.GeneralDao;
import modules.GeneralDaoImpl;
import modules.LaunchPadException;
import modules.model.Store;
import modules.model.Transfer;
import modules.transfer.bi.BITransferFactory;
import modules.transfer.si.SITransferFactory;
import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import com.InvalidDataException;

import com.nv.security.SecurityToken;
import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;
import com.nv.util.GeneralUtil;
import com.ui.AbstractModalWindow;
import com.ui.WindowProcessTemplateListener;
import com.ui.text.MaximumCharacterStyledDocument;

/**
 * A common dialog for allowing the user to perform Transfer Header detail
 * updates.
 * 
 * @author rterrell
 *
 */
public class CommonTransferHeaderEditDialog extends AbstractModalWindow {

    private static final long serialVersionUID = 2240204949920003193L;

    private static final Logger logger = Logger
            .getLogger(CommonTransferHeaderEditDialog.class);

    private Transfer model;

    private Transfer modifiedModel;

    private JTextField manifestTxt;

    private JTextField proBillTxt;

    private JTextField carrierTxt;

    private JTextField shipDateTxt;

    private JTextField pkgTypeTxt;

    protected JTextField toStoreTxt;

    private JTextField rcTxt;

    /**
     * Create a CommonTransferHeaderEditDialog object initailized with a parent
     * object, the price change model item, the window size, the window postion
     * and window title.
     * 
     * @param owner
     *            the parent window reference
     * @param item
     *            The price change model containing the data to be updated.
     * @param size
     *            The window size coordinates
     * @param pos
     *            The window position coordinates
     * @param winTitle
     *            The window title.
     */
    public CommonTransferHeaderEditDialog(Frame owner, Transfer item,
            Dimension size, Point pos, String winTitle) {
        super(owner, item, size, pos, winTitle);
        logger.info("CommonTransferHeaderEditDialog intialized");
        return;
    }

    /**
     * Initializes the dialog.
     * <p>
     * Captures the input model as the appropriate runtime type and modifies the
     * window title to include the transfer number.
     */
    @Override
    protected void initDialog() {
        this.model = (Transfer) this.inData;
        super.initDialog();

        // Force dialog to close automatically after a successful update
        this.okButton
                .setActionCommand(WindowProcessTemplateListener.ACTION_COMMAND_PROCESS_CLOSE);

        // Modify window title to include the transfer number.
        String appTitle = this.getTitle();
        appTitle += this.model.getTranferId();
        this.setTitle(appTitle);

        // Do not resize
        this.setResizable(false);
    }

    /**
     * Uses the MigLayout to layout the controls for this window.
     * 
     */
    @Override
    public JPanel createContentLayout() {
        // Use the MigLayout to setup window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[][]", "[][][][][][][]20[]"));

        JLabel lbl1 = new JLabel("Manifest #:");
        this.manifestTxt = GeneralUtil.createMaskedTextField("*********");
        if (this.model.getManifestNo() == null
                || this.model.getManifestNo() == 0) {
            this.manifestTxt.setText("");
        }
        else {
            this.manifestTxt.setText(this.model.getManifestNo().toString());
        }
        mainPanel.add(lbl1);
        mainPanel.add(this.manifestTxt, "width 180:180:180, wrap");

        JLabel lbl2 = new JLabel("Pro Bill:");
        this.proBillTxt = GeneralUtil.createMaskedTextField("*********");
        if (this.model.getProBill() == null || this.model.getProBill() == 0) {
            this.proBillTxt.setText("");
        }
        else {
            this.proBillTxt.setText(this.model.getProBill().toString());
        }
        mainPanel.add(lbl2);
        mainPanel.add(this.proBillTxt, "width 180:180:180, wrap");

        JLabel lbl3 = new JLabel("Carrier ID:");
        this.carrierTxt = GeneralUtil.createMaskedTextField("*****");
        this.carrierTxt.setText(this.model.getCarrierId() == null ? null
                : this.model.getCarrierId().trim());
        mainPanel.add(lbl3);
        mainPanel.add(this.carrierTxt, "width 180:180:180, wrap");

        JLabel lbl4 = new JLabel("Shipped Date:");
        this.shipDateTxt = new JTextField();
        this.shipDateTxt.setText(this.model.getShipDateStr() == null ? null
                : this.model.getShipDateStr().trim());
        mainPanel.add(lbl4);
        mainPanel.add(this.shipDateTxt, "width 180:180:180, wrap");

        JLabel lbl5 = new JLabel("Pkg Type:");
        this.pkgTypeTxt = new JTextField(new MaximumCharacterStyledDocument(1),
                "", 1);
        this.pkgTypeTxt.setText(this.model.getPkgType() == null ? null
                : this.model.getPkgType().toString().trim());
        mainPanel.add(lbl5);
        mainPanel.add(this.pkgTypeTxt, "width 180:180:180, wrap");

        JLabel lbl6 = new JLabel("To Store:");
        this.toStoreTxt = GeneralUtil.createMaskedTextField("*****");
        this.toStoreTxt.setText(this.model.getToStore() == null ? null
                : this.model.getToStore().toString());
        // For BI Transfers this field is to be non-editable and greyed out.
        if (this.model.getTranferType() == TransferConstants.XFER_TYPE_BI) {
            this.toStoreTxt.setEditable(false);
            this.toStoreTxt.setBackground(Color.LIGHT_GRAY);
        }
        mainPanel.add(lbl6);
        mainPanel.add(this.toStoreTxt, "width 180:180:180, wrap");

        JLabel lbl7 = new JLabel("Reason Code:");
        this.rcTxt = GeneralUtil.createMaskedTextField("*****");
        this.rcTxt.setText(this.model.getReasonCd() == null ? null : this.model
                .getReasonCd().toString());
        this.rcTxt.setEditable(false);
        this.rcTxt.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(lbl7);
        mainPanel.add(this.rcTxt, "width 180:180:180, wrap");

        this.msgLbl = new JLabel();
        this.msgLbl.setForeground(Color.RED);
        mainPanel.add(msgLbl, "span 2, wrap");

        return mainPanel;
    }

    /**
     * Accepts and validates the user's input of a Transfer Header detail
     * record.
     * 
     * @return an instance of {@link Transfer} capturing the user input.
     * @throws InvalidDataException
     *             validation errors
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        Transfer editModel = null;
        try {
            editModel = this.validateEdits();
            return editModel;
        } catch (InvalidDataException e) {
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw e;
        }
    }

    /**
     * Validates the Transfer header detail record containing the edits.
     * <p>
     * This method will perform validations common to all Transfer types. The
     * the manifest number and the pro bill must be a number and cannot be
     * longer than 9 ditis. The shipped date must be in the format of "mmddyyyy"
     * and must be a vaild date.
     * <p>
     * Store Initiated (SI) transfers require a specific set of validations
     * regarding the To-Store number. The To Store number 1) cannot be changed
     * to 22 when it originally equals something else, 2) cannot be changed when
     * it originally equals 22, and 3) must be checked against the database when
     * the store number has been leagally changed.
     * 
     * @return an instance of {@link Transfer} containing the validated transfer
     *         header data.
     * @throws InvalidDataException
     *             <ol>
     *             <li>The manifest number does not have a value, not numeric,
     *             or exceeds 9 digits.</li> <li>The pro bill does not have a
     *             value, not numeric, or exceeds 9 digits.</li> <li>The
     *             transfer date is invalid.</li> <li>For SI type transfers, the
     *             To-Store value entered
     *             <ul>
     *             <li>is null or blank,</li> <li>is not numeric,</li> <li>
     *             exceeds 5 digits,</li> <li>differs from its oiginal value of
     *             22,</li> <li>equals 22 when its original value was something
     *             other than 22, </li> <li>does not exists.</li>
     *             </ul>
     *             </li>
     *             </ol>
     */
    protected Transfer validateEdits() throws InvalidDataException {
        Transfer editModel = new Transfer();
        String val = null;

        // Validate Manifest no
        val = this.manifestTxt.getText();
        if (val == null || val.equals(GeneralUtil.spaces(val.length()))) {
            val = "0";
        }
        val = val.trim();
        if (!GeneralUtil.isNumeric(val)) {
            this.msg = "Manifest number must be numeric";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        if (val.length() > 9) {
            this.msg = "Manifest number must not exceed 9 digits";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        editModel.setManifestNo(Integer.parseInt(val));

        // Validate Pro Bill
        val = this.proBillTxt.getText();
        if (val == null || val.equals(GeneralUtil.spaces(val.length()))) {
            val = "0";
        }
        val = val.trim();
        if (!GeneralUtil.isNumeric(val)) {
            this.msg = "Pro bill number must be numeric";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        if (val.length() > 9) {
            this.msg = "Pro bill number must not exceed 9 digits";
            logger.error(this.msg);
            throw new InvalidDataException(this.msg);
        }
        editModel.setProBill(Integer.parseInt(val));

        // Validate Tranfer Date which is not required
        val = this.shipDateTxt.getText().trim();
        if (val == null || val.equals(GeneralUtil.spaces(val.length()))) {
            editModel.setShipDate(null);
        }
        else {
            val = val.trim();
            // Must be validated since a value was found

            try {
                Date d = GeneralUtil.validateLaunchPadDateEdits("Shipped Date",
                        val);
                editModel.setShipDate(d);
            } catch (Exception e) {
                this.msg = AppMessages.MSG_INVALID_DATE;
                logger.error(this.msg, e);
                throw new InvalidDataException(this.msg, e);
            }
        }

        // Validate To Store for SI Transfers
        if (this.model.getTranferType() == TransferConstants.XFER_TYPE_SI) {
            val = this.toStoreTxt.getText();
            if (val == null || val.equals(GeneralUtil.spaces(val.length()))) {
                this.msg = "To Store is required";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }
            if (!GeneralUtil.isNumeric(val)) {
                this.msg = "To Store number must be numeric";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }
            if (val.length() > 5) {
                this.msg = "To Store number must not exceed 5 digits";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }

            val = val.trim();
            editModel.setToStore(Integer.parseInt(val));
            if (this.model.getToStore() == 22 && editModel.getToStore() != 22) {
                this.msg = "To Store number cannot be changed when its orginal value is 22";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }
            if (this.model.getToStore() != 22 && editModel.getToStore() == 22) {
                this.msg = "To Store number cannot be changed to 22 when its original value\nis something other than 22";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }

            // validate To Store against the store table
            SecurityToken token = UserSecurityManager.getSecurityToken();
            GeneralDao dao = new GeneralDaoImpl(token);
            Store store = null;
            try {
                store = dao.fetchStore(editModel.getToStore());
            } catch (Exception e) {
                StringBuffer buf = new StringBuffer();
                buf.append("A critical database error occurred.\n");
                buf.append(e.getMessage());
                buf.append(".\n\nPlease contact the support team for assistance");
                this.msg = buf.toString();
                logger.fatal(this.msg, e);
                String errorTitle = (this.model.getTranferType() == TransferConstants.XFER_TYPE_BI ? "BI "
                        : "SI ")
                        + "Transfer Admin";
                JOptionPane.showMessageDialog(this, this.msg, errorTitle,
                        JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            } finally {
                dao.close();
                dao = null;
            }

            if (store == null) {
                this.msg = editModel.getToStore()
                        + " is not a valid store number for To Store field";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }
            String hmStore = store.getHomeStore();
            if (hmStore != null && hmStore.equalsIgnoreCase("x")) {
                this.msg = "Cannot transfer to the same store";
                logger.error(this.msg);
                throw new InvalidDataException(this.msg);
            }
        }
        else {
            editModel.setToStore(this.model.getToStore());
        }

        // Add the remaining fields to the edited model
        val = this.carrierTxt.getText().trim();
        editModel.setCarrierId(val);

        val = this.pkgTypeTxt.getText().trim();
        editModel.setPkgType(val);

        editModel.setReasonCd(this.model.getReasonCd());

        // Assign the primary key
        editModel.setTranferId(this.model.getTranferId());
        editModel.setTranferType(this.model.getTranferType());
        editModel.setMaster(this.model.getMaster());
        return editModel;
    }

    /**
     * Updates the header details of a Transfer record.
     * 
     * @param data
     *            an instance of {@link Transfer} containing the header data to
     *            be applied to the database as an update.
     * @return an instance of the updated {@link Transfer}
     * @throws LaunchPadException
     *             general database errors.
     */
    @Override
    public Object processData(Object data) throws LaunchPadException {
        Transfer item = null;
        if (data instanceof Transfer) {
            item = (Transfer) data;
        }
        else {
            return null;
        }

        // Apply updates to the database
        SecurityToken token = UserSecurityManager.getSecurityToken();

        // Determine which DAO we need to use.
        TransferDao dao = null;
        if (item.getTranferType() == TransferConstants.XFER_TYPE_SI) {
            SITransferFactory f = new SITransferFactory();
            dao = f.getDaoInstance(token);
        }
        else if (item.getTranferType() == TransferConstants.XFER_TYPE_BI) {
            BITransferFactory f = new BITransferFactory();
            dao = f.getDaoInstance(token);
        }
        else {
            StringBuffer buf = new StringBuffer();
            buf.append("Unable to update Transfer Header record [");
            buf.append(item.getTranferId());
            buf.append("].  Could not determine whether transfer type is SI or BI");
            buf.append(".\n\nPlease contact the support team for assistance.");
            this.msg = buf.toString();
            JOptionPane.showMessageDialog(this, this.msg,
                    "Database Error for Transfer Header Update Operation",
                    JOptionPane.ERROR_MESSAGE);
            throw new LaunchPadException(this.msg);
        }

        try {
            int rows = dao.updateTransfer(item);
            logger.info("Total rows effected by Transfer Header update operation: "
                    + rows);
            this.msg = "Transfer No., " + item.getTranferId()
                    + ", was successfully updated";
            logger.info(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, false);
            this.modifiedModel = item;
            return item;
        } catch (Exception e) {
            StringBuffer buf = new StringBuffer();
            buf.append("A critical database error occurred.\n");
            buf.append(e.getMessage());
            buf.append(".\n\nPlease contact the support team for assistance");
            this.msg = buf.toString();
            logger.fatal(this.msg, e);
            String errorTitle = (this.model.getTranferType() == TransferConstants.XFER_TYPE_BI ? "BI "
                    : "SI ")
                    + "Transfer Admin";
            JOptionPane.showMessageDialog(this, this.msg, errorTitle,
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        } finally {
            dao.close();
            dao = null;
        }
    }

    /**
     * @return the modifiedModel
     */
    public Transfer getModifiedModel() {
        return modifiedModel;
    }

    // /* (non-Javadoc)
    // * @see
    // java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
    // */
    // @Override
    // public void focusGained(FocusEvent e) {
    // if (e.getSource() instanceof JTextField) {
    // JTextField f = (JTextField) e.getSource();
    // f.setText(f.getText().trim());
    // }
    // return;
    // }
    //
    // /* (non-Javadoc)
    // * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
    // */
    // @Override
    // public void focusLost(FocusEvent e) {
    // if (e.getSource() instanceof JTextField) {
    // JTextField f = (JTextField) e.getSource();
    // f.setText(f.getText().trim());
    // }
    // return;
    //
    // }

}

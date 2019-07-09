package modules.priceaudit;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JPanel;

import modules.GenericQtyInputDialog;

import org.apache.log4j.Logger;

import com.InvalidDataException;
import com.nv.util.AppUtil;

/**
 * This is a dialog for entering quantity values specifically for a Price Audit
 * record.
 * 
 * @author rterrell
 *
 */
public class PriceAuditMarkedQtyDialog extends GenericQtyInputDialog {

    private static final long serialVersionUID = -3700408857611961791L;

    private static final Logger logger = Logger
            .getLogger(PriceAuditMarkedQtyDialog.class);

    /**
     * Creates a PriceAuditMarkedQtyDialog initialized with its parent, size,
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
     * @param qty
     *            the quantity to be displayed
     */
    public PriceAuditMarkedQtyDialog(Window owner, Dimension size, Point pos,
            String winTitle, String inputLabel, Integer qty) {
        super(owner, size, pos, winTitle, inputLabel, qty);
        return;
    }

    /**
     * Verifies that the quantity sent value is in the range of 0 t0 9999
     * 
     * @return the quatnity entered by the user as a String
     * @throws InvalidDataException
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        // At this point, we have a valid numeric String as quantity
        Object val = super.getInputData();
        int intVal = Integer.parseInt(val.toString());
        boolean validRange = (intVal >= 0 && intVal <= 9999);
        if (!validRange) {
            this.msg = "Quantity must be in the range 0 to 9999";
            logger.error(this.msg);
            AppUtil.showMessage(this.msgLbl, this.msg, true);
            throw new InvalidDataException(this.msg);
        }
        return val;
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.GenericQtyInputDialog#createContentLayout()
     */
    @Override
    public JPanel createContentLayout() {
        this.setMask("****");
        return super.createContentLayout();
    }
}

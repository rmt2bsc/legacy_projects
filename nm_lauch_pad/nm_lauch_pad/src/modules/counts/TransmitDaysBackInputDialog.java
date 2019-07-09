package modules.counts;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.InvalidDataException;
import com.nv.util.AppUtil;

import modules.GenericQtyInputDialog;

/**
 * This is a dialog for entering the number of days back from the current date
 * for showing transmitted count headers.
 * 
 * @author rterrell
 *
 */
public class TransmitDaysBackInputDialog extends GenericQtyInputDialog {

    private static final long serialVersionUID = -3700408857611961791L;

    private static final Logger logger = Logger
            .getLogger(TransmitDaysBackInputDialog.class);

    /**
     * Creates a TransmitDaysBackInputDialog initialized with its parent, size,
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
    public TransmitDaysBackInputDialog(Window owner, Dimension size, Point pos,
            String winTitle, String inputLabel) {
        super(owner, size, pos, winTitle, inputLabel);
        return;
    }

    /**
     * Verifies that the number of days value is in the range of 0 t0 999
     * 
     * @return the number of days entered by the user as a String
     * @throws InvalidDataException
     */
    @Override
    public Object getInputData() throws InvalidDataException {
        // At this point, we have a valid numeric String as quantity
        Object val = super.getInputData();
        int intVal = Integer.parseInt(val.toString());
        boolean validRange = (intVal >= 0 && intVal <= 999);
        if (!validRange) {
            this.msg = "Number of days value must be in the range 0 to 999";
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
        this.setMask("***");
        return super.createContentLayout();
    }
}

package modules;

import javax.swing.ImageIcon;

/**
 * A Value object managing the details of a menu item.
 * 
 * @author rterrell
 *
 */
public class MainMenuItem {
    private String displayValue;

    private ImageIcon image;

    private boolean secure;

    private int processId;

    /**
     * @return the displayValue
     */
    public String getDisplayValue() {
        return displayValue;
    }

    /**
     * @param displayValue
     *            the displayValue to set
     */
    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    /**
     * @return the image
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * @param image
     *            the image to set
     */
    public void setImage(ImageIcon image) {
        this.image = image;
    }

    /**
     * @return the secure
     */
    public boolean isSecure() {
        return secure;
    }

    /**
     * @param secure
     *            the secure to set
     */
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    /**
     * @return the processId
     */
    public int getProcessId() {
        return processId;
    }

    /**
     * @param processId
     *            the processId to set
     */
    public void setProcessId(int processId) {
        this.processId = processId;
    }
}

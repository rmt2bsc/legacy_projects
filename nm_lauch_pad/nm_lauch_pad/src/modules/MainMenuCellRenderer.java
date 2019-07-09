package modules;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.nv.security.SecurityToken;
import com.nv.security.UserSecurityManager;
import com.nv.util.AppUtil;

/**
 * Cell renderer for the Launch Pad main menu.
 * <p>
 * It selectively displays menu items based on whether or not the user is logged
 * in.
 * 
 * @author rterrell
 *
 */
public class MainMenuCellRenderer extends JLabel implements ListCellRenderer {

    private static final long serialVersionUID = -5341904665219245042L;

    /**
     * Create a MainMenuCellRenderer with a prefered size of 420 width ad 60
     * height.
     */
    public MainMenuCellRenderer() {
        // This setting is required in order to actually see the highlighting of
        // a row being selected
        this.setOpaque(true);

        // Set the expected size of each item row.
        this.setPreferredSize(new Dimension(400, 40));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing
     * .JList, java.lang.Object, int, boolean, boolean)
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        MainMenuItem item = (MainMenuItem) value;
        SecurityToken token = UserSecurityManager.getSecurityToken();

        if (token == null) {
            return new JLabel();
        }

        // When not logged in, exclude those menu items that are considered
        // "secure"
        if (item.isSecure()) {
            if (token.getUserId() > 0 && token.getSignOn() != null
                    && token.getSecurityLevel() == 3) {
                // We've got a valid user...go ahead a display menu item.
            }
            else {
                return new JLabel();
            }
        }

        if (token.getUserId() > 0 && token.getSignOn() != null
                && token.getSecurityLevel() == 3) {
            // When user is logged in, exclude displaying the "Login" menu item.
            // The menu item's process id is equal "100"
            if (item.getProcessId() == 100) {
                return new JLabel();
            }

            // Disable IDT Administration menu item provided the IDT Admin flag
            // does not exists
            if (item.getProcessId() == MainMenuListModelImpl.IDT_PROCESS_ID) {
                String flag = AppUtil
                        .getAppProperty(GeneralDao.IDT_ADMIN_FLAG_VALUE);
                if (flag == null) {
                    return new JLabel();
                }
            }
        }

        this.setText(item.getDisplayValue());
        this.setIcon(item.getImage());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        this.setEnabled(list.isEnabled());
        this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        this.setVisible(true);
        return this;
    }

}

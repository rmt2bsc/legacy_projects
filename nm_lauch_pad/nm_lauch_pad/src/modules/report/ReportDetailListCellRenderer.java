package modules.report;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import modules.model.ReportDetail;

/**
 * Cell renderer for the report detail list component.
 * 
 * @author rterrell
 *
 */
public class ReportDetailListCellRenderer extends JLabel implements
        ListCellRenderer {

    private static final long serialVersionUID = -5341904665219245042L;

    /**
     * Create a ReportHeaderListCellRenderer with a prefered size of 420 width
     * ad 60 height.
     */
    public ReportDetailListCellRenderer() {
        // This setting is required in order to actually see the highlighting of
        // a row being selected
        this.setOpaque(true);

        // Set the expected size of each item row.
        // this.setPreferredSize(new Dimension(200, 100));
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
        ReportDetail item = (ReportDetail) value;
        this.setText(item.getRptDesc().trim());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        this.setEnabled(list.isEnabled());
        this.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        this.setVisible(true);
        return this;
    }

}

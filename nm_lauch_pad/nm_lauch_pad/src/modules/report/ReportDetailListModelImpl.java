package modules.report;

import java.util.List;

import javax.swing.AbstractListModel;

import modules.model.ReportDetail;

/**
 * Launch Pad's implementation of AbstractListModel for the report detail list
 * component.
 * 
 * @author rterrell
 *
 */
public class ReportDetailListModelImpl extends AbstractListModel {

    private static final long serialVersionUID = 8659567350752547834L;

    private List<ReportDetail> items;

    /**
     * Creates an ReportDetailListModelImpl object initialized with a list of
     * report detail items.
     * 
     * @param items
     */
    public ReportDetailListModelImpl(List<ReportDetail> items) {
        this.items = items;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ListModel#getSize()
     */
    @Override
    public int getSize() {
        return (this.items == null ? 0 : this.items.size());
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Override
    public Object getElementAt(int index) {
        return (this.items == null ? null : this.items.get(index));
    }

    /**
     * @param items
     *            the items to set
     */
    public void setItems(List<ReportDetail> items) {
        this.items = items;
    }

    public void update() {
        this.fireContentsChanged(this, 0, items.size() - 1);
    }

}

package modules.report;

import java.util.List;

import javax.swing.AbstractListModel;

import modules.model.ReportHeader;

/**
 * Launch Pad's implementation of AbstractListModel for the report header list
 * component.
 * 
 * @author rterrell
 *
 */
public class ReportHeaderListModelImpl extends AbstractListModel {

    private static final long serialVersionUID = 8659567350752547834L;

    private List<ReportHeader> items;

    /**
     * Creates an ReportHeaderListModelImpl object initialized with a list of
     * report header items.
     * 
     * @param items
     */
    public ReportHeaderListModelImpl(List<ReportHeader> items) {
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

}

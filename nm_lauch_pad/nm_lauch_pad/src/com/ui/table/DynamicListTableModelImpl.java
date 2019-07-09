package com.ui.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.nv.util.BeanUtility;

/**
 * An imiplementation of {@link DynamicListTableModel} for managing JTable
 * presentation data.
 * <p>
 * This implementation requires the presentation data to exist as a List of
 * arbitrary java beans. Another requirement is one or more
 * {@link ColumnDefinition} objects must exist in order to perform the
 * appropriate mapping of properties from the view (JTable) to the model and
 * vice versa. The {@link ColumnDefinition} is the crux to a properly
 * functioning {@link DynamicListTableModel}.
 * 
 * @author rterrell
 *
 */
public class DynamicListTableModelImpl extends AbstractTableModel implements
        DynamicListTableModel {

    private static final long serialVersionUID = -7964258951414564558L;

    private List data;

    private List<ColumnDefinition> cols;

    /**
     * Creates a <i>DynamicListTableModelImpl</i> initialized with a List of
     * data items to be displayed and a List of column definitions.
     */
    public DynamicListTableModelImpl(List data, List<ColumnDefinition> cols) {
        this.data = data;
        this.cols = cols;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        return (this.data == null ? 0 : this.data.size());
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        return (this.cols == null ? 0 : this.cols.size());
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (this.data != null && this.data.size() > 0 && this.cols != null
                && this.cols.size() > 0) {
            // Continue...
        }
        else {
            return null;
        }

        Object bean = this.data.get(rowIndex);
        ColumnDefinition colDef = this.cols.get(columnIndex);
        BeanUtility bu = null;
        try {
            bu = new BeanUtility(bean);
            Object val = bu.getPropertyValue(colDef.getId());
            return val;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ui.table.DynamicListTableModel#getSelectedRowData(int)
     */
    @Override
    public Object getSelectedRowData(int rowIndex) {
        return this.data.get(rowIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ui.table.DynamicListTableModel#addRow(java.lang.Object)
     */
    @Override
    public int addRow(Object rowData) {
        if (rowData == null) {
            return -1;
        }
        this.data.add(rowData);
        this.fireTableRowsInserted(0, 0);
        return this.data.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ui.table.DynamicListTableModel#removeRow(int)
     */
    @Override
    public int removeRow(int rowIndex) {
        boolean indexValidRange = (rowIndex >= 0 && rowIndex <= this
                .getRowCount());
        if (!indexValidRange) {
            return -1;
        }
        this.data.remove(rowIndex);
        this.fireTableRowsDeleted(0, 0);
        return 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class c = super.getColumnClass(columnIndex);
        return c;
    }

}

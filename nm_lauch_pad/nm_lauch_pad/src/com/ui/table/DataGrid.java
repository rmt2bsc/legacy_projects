package com.ui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.nv.util.AppUtil;
import com.nv.util.GeneralUtil;

/**
 * An extension of {@link JTable} bearing common functionality for the Launch
 * Pad application.
 * 
 * @author rterrell
 *
 */
public class DataGrid extends JTable {

    private static final long serialVersionUID = -8132586284229300549L;

    private Color normBg;

    private Color normFg;

    private Color altBg;

    private Color altFg;

    private Color selBg;

    private Color selFg;

    private String msg;

    /**
     * Create DataGrid without a table model, table column model, and a list
     * selection model.
     */
    protected DataGrid() {
        this.init();
    }

    /**
     * Create DataGrid initialized with a table model, table column model, and a
     * list selection model.
     * 
     * @param dm
     *            an instance of {@link DynamicListTableModel} representing the
     *            table model for the JTable.
     * @param cm
     *            an instance of {@link TableColumnModel} representing the table
     *            column modle for the JTable.
     * @param sm
     *            an instance of {@link ListSelectionModel} representing the
     *            list selection model for the JTable.
     */
    public DataGrid(DynamicListTableModel dm, TableColumnModel cm,
            ListSelectionModel sm) {
        super(dm, cm, sm);
        this.init();
    }

    /**
     * Performs basic initialization of the DataGrid.
     */
    protected void init() {
        // this.setCellSelectionEnabled(false);
        this.setAutoCreateRowSorter(true);
        this.setRowSelectionAllowed(true);
        this.setColumnSelectionAllowed(false);
        this.setShowGrid(false);

        // Validate column definiations
        this.validateColumnDefs();

        // Configure data grid row colors
        this.configTableGrid();

        // Enable the table's resize property for all columns to resize when the
        // parent window is resized. This setting will enaable the vertical
        // scrollbar and disable the usage of the horizontal scrollbar of the
        // JScrollPane component. Also, Swing ignores column widths set by you
        // when resize mode of the JTable is not AUTO_RESIZE_OFF. When setting
        // the column widths, use setPreferedWidth instead of setWidth.
        this.setAutoResizeMode(DataGrid.AUTO_RESIZE_ALL_COLUMNS);

        Font df = AppUtil.getDataGridRowFont();
        Font hf = AppUtil.getDataGridHeaderFont();
        this.setFont(df);
        this.getTableHeader().setFont(hf);

        return;
    }

    /**
     * Identify and configure the background and foreground colors of the
     * table's grid.
     */
    private void configTableGrid() {
        Properties props = GeneralUtil
                .loadProperties(AppUtil.CONFIG_COMMON_FILE);
        this.normBg = GeneralUtil.createColorInstance(
                props.getProperty("datagrid.norm.bg"), Color.WHITE);
        this.normFg = GeneralUtil.createColorInstance(
                props.getProperty("datagrid.norm.fg"), Color.BLACK);
        this.altBg = GeneralUtil.createColorInstance(
                props.getProperty("datagrid.alt.bg"), Color.WHITE);
        this.altFg = GeneralUtil.createColorInstance(
                props.getProperty("datagrid.alt.fg"), Color.BLACK);
        this.selBg = GeneralUtil.createColorInstance(
                props.getProperty("datagrid.sel.bg"), Color.WHITE);
        this.selFg = GeneralUtil.createColorInstance(
                props.getProperty("datagrid.sel.fg"), Color.BLACK);
    }

    /**
     * Ensure that the index property of each {@link TableColumn} model instance
     * does not equal or exceed the to total number of column defintions.
     * 
     * @throws ColumnDefinitionException
     *             When the index property of a table column definition is found
     *             to exceed the bounds of the List of
     *             {@link com.ui.table.ColumnDefinition}.
     */
    private void validateColumnDefs() throws ColumnDefinitionException {
        TableColumnModel model = this.getColumnModel();
        int colCount = model.getColumnCount();
        Enumeration<TableColumn> e = model.getColumns();
        while (e.hasMoreElements()) {
            TableColumn c = e.nextElement();
            if (c.getModelIndex() >= colCount) {
                StringBuffer buf = new StringBuffer();
                buf.append("The index for table column definition, ");
                buf.append(c.getHeaderValue());
                buf.append(", is out or range [Index=");
                buf.append(c.getModelIndex());
                buf.append(", Column Total=");
                buf.append(colCount);
                buf.append("].  Check ColumnDefinition configuration for JTable instance, ");
                buf.append(this.getClass().getName());
                this.msg = buf.toString();
                throw new ColumnDefinitionException(this.msg);
            }
        }
    }

    /**
     * Returns one or more arbitrary objects that corresponds to the total
     * number of selected rows in the JTable view.
     * 
     * @return a List of arbitrary model objects that are stored at the selected
     *         indexes. Returns null when there are no rows selected.
     */
    public List<Object> getAllSelectedRowData() {
        int rows[] = this.getSelectedRows();
        if (rows.length == 0) {
            return null;
        }
        List<Object> list = new ArrayList<Object>();
        for (int ndx = 0; ndx < rows.length; ndx++) {
            Object item = this.getSelectedRowData(rows[ndx]);
            list.add(item);
        }
        return list;
    }

    /**
     * Returns the model object that corresponds to the selected row of the
     * JTable view.
     * 
     * @param viewRow
     *            the index of the selected row
     * @return an arbitrary object that stored in the model at position
     *         <i>viewRow</i>. Returns null when <i>viewRow</i> is a number less
     *         than zero.
     */
    public Object getSelectedRowData(int viewRow) {
        if (viewRow < 0) {
            return null;
        }
        DynamicListTableModel model = (DynamicListTableModel) this.getModel();
        int mappedRow = this.convertRowIndexToModel(viewRow);
        return model.getSelectedRowData(mappedRow);
    }

    /**
     * Alternates the row colors
     */
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex,
            int vColIndex) {
        Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);

        // Default backgroune and text to normal colors
        c.setForeground(this.normFg);
        c.setBackground(this.normBg);

        boolean rowSelected = isCellSelected(rowIndex, vColIndex);
        if (rowSelected) {
            c.setBackground(this.selBg);
            c.setForeground(this.selFg);
            return c;
        }
        if (rowIndex % 2 == 0 && !rowSelected) {
            c.setBackground(this.altBg);
            c.setForeground(this.altFg);
        }
        else {
            // If not shaded, match the table's background
            c.setBackground(getBackground());
        }
        return c;
    }

}

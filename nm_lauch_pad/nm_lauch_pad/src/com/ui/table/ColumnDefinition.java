package com.ui.table;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Java bean class that manages the JTable-to-Model mapping definition details
 * of a specified column.
 * 
 * @author rterrell
 *
 */
public class ColumnDefinition {
    private String id;

    private String headerValue;

    private Integer index;

    private Integer maxWidth;

    private Integer minWidth;

    private Integer prefWidth;

    private Integer width;

    private boolean resizeable;

    private TableCellEditor cellEditor;

    private TableCellRenderer cellRenderer;

    private TableCellRenderer headerRenderer;

    /**
     * Creates a {@link ColumnDefinition} without any column definitions.
     * <p>
     * Defaults all columns to be resizable. This will allow the user to employ
     * a pointing device such as a mouse to adjust the width of a column by
     * dragging the borders of the column header inward and outward.
     */
    public ColumnDefinition() {
        this.resizeable = true;
        return;
    }

    /**
     * Creates a {@link ColumnDefinition} identified by the column's assoicated
     * bean property name, its header display value, and its position in JTable
     * column list.
     * 
     * @param propertyName
     *            The property name belonging to the model (java bean) that will
     *            map to the JTable column.
     * @param displayValue
     *            The description of the column that will be displayed as column
     *            header
     * @param index
     *            The column postion where the column will be placed in the
     *            JTable.
     */
    public ColumnDefinition(String propertyName, String displayValue, int index) {
        this();
        this.id = propertyName;
        this.headerValue = displayValue;
        this.index = index;
    }

    /**
     * Creates a {@link ColumnDefinition} identified by the column's assoicated
     * bean property name, its header display value, its position in JTable
     * column list, and its width.
     * 
     * param propertyName The property name belonging to the model (java bean)
     * that will map to the JTable column.
     * 
     * @param displayValue
     *            The description of the column that will be displayed as column
     *            header
     * @param index
     *            The column postion where the column will be placed in the
     *            JTable.
     * @param width
     *            The width of the column. This will also set the prefered width
     *            property to this value.
     */
    public ColumnDefinition(String propertyName, String displayValue,
            int index, int width) {
        this(propertyName, displayValue, index);
        this.width = width;
        this.prefWidth = width;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the headerValue
     */
    public String getHeaderValue() {
        return headerValue;
    }

    /**
     * @param headerValue
     *            the headerValue to set
     */
    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @param index
     *            the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * @return the maxWidth
     */
    public Integer getMaxWidth() {
        return maxWidth;
    }

    /**
     * @param maxWidth
     *            the maxWidth to set
     */
    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * @return the minWidth
     */
    public Integer getMinWidth() {
        return minWidth;
    }

    /**
     * @param minWidth
     *            the minWidth to set
     */
    public void setMinWidth(Integer minWidth) {
        this.minWidth = minWidth;
    }

    /**
     * @return the prefWidth
     */
    public Integer getPrefWidth() {
        return prefWidth;
    }

    /**
     * @param prefWidth
     *            the prefWidth to set
     */
    public void setPrefWidth(Integer prefWidth) {
        this.prefWidth = prefWidth;
    }

    /**
     * @return the width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return the resizeable
     */
    public boolean isResizeable() {
        return resizeable;
    }

    /**
     * @param resizeable
     *            the resizeable to set
     */
    public void setResizeable(boolean resizeable) {
        this.resizeable = resizeable;
    }

    /**
     * @return the cellEditor
     */
    public TableCellEditor getCellEditor() {
        return cellEditor;
    }

    /**
     * @param cellEditor
     *            the cellEditor to set
     */
    public void setCellEditor(TableCellEditor cellEditor) {
        this.cellEditor = cellEditor;
    }

    /**
     * @return the cellRenderer
     */
    public TableCellRenderer getCellRenderer() {
        return cellRenderer;
    }

    /**
     * @param cellRenderer
     *            the cellRenderer to set
     */
    public void setCellRenderer(TableCellRenderer cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    /**
     * @return the headerRenderer
     */
    public TableCellRenderer getHeaderRenderer() {
        return headerRenderer;
    }

    /**
     * @param headerRenderer
     *            the headerRenderer to set
     */
    public void setHeaderRenderer(TableCellRenderer headerRenderer) {
        this.headerRenderer = headerRenderer;
    }
}

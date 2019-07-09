package com.ui.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Builds a table column model, {@link TableColumnModel}, in which the model is
 * based on one or more {@link ColumnDefinition} instances.
 * <p>
 * The table column model created by this builder is expected to used with
 * {@link DataGrid}.
 * <p>
 * This class uses instances of {@link ColumnDefinition}, which represents
 * information regarding a single colmun, to build TableColumn objects that
 * typically comprises the {@link TableColumnModel}.
 * 
 * @author rterrell
 *
 */
public class ColumnModelBuilder {

    private TableColumnModel model;

    private List<ColumnDefinition> defList;

    /**
     * Creates a ColumnModelBuilder initialized with an empty table model and an
     * empty column definition list.
     */
    public ColumnModelBuilder() {
        this.model = new DefaultTableColumnModel();
        this.defList = new ArrayList<ColumnDefinition>();
    }

    /**
     * Creates a {@link TableColumn} instance and adds it to the table model
     * using <i>def</i> and adds the <i>def</i> instance to the list of column
     * definitions that is maintained by this class.
     * 
     * @param def
     *            an instance of {@link ColumnDefinition}
     * @return boolean true when <i>def</i> is converted to a
     *         {@link TableColumn} and added to the list column definitions
     *         successfully. Returns false when <i>def</i> itself or its
     *         properties, <i>id</i> or <i>headerValue</i>, are null.
     */
    public boolean addCol(ColumnDefinition def) {
        if (def == null) {
            return false;
        }
        if (def.getId() == null || def.getId().equals("")) {
            return false;
        }
        if (def.getHeaderValue() == null || def.getHeaderValue().equals("")) {
            return false;
        }

        if (def.getIndex() == null || def.getIndex() < 0) {
            def.setIndex(this.model.getColumnCount());
        }

        // Create TableColumn instance using the approriate constructor
        TableColumn col = null;
        if (def.getWidth() != null && def.getCellRenderer() != null
                && def.getCellEditor() != null) {
            col = new TableColumn(def.getIndex(), def.getWidth(),
                    def.getCellRenderer(), def.getCellEditor());
        }
        else if (def.getWidth() != null) {
            col = new TableColumn(def.getIndex(), def.getWidth());
        }
        else {
            col = new TableColumn(def.getIndex());
        }

        // Continue to set the remaining properties as needed.
        col.setIdentifier(def.getId());
        col.setHeaderValue(def.getHeaderValue());
        col.setResizable(def.isResizeable());

        if (def.getPrefWidth() != null) {
            col.setPreferredWidth(def.getPrefWidth());
        }
        if (def.getMaxWidth() != null) {
            col.setMaxWidth(def.getMaxWidth());
        }
        if (def.getMinWidth() != null) {
            col.setMinWidth(def.getMinWidth());
        }

        this.model.addColumn(col);
        this.defList.add(def);
        return true;
    }

    /**
     * Removes a column from the table column model.
     * <p>
     * 
     * @param propName
     *            the name of the column assoicated with the table column model.
     * @return boolean true when the column is removed and false otherwise.
     */
    public boolean removeCol(String propName) {
        try {
            int ndx = this.model.getColumnIndex(propName);
            TableColumn colDef = this.model.getColumn(ndx);
            this.model.removeColumn(colDef);
        } catch (Exception e) {
            // Column Def was not found in the Table Column Modle.
            return false;
        }

        // Verify that the column has been removed.
        try {
            this.model.getColumnIndex(propName);
            // This indicates that the column still exists
            return false;
        } catch (Exception e) {
            // Column was removed successfully
            return true;
        }
    }

    /**
     * Return the List of column definitions.
     * 
     * @return List of {@link ColumnDefinition}
     */
    public List<ColumnDefinition> getDefList() {
        return defList;
    }

    /**
     * Retrun the table model
     * 
     * @return {@link TableColumnModel}
     */
    public TableColumnModel getModel() {
        return this.model;
    }
}

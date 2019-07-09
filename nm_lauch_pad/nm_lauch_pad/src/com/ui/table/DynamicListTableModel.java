package com.ui.table;

import javax.swing.table.TableModel;

/**
 * A table model interface which serves as an extension of {@link TableModel}.
 * <p>
 * It provides the ability to return an arbitrary object from JTables model
 * based on its index.
 * 
 * @author rterrell
 *
 */
public interface DynamicListTableModel extends TableModel {

    /**
     * Obtain the data object from the TableModel's List indexed at
     * <i>rowIndex</i>.
     * <p>
     * If the contents of the view has been resorted, then <i>rowIndex</i> is
     * mapped to the underlying table model's index. If the view contents have
     * not been sorted then the model and view indices are the same.
     * 
     * @param rowIndex
     *            the index of the row in the view that is to be mapped to the
     *            row in the table model.
     * @return an arbitrary object indexed from the taable model's List.
     */
    Object getSelectedRowData(int rowIndex);

    /**
     * Adds a row to the table model.
     * 
     * @param rowData
     * @return int 1 is returned when the row is added successfully or -1 if the
     *         add operation failed.
     */
    int addRow(Object rowData);

    /**
     * Deletes a row from the table model.
     * 
     * @param rowIndex
     * @return int 1 is returned when the row is deleted successfully or -1 if
     *         the delete operation failed.
     */
    int removeRow(int rowIndex);

}

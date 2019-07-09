package com.ui.table;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.DefaultRowSorter;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import com.nv.util.DateStringComparator;
import com.nv.util.NumericStringComparator;
import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.event.ComponentItemSelectedEvent;
import com.ui.event.ComponentSelectionListener;
import com.ui.event.EventDispatcher;
import com.ui.event.EventDispatcherImpl;

/**
 * An extension of {@link JScrollPane} encapsulating an instance of
 * {@link DataGrid}.
 * <p>
 * <b>How To Use</b>
 * 
 * <pre>
 * // This is an empty list. For all intents and purposes, imagine that
 * // the list is populated with arbitrary java beans to be displayed as
 * // grid data.
 * List listData = new ArrayList();
 * 
 * // Setup column definitions
 * List&lt;ColumnDefinition&gt; colDefs = new ArrayList&lt;ColumnDefinition&gt;();
 * colDefs.add(new ColumnDefinition(&quot;server&quot;, &quot;Server&quot;, 0));
 * colDefs.add(new ColumnDefinition(&quot;storeNo&quot;, &quot;Store&quot;, 1));
 * 
 * // Set the size of the data grid component
 * Dimension size = new Dimension(400, 175);
 * 
 * // Create ScrollableDataGrid grid component
 * ScrollableDataGrid dg = new ScrollableDataGrid(list, colDefs,
 *         ListSelectionModel.SINGLE_SELECTION, size);
 * 
 * // Add parent class instance which should be an implementation of
 * // ComponentSelectionListener interface
 * dg.addItemSelectionListener(this);
 * </pre>
 * 
 * 
 * @author rterrell
 *
 */
public class ScrollableDataGrid extends JScrollPane implements
        ListSelectionListener {

    private static final long serialVersionUID = -3565769493647636386L;

    private static final int EVENT_ID_SELECTIONCHANGED = 100;

    private static final int EVENT_ID_DOUBLECLICKED = 101;

    private static Logger logger;

    private DataGrid tableView;

    private List tableData;

    private List<ColumnDefinition> colDefs;

    private TableColumnModel colModel;

    private Object selection;

    private int selectionMode;

    private Dimension size;

    private EventListenerList listenerList;

    private EventDispatcher evtDispatcher;

    /**
     * Message text memeber variable
     */
    protected String msg;

    /**
     * Create a ScrollableDataGrid with a default width and height of 400 by 400
     * and selection mode of
     * {@link ListSelectionModel#MULTIPLE_INTERVAL_SELECTION
     * MULTIPLE_INTERVAL_SELECTION}
     */
    protected ScrollableDataGrid() {
        super();
        logger = Logger.getLogger(ScrollableDataGrid.class);
        logger.info("Logger Intializied");
        this.size = new Dimension(400, 400);
        this.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
    }

    /**
     * Create a ScrollableDataGrid initialized with a specified list of data to
     * be displayed and related column definitions for each element included in
     * <i>tableData</i>, a default width and height of 400 by 400 and selection
     * mode of {@link ListSelectionModel#MULTIPLE_INTERVAL_SELECTION
     * MULTIPLE_INTERVAL_SELECTION}
     * 
     * @param tableData
     *            a List of arbitrary objects to be associated with the table
     *            model and presented as the data grid.
     * @param colDefs
     *            a List of {@link ColumnDefinition} objects to be assoicated
     *            with underlying JTable.
     */
    public ScrollableDataGrid(List tableData, List<ColumnDefinition> colDefs) {
        this();
        this.colDefs = colDefs;
        this.initGrid(tableData);
    }

    /**
     * Create a ScrollableDataGrid initialized with a specified list of data to
     * be displayed and related column definitions for each element included in
     * <i>tableData</i>, a default width and height of 400 by 400, and the
     * specified selection mode of <i>selectionMode</i>.
     * 
     * @param tableData
     *            a List of arbitrary objects to be associated with the table
     *            model and presented as the data grid.
     * @param colDefs
     *            a List of {@link ColumnDefinition} objects to be assoicated
     *            with underlying JTable.
     * @param selectionMode
     *            a int value representing one of the following values:
     *            <ul>
     *            <li>{@link ListSelectionModel#MULTIPLE_INTERVAL_SELECTION
     *            MULTIPLE_INTERVAL_SELECTION}</li>
     *            <li>{@link ListSelectionModel#SINGLE_INTERVAL_SELECTION
     *            SINGLE_INTERVAL_SELECTION}</li>
     *            <li>{@link ListSelectionModel#SINGLE_SELECTION
     *            SINGLE_SELECTION}</li>
     *            </ul>
     * 
     */
    public ScrollableDataGrid(List tableData, List<ColumnDefinition> colDefs,
            int selectionMode) {
        this();
        this.colDefs = colDefs;
        this.selectionMode = selectionMode;
        this.initGrid(tableData);
    }

    /**
     * Create a ScrollableDataGrid initialized with a specified list of data to
     * be displayed and related column definitions for each element included in
     * <i>tableData</i>, a specified selection mode of <i>selectionMode</i>, and
     * a specified component size.
     * 
     * @param tableData
     *            a List of arbitrary objects to be associated with the table
     *            model and presented as the data grid.
     * @param colDefs
     *            a List of {@link ColumnDefinition} objects to be assoicated
     *            with underlying JTable.
     * @param selectionMode
     *            a int value representing one of the following values:
     *            <ul>
     *            <li>{@link ListSelectionModel#MULTIPLE_INTERVAL_SELECTION
     *            MULTIPLE_INTERVAL_SELECTION}</li>
     *            <li>{@link ListSelectionModel#SINGLE_INTERVAL_SELECTION
     *            SINGLE_INTERVAL_SELECTION}</li>
     *            <li>{@link ListSelectionModel#SINGLE_SELECTION
     *            SINGLE_SELECTION}</li>
     *            </ul>
     * @param size
     *            an instance of {@link Dimension}.
     */
    public ScrollableDataGrid(List tableData, List<ColumnDefinition> colDefs,
            int selectionMode, Dimension size) {
        this();
        this.tableData = tableData;
        this.colDefs = colDefs;
        this.selectionMode = selectionMode;
        this.size = size;
        this.initGrid(tableData);
    }

    /**
     * Create the structure of the underlying JTable by initializing the table
     * model, table column model, list selection model, and mouse listeners.
     */
    private void initGrid(List tableData) {
        if (this.colDefs == null) {
            return;
        }
        // create column model builder and load column definitions
        ColumnModelBuilder builder = new ColumnModelBuilder();
        for (ColumnDefinition def : this.colDefs) {
            builder.addCol(def);
        }

        // Create TableColumnModel
        this.colModel = builder.getModel();

        // Setup event listener
        this.listenerList = new EventListenerList();

        // Setup Event dispatcher
        this.evtDispatcher = new EventDispatcherImpl(this.listenerList);

        // Load JTable view
        this.tableView = this.loadView(tableData);
    }

    /**
     * Loads the {@link DataGrid} view with the list of data, assoicates the
     * view with the model, and adds the view to the scrollable pane's view
     * port.
     * <p>
     * Call this method when loading the view for the first time and when the
     * need arises to refresh the view with a new list. This method takes care
     * of repainting.
     * 
     * @param dataList
     *            a List of arbitrary objects that will be displayed in the
     *            grid.
     * @return an new instance of {@link DataGrid}
     */
    public DataGrid loadView(List dataList) {
        this.tableData = dataList;
        // Create list selection model from default implementation
        ListSelectionModel listSelModel = new DefaultListSelectionModel();
        // Set selection mode
        listSelModel.setSelectionMode(this.selectionMode);
        // Create tableView model
        DynamicListTableModelImpl tableModel = new DynamicListTableModelImpl(
                this.tableData, this.colDefs);
        // Construct DataGrid instance
        this.tableView = new DataGrid(tableModel, this.colModel, listSelModel);
        // Assign DataGrid instance as the viewpot for this JScrollPane so the
        // tableView is scrollable
        this.tableView.setPreferredScrollableViewportSize(this.size);
        this.tableView.setFillsViewportHeight(true);
        this.setViewportView(this.tableView);
        // Assign mouse listener
        this.tableView.addMouseListener(new TableViewMouseAdapter(this));
        this.tableView.getSelectionModel().addListSelectionListener(this);
        // repaint the component
        this.repaint();

        // Return results
        return this.tableView;
    }

    /**
     * Assings a {@link NumericStringComparator} comparator to every column
     * specified in <i>cols</i> for the purpose of sorting String values as
     * numerics.
     * 
     * @param cols
     *            an interger array containing the column indexes that require a
     *            {@link NumericStringComparator} instance.
     * @throws RuntimeException
     *             if a column's index is outside the range of the table's
     *             model.
     */
    public void setNumericColumnSorter(int cols[]) {
        if (this.tableView == null || this.tableView.getRowSorter() == null) {
            return;
        }

        DefaultRowSorter sorter = (DefaultRowSorter) this.getTableView()
                .getRowSorter();
        int colCount = cols.length;
        try {
            for (int ndx = 0; ndx < colCount; ndx++) {
                sorter.setComparator(cols[ndx], new NumericStringComparator());
            }
        } catch (Exception e) {
            String msg = "Error occurred attempting to setup numeric String column comparator for table grid row sorter";
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    public void setDateColumnSorter(int cols[]) {
        if (this.tableView == null || this.tableView.getRowSorter() == null) {
            return;
        }

        DefaultRowSorter sorter = (DefaultRowSorter) this.getTableView()
                .getRowSorter();
        int colCount = cols.length;
        try {
            for (int ndx = 0; ndx < colCount; ndx++) {
                sorter.setComparator(cols[ndx], new DateStringComparator());
            }
        } catch (Exception e) {
            String msg = "Error occurred attempting to setup Date String column comparator for table grid row sorter";
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * Return the grid's selected row.
     * 
     * @return int
     */
    public int getSelectedRow() {
        return this.tableView.getSelectedRow();
    }

    /**
     * Obtain the model object that corresponds to the currently selected row.
     * 
     * @return an arbitrary object representing the data of the selected row.
     */
    public Object getSelectedItem() {
        int viewRow = this.tableView.getSelectedRow();
        return this.tableView.getSelectedRowData(viewRow);
    }

    /**
     * Obtain one or more model objects corresponding to one or more selected
     * rows.
     * 
     * @return a List of arbitrary model objects as a result of one or more
     *         selected rows.
     */
    public List getSelectedItems() {
        return this.tableView.getAllSelectedRowData();
    }

    /**
     * Adds an instance of {@link ComponentSelectionListener} interface to the
     * List of Listeners.
     * 
     * @param listener
     *            an instance of a class that has implemented the
     *            {@link ComponentSelectionListener} interface.
     */
    public void addItemSelectionListener(ComponentSelectionListener listener) {
        if (this.listenerList == null) {
            this.listenerList = new EventListenerList();
        }
        this.listenerList.add(ComponentSelectionListener.class, listener);
    }

    /**
     * Removes a listener from the list of {@link ComponentSelectionListener}
     * implementations.
     * 
     * @param listener
     *            an instance of {@link ComponentSelectionListener} interface
     *            targeted for removal.
     */
    public void removeItemSelectionListener(ComponentSelectionListener listener) {
        this.listenerList.remove(ComponentSelectionListener.class, listener);
    }

    /**
     * An extension of MouseAdapter to capture and manage mouse related events
     * regarding the JTable view.
     * 
     * @author rterrell
     *
     */
    public class TableViewMouseAdapter extends MouseAdapter {
        private ScrollableDataGrid parent;

        /**
         * Create a TableViewMouseAdapter with a know parent object.
         * 
         * @param parent
         */
        private TableViewMouseAdapter(ScrollableDataGrid parent) {
            this.parent = parent;
        }

        /**
         * Responds to the user's interactiion with the DataGrid's rows via
         * mouse clicks.
         * <p>
         * The corresponding model object to the selected row is obtained and is
         * used as a property of the
         * {@link com.ui.event.ComponentSelectionEvent} which is later
         * dispatched.
         * 
         * @param e
         *            an instance of {@link MouseEvent}
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            // Get model object behind selected row.
            if (e.getClickCount() == 1) {
                parent.selection = parent.getSelectedItem();
            }
            // Invoke the proper event handler for when the user double
            // clicks a row by firing the ComponentItemDoubleClickedEvent event.
            if (e.getClickCount() == 2) {
                // Prepare to fire custom event
                ComponentItemDoubleClickedEvent evt = new ComponentItemDoubleClickedEvent(
                        parent.tableView, parent.selection,
                        ScrollableDataGrid.EVENT_ID_DOUBLECLICKED);
                parent.evtDispatcher.fireEvent(evt);
            }
        }
    } // end MainMenuMouseAdapter

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event
     * .ListSelectionEvent)
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        // This line of code will prevent this handler from being called twice.
        // Basically, this
        // event is called when the mouse button is pressed down and triggered
        // again when the
        // mouse button is released. Now, when navigating the rows of the JTable
        // using the up/down
        // arrow keys, this handler is only triggered once.
        if (e.getValueIsAdjusting()) {
            return;
        }
        Object rowItem = this.getSelectedItem();
        ComponentItemSelectedEvent evt = new ComponentItemSelectedEvent(
                this.tableView, rowItem,
                ScrollableDataGrid.EVENT_ID_SELECTIONCHANGED);
        this.evtDispatcher.fireEvent(evt);
    }

    /**
     * Adds a row the grid's data model.
     * <p>
     * The row added to the data model is also highlighted.
     * 
     * @param data
     *            an arbitrary object representing the row to add.
     * @return 1 for success and -1 for failure.
     */
    public int addGridRow(Object data) {
        DynamicListTableModel model = (DynamicListTableModel) this.tableView
                .getModel();
        int row = model.addRow(data);
        this.scrollToVisible(row - 1);
        return row;
    }

    /**
     * Removes the row specified as <i>index</i> from the grid.
     * 
     * @param index
     *            the index of the model row
     * @return 1 for success and -1 for failure.
     */
    public int removeGridRow(int index) {
        DynamicListTableModel model = (DynamicListTableModel) this.tableView
                .getModel();
        int viewIndex = this.tableView.convertRowIndexToModel(index);
        int rc = model.removeRow(viewIndex);
        AbstractTableModel model2 = (AbstractTableModel) this.tableView
                .getModel();
        model2.fireTableRowsDeleted(0, 0);
        return rc;
    }

    /**
     * Refreshes the Grid with the latest changes.
     * 
     */
    public void refreshGrid() {
        AbstractTableModel model = (AbstractTableModel) this.tableView
                .getModel();
        try {
            model.fireTableDataChanged();
        } catch (Throwable e) {
            this.tableView.revalidate();
            this.tableView.repaint();
        }
    }

    /**
     * Scrolls the JTable to the specified row.
     * 
     * @param rowIndex
     */
    public void scrollToVisible(int rowIndex) {
        // rowIndex = rowIndex - 1;
        this.tableView.getSelectionModel().setSelectionInterval(rowIndex,
                rowIndex);
        Rectangle rect = new Rectangle(this.tableView.getCellRect(rowIndex, 0,
                true));
        this.tableView.scrollRectToVisible(rect);
    }

    /**
     * @return the tableView
     */
    public DataGrid getTableView() {
        return tableView;
    }

}

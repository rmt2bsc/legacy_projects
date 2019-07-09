package testcases.gui;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import modules.model.Assoc;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.nv.db.IbaistEnvConfig;
import com.ui.table.ColumnDefinition;
import com.ui.table.ColumnModelBuilder;
import com.ui.table.DataGrid;
import com.ui.table.DynamicListTableModelImpl;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Test the Neiman Marcus custom JTable, Table Model, and Table Column Model
 * 
 * @author rterrell
 *
 */
public class TableFwTest extends JPanel implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private DataGrid table;

    private List<Assoc> data;

    public TableFwTest() {
	super(new GridLayout(2, 0));

	// Get data from DB and build column def list
	data = this.getData();

	// Build Table model and Table Column Model
	ColumnModelBuilder colBuilder = new ColumnModelBuilder();
	colBuilder.addCol(new ColumnDefinition("id", "Assoc. Id", 0));
	colBuilder.addCol(new ColumnDefinition("lastName", "Last Name", 1));
	colBuilder.addCol(new ColumnDefinition("firstName", "First Name", 2));
	colBuilder.addCol(new ColumnDefinition("midInit", "Middle Init", 3));
	colBuilder.addCol(new ColumnDefinition("securityLevel", "Security Level", 4));

	// Create JTable instance
	List<ColumnDefinition> colDefs = colBuilder.getDefList();
	TableColumnModel colModel = colBuilder.getModel();
	ListSelectionModel selModel = new DefaultListSelectionModel();
	selModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	MyTableModel model = new MyTableModel(data, colDefs);
	table = new DataGrid(model, colModel, selModel);

	table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	table.setFillsViewportHeight(true);

	//Create the scroll pane and add the table to it.
	JScrollPane scrollPane = new JScrollPane(table);

	//Add the scroll pane to this panel.
	add(scrollPane);

	JButton cbFetch = new JButton("Get Row");
	cbFetch.setActionCommand("fetch");
	cbFetch.addActionListener(this);
	add(cbFetch);
    }

    class MyTableModel extends DynamicListTableModelImpl {
	private static final long serialVersionUID = 1L;

	private MyTableModel(List data, List<ColumnDefinition> cols) {
	    super(data, cols);
	}
    }

    public void actionPerformed(ActionEvent e) {
	if (this.table.getSelectionModel().getSelectionMode() == ListSelectionModel.SINGLE_SELECTION) {
	    int rowFromView = this.table.getSelectedRow();
	    Assoc item = (Assoc) this.table.getSelectedRowData(rowFromView);
	    System.out.println(item.getFirstName() + "  " + item.getLastName());
	}

	if (this.table.getSelectionModel().getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) {
	    List<Object> items = this.table.getAllSelectedRowData();
	    System.out.println("Total List count: " + items.size());
	}
    }

    private List<Assoc> getData() {
	try {
	    SqlMapClient client = IbaistEnvConfig.getSqlMap();
	    List<Assoc> list = null;
	    try {
		list = client.queryForList("UserAdmin.list");
		return list;
	    }
	    catch (SQLException e) {
		throw new RuntimeException(e);
	    }
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
	//Create and set up the window.
	JFrame frame = new JFrame("TableDemo");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//Create and set up the content pane.
	TableFwTest newContentPane = new TableFwTest();
	newContentPane.setOpaque(true); //content panes must be opaque
	frame.setContentPane(newContentPane);

	//Display the window.
	frame.pack();
	frame.setVisible(true);
    }

    public static void main(String[] args) {
	//Schedule a job for the event-dispatching thread:
	//creating and showing this application's GUI.
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		createAndShowGUI();
	    }
	});
    }
}

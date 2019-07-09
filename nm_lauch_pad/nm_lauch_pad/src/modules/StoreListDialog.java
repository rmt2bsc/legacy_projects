package modules;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.nv.util.AppUtil;
import com.ui.event.ComponentItemDoubleClickedEvent;
import com.ui.event.ComponentItemSelectedEvent;
import com.ui.event.ComponentSelectionListener;
import com.ui.table.ColumnDefinition;
import com.ui.table.ScrollableDataGrid;

/**
 * Dialog for displaying a list of stores and their servers for the purpose of
 * allowing the user to select a server to establish a connection.
 * 
 * @author rterrell
 *
 */
public class StoreListDialog extends JDialog implements ActionListener,
        ComponentSelectionListener {

    private static final long serialVersionUID = 7180865667402261284L;

    private final JPanel contentPanel = new JPanel();

    private StoreListItem selection;

    private ScrollableDataGrid grid;

    private JLabel msgLabel;

    /**
     * Create the StoreListDialog with a window title and a list of objects
     * where each element comprises the store number and server name.
     */
    public StoreListDialog(String title, List<StoreListItem> list) {
        this.setTitle(title);
        setBounds(200, 200, 450, 330);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Setup list
        this.grid = this.setupDataGrid(list);

        // Add the scroll pane to this panel.
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        this.msgLabel = new JLabel("Message Area");
        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        msgLabel.setFont(f);
        msgLabel.setForeground(Color.RED);
        msgLabel.setVisible(false);
        listPanel.add(msgLabel, BorderLayout.SOUTH);

        listPanel.add(this.grid, BorderLayout.CENTER);
        getContentPane().add(listPanel, BorderLayout.CENTER);

        // Setup buttons
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton connectButton = new JButton("Connect");
        connectButton.setActionCommand("Connect");
        connectButton.addActionListener(this);
        buttonPane.add(connectButton);
        getRootPane().setDefaultButton(connectButton);

        setIconImage(AppUtil.getAppIcon().getImage());
        // this.grid.getTableView().setAutoResizeMode(DataGrid.AUTO_RESIZE_ALL_COLUMNS);
    }

    /**
     * Creates the structure of the ScrollableDataGrid based on the List of data
     * items representing the grid data tobe displayed.
     * 
     * @param list
     *            the data to be displayed.
     * @return instance of {@link ScrollableDataGrid}
     */
    protected ScrollableDataGrid setupDataGrid(List<StoreListItem> list) {
        // Setup column definitions
        List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
        colDefs.add(new ColumnDefinition("server", "Server", 0));
        colDefs.add(new ColumnDefinition("storeNo", "Store", 1));

        // Set the size of the data grid component
        Dimension size = new Dimension(400, 175);
        // Create ScrollableDataGrid grid component
        ScrollableDataGrid dg = new ScrollableDataGrid(list, colDefs,
                ListSelectionModel.SINGLE_SELECTION, size);
        dg.getTableView().setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        dg.getTableView().getTableHeader()
                .setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        dg.addItemSelectionListener(this);

        return dg;
    }

    /**
     * Handler for clicking the Connect button.
     * <p>
     * The user is required to make a selection. If a selection is not made an
     * error message is displayed and the dialog will continue to display.
     * Otherwise, the dialog will close.
     */
    public void actionPerformed(ActionEvent e) {
        this.selection = (StoreListItem) this.grid.getSelectedItem();
        if (this.selection == null) {
            String msg = "A store is required to be selected";
            this.msgLabel.setText(msg);
            this.msgLabel.setVisible(true);
        }
        else {
            setVisible(false);
            dispose();
        }
    }

    /**
     * Return the store/server selection.
     * 
     * @return the selection
     */
    public StoreListItem getSelection() {
        return selection;
    }

    /**
     * This is a stub method
     */
    @Override
    public void handleDoubleClickedRow(ComponentItemDoubleClickedEvent evt) {
        return;
    }

    /**
     * This is a stub method.
     */
    @Override
    public void handleSelectionChanged(ComponentItemSelectedEvent evt) {
        return;
    }
}

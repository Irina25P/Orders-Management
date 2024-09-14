package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 * Represents the GUI view for displaying bill logs in a table format.
 * This class is implemented as a singleton to ensure that only one instance of the bill view is active at any time.
 * It provides a table to display bill details such as order ID, client ID, amount, and date/time,
 * along with a back button to navigate to the order view.
 */
public class BillView extends JFrame {
    private static BillView instance;
    private JTable billTable;
    private JButton btnBack = new JButton("Back to Orders");
    /**
     * Private constructor for initializing the BillView frame.
     * Sets up the table for displaying bills, including defining table columns and layout. It also initializes
     * navigation controls for returning to the order view.
     */
    public BillView() {
        setTitle("Bill Log");
        billTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(billTable);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Order ID");
        model.addColumn("Client ID");
        model.addColumn("Amount");
        model.addColumn("Date/Time");
        billTable.setModel(model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnBack, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> {
            this.setVisible(false);
            OrderView.getInstance().setVisible(true);
        });

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 400);
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }
    /**
     * Provides a global point of access to the BillView instance.
     * Ensures that only one instance of BillView is created and used throughout the application.
     * @return the single instance of BillView
     */
    public static BillView getInstance() {
        if (instance == null) {
            instance = new BillView();
        }
        return instance;
    }
    /**
     * Retrieves the table model for the bill table. This model is used to add, remove, or update bill entries.
     * @return the DefaultTableModel of the bill table
     */
    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) billTable.getModel();
    }
}

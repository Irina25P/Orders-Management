package presentation;

import model.Product;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Provides the graphical user interface for order creation and bill viewing.
 * This class supports operations like selecting clients and products, setting order quantities,
 * and navigating to the bill log. It is designed as a singleton to ensure only one instance
 * of the order view is used throughout the application.
 */
public class OrderView extends JFrame {
    private static OrderView instance;
    private JButton btnCreateOrder = new JButton("Create Order");
    private JButton btnViewBills = new JButton("View Bills");
    private JComboBox<String> comboClients = new JComboBox<>();
    private JComboBox<String> comboProducts = new JComboBox<>();
    private JTextField txtQuantity = new JTextField(20);
    private JLabel lblStatus = new JLabel();
    private JButton btnBack = new JButton("Back to Main");
    /**
     * Private constructor that initializes the OrderView.
     * Sets up the user interface including layout, components, and event listeners.
     */
    public OrderView() {
        setTitle("Create Order");
        JPanel panel = new JPanel(new GridLayout(5, 2));  // Adjusted for an additional row
        panel.add(new JLabel("Select Client:"));
        panel.add(comboClients);
        panel.add(new JLabel("Select Product:"));
        panel.add(comboProducts);
        panel.add(new JLabel("Quantity:"));
        panel.add(txtQuantity);
        panel.add(btnCreateOrder);
        panel.add(lblStatus);
        panel.add(btnViewBills);
        panel.add(btnBack);

        btnBack.addActionListener(e -> {
            this.setVisible(false);
            MainView.getInstance().setVisible(true);  // Assuming MainView is a singleton
        });

        btnViewBills.addActionListener(e -> {
            BillView.getInstance().setVisible(true);
            new BillController(BillView.getInstance()).fillBillData(); // Refresh bill data on view
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300); // Adjusted to fit the new button
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }
    /**
     * Provides global access to the singleton instance of OrderView.
     * If no instance exists, a new one is created.
     * @return The single instance of OrderView.
     */
    public static OrderView getInstance() {
        if (instance == null) {
            instance = new OrderView();
        }
        return instance;
    }
    /**
     * Refreshes the client and product dropdown menus with the latest data.
     * @param clients List of clients to populate the client dropdown.
     * @param products List of products to populate the product dropdown.
     */
    public void refreshData(List<Client> clients, List<Product> products) {
        comboClients.removeAllItems();
        for (Client client : clients) {
            comboClients.addItem(client.toString());
        }

        comboProducts.removeAllItems();
        for (Product product : products) {
            comboProducts.addItem(product.toString());
        }
    }
    // Getter methods for the UI components.
    public JButton getBtnCreateOrder() {
        return btnCreateOrder;
    }
    public JComboBox<String> getComboClients() {
        return comboClients;
    }
    public JComboBox<String> getComboProducts() {
        return comboProducts;
    }
    public JTextField getTxtQuantity() {
        return txtQuantity;
    }
    public JLabel getLblStatus() {
        return lblStatus;
    }
    public JButton getBtnViewBills() {
        return btnViewBills;
    }

}
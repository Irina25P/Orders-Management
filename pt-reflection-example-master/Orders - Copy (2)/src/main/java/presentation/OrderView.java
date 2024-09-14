package presentation;

import model.Product;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class OrderView extends JFrame {
    private static OrderView instance;
    private JButton btnCreateOrder = new JButton("Create Order");
    private JButton btnViewBills = new JButton("View Bills");
    private JComboBox<String> comboClients = new JComboBox<>();
    private JComboBox<String> comboProducts = new JComboBox<>();
    private JTextField txtQuantity = new JTextField(20);
    private JLabel lblStatus = new JLabel();
    private JButton btnBack = new JButton("Back to Main");


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
    public static OrderView getInstance() {
        if (instance == null) {
            instance = new OrderView();
        }
        return instance;
    }
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
    public JButton getBtnViewBills() {
        return btnViewBills;
    }

}
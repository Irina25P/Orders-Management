package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OrderView extends JFrame {
    private JButton btnCreateOrder = new JButton("Create Order");
    private JComboBox<String> comboClients = new JComboBox<>();
    private JComboBox<String> comboProducts = new JComboBox<>();
    private JTextField txtQuantity = new JTextField(20);
    private JLabel lblStatus = new JLabel();

    public OrderView() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Select Client:"));
        panel.add(comboClients);
        panel.add(new JLabel("Select Product:"));
        panel.add(comboProducts);
        panel.add(new JLabel("Quantity:"));
        panel.add(txtQuantity);
        panel.add(btnCreateOrder);
        panel.add(lblStatus);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
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
}


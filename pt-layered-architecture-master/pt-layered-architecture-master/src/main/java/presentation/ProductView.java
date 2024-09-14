package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProductView extends JFrame {
    private JButton btnAdd = new JButton("Add Product");
    private JButton btnEdit = new JButton("Edit Product");
    private JButton btnDelete = new JButton("Delete Product");
    private JTextField txtName = new JTextField(20);
    private JTextField txtPrice = new JTextField(20);
    private JTextField txtStock = new JTextField(20);
    private JTable tableProducts = new JTable();

    public ProductView() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        panel.add(txtName);
        panel.add(new JLabel("Price:"));
        panel.add(txtPrice);
        panel.add(new JLabel("Stock:"));
        panel.add(txtStock);
        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.NORTH);
        this.add(new JScrollPane(tableProducts), BorderLayout.CENTER);
        this.setVisible(true);
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public JTextField getTxtPrice() {
        return txtPrice;
    }

    public JTextField getTxtStock() {
        return txtStock;
    }

    public JTable getTableProducts() {
        return tableProducts;
    }
}

package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductView extends JFrame {
    private ProductController controller;
    private static ProductView instance;
    private JButton btnAdd = new JButton("Add Product");
    private JButton btnEdit = new JButton("Edit Product");
    private JButton btnDelete = new JButton("Delete Product");
    private JButton btnFindById = new JButton("Find By ID");
    private JTextField txtName = new JTextField(20);
    private JTextField txtPrice = new JTextField(20);
    private JTextField txtStock = new JTextField(20);
    private JTable tableProducts = new JTable();
    private JButton btnBack = new JButton("Back to Main");
    private ProductBLL productBLL;

    private DefaultTableModel tableModel;

    private ProductView(ProductBLL productBLL) {
        this.productBLL = productBLL;
        initializeUI();
    }

    public void setController(ProductController controller) {
        this.controller = controller;
    }

    private void initializeUI() {
        setTitle("Manage Clients");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Price", "Stock"});
        tableProducts = new JTable(tableModel);
        add(new JScrollPane(tableProducts), BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new GridLayout(1, 4));
        topPanel.add(btnAdd);
        topPanel.add(btnEdit);
        topPanel.add(btnDelete);
        topPanel.add(btnFindById);
        topPanel.add(btnBack);

        add(topPanel, BorderLayout.NORTH);

        btnBack.addActionListener(e -> {
            setVisible(false);
            MainView.getInstance().setVisible(true);
        });

        // Action Listeners for buttons
        btnAdd.addActionListener(e -> addProduct());
        btnEdit.addActionListener(e -> editProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnFindById.addActionListener(e -> findProductById());
    }
    private void addProduct() {
        JTextField nameField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField stockField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Stock:"));
        panel.add(stockField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Product",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                BigDecimal price = new BigDecimal(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                if (price.compareTo(BigDecimal.ZERO) <= 0 || stock < 0) {
                    throw new IllegalArgumentException("Price must be greater than zero and stock must be non-negative.");
                }
                Product product = new Product(nameField.getText(), price, stock);
                productBLL.insertProduct(product);
                refreshTable(productBLL.getAllProducts()); // Refresh the table with updated data
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format for price or stock", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editProduct() {
        int selectedRow = tableProducts.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String name = tableModel.getValueAt(selectedRow, 1).toString();
            BigDecimal price = new BigDecimal(tableModel.getValueAt(selectedRow, 2).toString());
            int stock = Integer.parseInt(tableModel.getValueAt(selectedRow, 3).toString());
            JTextField nameField = new JTextField(name);
            JTextField priceField = new JTextField(String.valueOf(price));
            JTextField stockField = new JTextField(String.valueOf(stock));

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);
            panel.add(new JLabel("Stock:"));
            panel.add(stockField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Edit Product",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    price = new BigDecimal(priceField.getText());
                    stock = Integer.parseInt(stockField.getText());
                    if (price.compareTo(BigDecimal.ZERO) <= 0 || stock < 0) {
                        throw new IllegalArgumentException("Price must be greater than zero and stock must be non-negative.");
                    }
                    Product product = new Product(nameField.getText(), price, stock);
                    productBLL.insertProduct(product);
                    refreshTable(productBLL.getAllProducts()); // Refresh the table with updated data
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid number format for price or stock", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a product to edit.");
        }
    }

    private void deleteProduct() {
        int selectedRow = tableProducts.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this product?", "Delete Product",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new ProductBLL().deleteProduct(id);
                refreshTable(new ProductBLL().getAllProducts()); // Refresh the table after deletion
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a product to delete.");
        }
    }

    public void findProductById() {
        String idString = JOptionPane.showInputDialog(this, "Enter Product ID:");
        if (idString != null && !idString.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idString);
                Optional<Product> optionalProduct = productBLL.findProductById(id);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    JOptionPane.showMessageDialog(this, "Product Details: \n" + product.toString(), "Product Found", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No product found with ID: " + id, "Not Found", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID format", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error searching for client: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static ProductView getInstance(ProductBLL productBLL) {
        if (instance == null) {
            instance = new ProductView(productBLL);
        }
        return instance;
    }

    public void refreshTable(List<Product> products) {
        tableModel.setRowCount(0); // Clear the table
        for (Product product : products) {
            tableModel.addRow(new Object[]{product.getId(), product.getName(), product.getPrice(), product.getStock()});
        }
    }

    public JButton getBtnFindById() {
        return btnFindById;
    }

    public JButton getBtnBack() {
        return btnBack;
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

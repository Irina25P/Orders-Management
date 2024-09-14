package presentation;

import javax.swing.*;

import model.Client;
import presentation.ProductView;
import bll.ProductBLL;
import model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
/**
 * Controls the interactions between the product view and the business logic layer.
 * It manages the creation, modification, deletion, and querying of product data through the GUI.
 */
public class ProductController {
    private ProductView view;
    private ProductBLL bll;
    /**
     * Constructs a ProductController with a given view and business logic layer handler.
     * @param view The GUI component this controller manipulates.
     * @param bll The business logic layer for handling product operations.
     */
    public ProductController(ProductView view, ProductBLL bll) {
        this.view = view;
        this.bll = bll;
        initView();
    }
    /**
     * Initializes the view by setting up action listeners on buttons and refreshing the table.
     */
    private void initView() {
        view.getBtnAdd().addActionListener(e -> addProduct());
        view.getBtnEdit().addActionListener(e -> editProduct());
        view.getBtnDelete().addActionListener(e -> deleteProduct());
        view.getBtnFindById().addActionListener(e -> findProductById());
        refreshTable();
    }
    /**
     * Adds a new product using the data entered in the view's text fields.
     */
    private void addProduct() {
        String name = view.getTxtName().getText();
        BigDecimal price = new BigDecimal(view.getTxtPrice().getText());
        int stock = Integer.parseInt(view.getTxtStock().getText());
        Product product = new Product(name, price, stock);
        try {
            bll.updateProduct(product);
            refreshTable();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, "Validation error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error updating client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Edits an existing product selected in the table using the updated data from the view's text fields.
     */
    private void editProduct() {
        int selectedRow = view.getTableProducts().getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(view.getTableProducts().getValueAt(selectedRow, 0).toString());
            String name = view.getTxtName().getText();
            BigDecimal price = new BigDecimal(view.getTxtPrice().getText());
            int stock = Integer.parseInt(view.getTxtStock().getText());
            Product product = new Product(id, name, price, stock);
            try {
                bll.updateProduct(product);
                refreshTable();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, "Validation error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error updating client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a row to edit.");
        }
    }
    /**
     * Deletes an existing product selected in the table.
     */
    private void deleteProduct() {
        int selectedRow = view.getTableProducts().getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(view.getTableProducts().getValueAt(selectedRow, 0).toString());
            bll.deleteProduct(id);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Select a row to delete.");
        }
    }
    /**
     * Finds a product by ID, displaying product details if found.
     */
    public void findProductById() {
        String idString = JOptionPane.showInputDialog(view, "Enter Product ID:");
        if (idString != null && !idString.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idString);
                Optional<Product> optionalProduct = bll.findProductById(id);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    JOptionPane.showMessageDialog(view, "Product Details: \n" + product.toString(), "Product Found", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "No product found with ID: " + id, "Not Found", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Invalid ID format", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error searching for client: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Refreshes the product table with current data from the business layer.
     */
    public void refreshTable() {
        List<Product> products = bll.getAllProducts();
        TableModelUtils.populateTableWithReflection(view.getTableProducts(), products);
    }
}


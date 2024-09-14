package presentation;

import javax.swing.*;

import model.Client;
import presentation.ProductView;
import bll.ProductBLL;
import model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductController {
    private ProductView view;
    private ProductBLL bll;

    public ProductController(ProductView view, ProductBLL bll) {
        this.view = view;
        this.bll = bll;
        initView();
    }

    private void initView() {
        view.getBtnAdd().addActionListener(e -> addProduct());
        view.getBtnEdit().addActionListener(e -> editProduct());
        view.getBtnDelete().addActionListener(e -> deleteProduct());
        view.getBtnFindById().addActionListener(e -> findProductById());
        refreshTable();
    }

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

    public void refreshTable() {
        List<Product> products = bll.getAllProducts();
        TableModelUtils.populateTableWithReflection(view.getTableProducts(), products);
    }
}


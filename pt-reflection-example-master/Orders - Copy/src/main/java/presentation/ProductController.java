package presentation;

import javax.swing.*;

import model.Client;
import presentation.ProductView;
import bll.ProductBLL;
import model.Product;

import java.util.List;

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
        refreshTable();
    }

    private void addProduct() {
        String name = view.getTxtName().getText();
        double price = Double.parseDouble(view.getTxtPrice().getText());
        int stock = Integer.parseInt(view.getTxtStock().getText());
        Product product = new Product(name, price, stock);
        bll.insertProduct(product);
        refreshTable();
    }

    private void editProduct() {
        int selectedRow = view.getTableProducts().getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(view.getTableProducts().getValueAt(selectedRow, 0).toString());
            String name = view.getTxtName().getText();
            double price = Double.parseDouble(view.getTxtPrice().getText());
            int stock = Integer.parseInt(view.getTxtStock().getText());
            Product product = new Product(id, name, price, stock);
            bll.updateProduct(product);
            refreshTable();
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

    public void refreshTable() {
        List<Product> products = bll.getAllProducts();
        TableModelUtils.populateTableWithReflection(view.getTableProducts(), products);
    }
}


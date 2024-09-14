package presentation;

import javax.swing.*;

import bll.BillBLL;
import model.Bill;
import bll.OrderBLL;
import bll.ProductBLL;
import bll.ClientBLL;
import model.Order;
import model.Product;
import model.Client;

import java.time.LocalDateTime;
import java.util.List;

public class OrderController {
    private OrderView view;
    private OrderBLL orderBll;
    private ProductBLL productBll;
    private ClientBLL clientBll;
    private BillBLL billBLL;

    public OrderController(OrderView view, OrderBLL orderBll, ProductBLL productBll, ClientBLL clientBll, BillBLL billBLL) {
        this.view = view;
        this.orderBll = orderBll;
        this.productBll = productBll;
        this.clientBll = clientBll;
        this.billBLL = billBLL;
        initView();
    }

    private void initView() {
        view.getBtnCreateOrder().addActionListener(e -> createOrder());
        view.getBtnViewBills().addActionListener(e -> viewBillLog());
        populateClients();
        populateProducts();
    }
    public void showView() {
        refreshData();
        view.setVisible(true);
    }

    private void refreshData() {
        List<Client> clients = clientBll.getAllClients();
        List<Product> products = productBll.getAllProducts();
        view.refreshData(clients, products);
    }

    private void populateClients() {
        for (Client client : clientBll.getAllClients()) {
            view.getComboClients().addItem(client.toString()); // Assumes Client.toString() is overridden suitably
        }
    }

    private void populateProducts() {
        for (Product product : productBll.getAllProducts()) {
            view.getComboProducts().addItem(product.toString()); // Assumes Product.toString() is overridden suitably
        }
    }

    private void createOrder() {
        int clientIndex = view.getComboClients().getSelectedIndex();
        int productIndex = view.getComboProducts().getSelectedIndex();
        int quantity;
        try {
            quantity = Integer.parseInt(view.getTxtQuantity().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Enter a valid quantity.");
            return;
        }
        if (clientIndex != -1 && productIndex != -1 && quantity > 0) {
            Client client = clientBll.getAllClients().get(clientIndex);
            Product product = productBll.getAllProducts().get(productIndex);
            if (product.getStock() >= quantity) {
                Order order = new Order(0, client.getId(), product.getId(), quantity);
                int orderId = orderBll.insertOrder(order);
                if (orderId > 0) {
                    Bill bill = new Bill(orderId, client.getId(), product.getPrice() * quantity, LocalDateTime.now());
                    billBLL.insertBill(bill);
                    JOptionPane.showMessageDialog(view, "Order and Bill created successfully.");
                    view.getLblStatus().setText("Order and Bill created successfully.");
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to create order.");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Insufficient stock.");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Select a client and a product and enter a valid quantity.");
        }
    }
    private void viewBillLog() {
        BillView.getInstance().setVisible(true);
    }
}
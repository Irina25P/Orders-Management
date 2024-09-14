package presentation;

import javax.swing.*;
import presentation.OrderView;
import bll.OrderBLL;
import bll.ProductBLL;
import bll.ClientBLL;
import model.Order;
import model.Product;
import model.Client;

public class OrderController {
    private OrderView view;
    private OrderBLL orderBll;
    private ProductBLL productBll;
    private ClientBLL clientBll;

    public OrderController(OrderView view, OrderBLL orderBll, ProductBLL productBll, ClientBLL clientBll) {
        this.view = view;
        this.orderBll = orderBll;
        this.productBll = productBll;
        this.clientBll = clientBll;
        initView();
    }

    private void initView() {
        view.getBtnCreateOrder().addActionListener(e -> createOrder());
        populateClients();
        populateProducts();
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
        int quantity = Integer.parseInt(view.getTxtQuantity().getText());

        Client client = clientBll.getAllClients().get(clientIndex);
        Product product = productBll.getAllProducts().get(productIndex);

        if (product.getStock() >= quantity) {
            Order order = new Order(0, client.getId(), product.getId(), quantity);
            orderBll.insertOrder(order);
            product.setStock(product.getStock() - quantity);
            productBll.updateProduct(product);
            JOptionPane.showMessageDialog(null, "Order created successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient stock available.");
        }
    }
}

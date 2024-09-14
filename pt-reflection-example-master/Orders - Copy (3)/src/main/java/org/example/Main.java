package org.example;
import java.util.logging.Logger;

import bll.BillBLL;
import bll.ClientBLL;
import bll.ProductBLL;
import bll.OrderBLL;
import presentation.*;

public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());
//    public static void main(String[] args) {
//        try {
//            // Testing Client Functionality
//            ClientBLL clientBll = new ClientBLL();
//            Client client = new Client("John Doe", "1234 Street", "john@example.com");
//            int clientId = clientBll.insertClient(client);
//            System.out.println("Inserted Client ID: " + clientId);
//
//            Client retrievedClient = clientBll.findClientById(clientId);
//            System.out.println("Retrieved Client: " + retrievedClient);
//
//            // Testing Product Functionality
//            ProductBLL productBll = new ProductBLL();
//            Product product = new Product("Laptop", 999.99, 10);
//            int productId = productBll.insertProduct(product);
//            System.out.println("Inserted Product ID: " + productId);
//
//            Product retrievedProduct = productBll.findProductById(productId);
//            System.out.println("Retrieved Product: " + retrievedProduct);
//
//            // Testing Order Functionality
//            OrderBLL orderBll = new OrderBLL();
//            Order order = new Order(0, clientId, productId, 2);
//            int orderId = orderBll.insertOrder(order);
//            System.out.println("Inserted Order ID: " + orderId);
//
//            Order retrievedOrder = orderBll.findOrderById(orderId);
//            System.out.println("Retrieved Order: " + retrievedOrder);
//
//            // Update and List Tests
//            retrievedClient.setEmail("newemail@example.com");
//            clientBll.updateClient(retrievedClient);
//            System.out.println("Updated Client: " + clientBll.findClientById(clientId));
//
//            List<Client> allClients = clientBll.getAllClients();
//            System.out.println("All Clients: " + allClients);
//
//            List<Product> allProducts = productBll.getAllProducts();
//            System.out.println("All Products: " + allProducts);
//
//            List<Order> allOrders = orderBll.getAllOrders();
//            System.out.println("All Orders: " + allOrders);
//
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, "Error", ex);
//        }
//    }
public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
        MainView.getInstance().setVisible(true);
        setupListeners();
    });
}

    private static void setupListeners() {
        MainView mainView = MainView.getInstance();

        mainView.getBtnClient().addActionListener(e -> {
            ClientBLL clientBLL = new ClientBLL();
            ClientView clientView = ClientView.getInstance(clientBLL);
            clientView.refreshTable(new ClientBLL().getAllClients()); // Refresh the table with updated data
            mainView.setVisible(false);
            clientView.setVisible(true);
        });

        mainView.getBtnProduct().addActionListener(e -> {
            ProductView productView = ProductView.getInstance();
            productView.refreshTable(new ProductBLL().getAllProducts()); // Refresh the table with updated data
            mainView.setVisible(false);
            productView.setVisible(true);
        });

        mainView.getBtnOrder().addActionListener(e -> {
            OrderController orderController = new OrderController(OrderView.getInstance(), new OrderBLL(), new ProductBLL(), new ClientBLL(), new BillBLL());
            orderController.showView();
            mainView.setVisible(false);
        });
    }
}
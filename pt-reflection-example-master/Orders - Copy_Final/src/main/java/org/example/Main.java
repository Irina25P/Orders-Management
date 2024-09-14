package org.example;

import java.util.logging.Logger;
import bll.BillBLL;
import bll.ClientBLL;
import bll.ProductBLL;
import bll.OrderBLL;
import presentation.*;
/**
 * The main class of the application that initializes the GUI and sets up navigation between different sections.
 * This class is responsible for launching the application and configuring the interactions between the main view
 * and other components of the application such as client, product, and order views.
 */
public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    /**
     * Main entry point of the application. This method sets up the main GUI to be visible and initializes
     * navigation listeners to facilitate transitions between different views.
     * @param args Command line arguments passed to the program.
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainView.getInstance().setVisible(true);
            setupListeners();
        });
    }
    /**
     * Sets up the action listeners for buttons in the MainView. Each button is configured to initialize
     * and display its corresponding view, managing the transitions and data refresh operations for
     * client, product, and order management.
     */
    private static void setupListeners() {
        MainView mainView = MainView.getInstance();

        mainView.getBtnClient().addActionListener(e -> {
            ClientBLL clientBLL = new ClientBLL();
            ClientView clientView = ClientView.getInstance(clientBLL);
            clientView.refreshTable(new ClientBLL().getAllClients()); // Refresh client list on view
            mainView.setVisible(false);
            clientView.setVisible(true);
        });

        mainView.getBtnProduct().addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            ProductView productView = ProductView.getInstance(productBLL);
            productView.refreshTable(new ProductBLL().getAllProducts()); // Refresh product list on view
            mainView.setVisible(false);
            productView.setVisible(true);
        });

        mainView.getBtnOrder().addActionListener(e -> {
            OrderController orderController = new OrderController(OrderView.getInstance(), new OrderBLL(), new ProductBLL(), new ClientBLL(), new BillBLL());
            orderController.showView(); // Show order view and load data
            mainView.setVisible(false);
        });
    }
}

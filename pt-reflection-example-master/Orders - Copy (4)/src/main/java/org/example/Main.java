package org.example;
import java.util.logging.Logger;

import bll.BillBLL;
import bll.ClientBLL;
import bll.ProductBLL;
import bll.OrderBLL;
import presentation.*;

public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

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
            clientView.refreshTable(new ClientBLL().getAllClients());
            mainView.setVisible(false);
            clientView.setVisible(true);
        });

        mainView.getBtnProduct().addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            ProductView productView = ProductView.getInstance(productBLL);
            productView.refreshTable(new ProductBLL().getAllProducts());
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
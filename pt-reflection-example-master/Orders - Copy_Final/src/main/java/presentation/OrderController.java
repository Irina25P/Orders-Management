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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Controller for handling order-related operations in the user interface.
 * This class interacts with business logic layers to manage orders, products, clients, and bills,
 * and updates the {@link OrderView} based on user actions and business logic responses.
 */
public class OrderController {
    private OrderView view;
    private OrderBLL orderBll;
    private ProductBLL productBll;
    private ClientBLL clientBll;
    private BillBLL billBLL;
    /**
     * Constructs an OrderController with dependencies for managing order operations.
     *
     * @param view The view component this controller manipulates.
     * @param orderBll The business logic layer for order operations.
     * @param productBll The business logic layer for product operations.
     * @param clientBll The business logic layer for client operations.
     * @param billBLL The business logic layer for billing operations.
     */
    public OrderController(OrderView view, OrderBLL orderBll, ProductBLL productBll, ClientBLL clientBll, BillBLL billBLL) {
        this.view = view;
        this.orderBll = orderBll;
        this.productBll = productBll;
        this.clientBll = clientBll;
        this.billBLL = billBLL;
        initView();
    }
    /**
     * Initializes the view with necessary listeners and populates UI components.
     */
    private void initView() {
        view.getBtnCreateOrder().addActionListener(e -> createOrder());
        view.getBtnViewBills().addActionListener(e -> viewBillLog());
        populateClients();
        populateProducts();
    }
    /**
     * Makes the view visible and refreshes the underlying data.
     */
    public void showView() {
        refreshData();
        view.setVisible(true);
    }
    /**
     * Refreshes client and product data from their respective BLLs and updates the view.
     */
    private void refreshData() {
        List<Client> clients = clientBll.getAllClients();
        List<Product> products = productBll.getAllProducts();
        view.refreshData(clients, products);
    }
    /**
     * Populates the client dropdown in the view based on available clients.
     */
    private void populateClients() {
        for (Client client : clientBll.getAllClients()) {
            view.getComboClients().addItem(client.toString()); // Assumes Client.toString() is overridden suitably
        }
    }
    /**
     * Populates the product dropdown in the view based on available products.
     */
    private void populateProducts() {
        for (Product product : productBll.getAllProducts()) {
            view.getComboProducts().addItem(product.toString()); // Assumes Product.toString() is overridden suitably
        }
    }
    /**
     * Handles the creation of an order and corresponding bill based on user inputs.
     * Validates product stock and client/product selections, creates order and bill records, and updates the view.
     */
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
                    Bill bill = new Bill(
                            orderId,
                            client.getId(),
                            product.getPrice().multiply(BigDecimal.valueOf(quantity)).doubleValue(), // Convert the BigDecimal result to double
                            LocalDateTime.now()
                    );

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
    /**
     * Displays the bill log view to the user.
     */
    private void viewBillLog() {
        BillView.getInstance().setVisible(true);
    }
}
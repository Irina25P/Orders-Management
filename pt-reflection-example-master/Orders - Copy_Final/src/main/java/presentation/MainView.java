package presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the main navigation window of the application.
 * This class is a singleton, ensuring that only one instance of the main navigation window exists.
 * It provides buttons for navigating to different sections of the application such as Clients, Products, and Orders.
 */
public class MainView extends JFrame {
    private static MainView instance;
    private JButton btnClient = new JButton("Manage Clients");
    private JButton btnProduct = new JButton("Manage Products");
    private JButton btnOrder = new JButton("Manage Orders");
    /**
     * Private constructor to initialize the MainView.
     * Sets up the layout and components of the main navigation window.
     */
    private MainView() {
        initialize();
    }
    /**
     * Ensures that only one instance of MainView exists throughout the application.
     * If no instance exists, a new one is created.
     * @return The single instance of MainView.
     */
    public static MainView getInstance() {
        if (instance == null) {
            instance = new MainView();
        }
        return instance;
    }
    /**
     * Initializes the user interface components of the main window.
     * Sets the window title, size, default close operation, layout, and adds navigation buttons.
     */
    private void initialize() {
        setTitle("Main Navigation");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));
        add(btnClient);
        add(btnProduct);
        add(btnOrder);
        setLocationRelativeTo(null);  // Center the window
    }
    /**
     * Retrieves the button used for navigating to the client management section.
     * @return JButton that triggers navigation to manage clients.
     */
    public JButton getBtnClient() {
        return btnClient;
    }
    /**
     * Retrieves the button used for navigating to the product management section.
     * @return JButton that triggers navigation to manage products.
     */
    public JButton getBtnProduct() {
        return btnProduct;
    }
    /**
     * Retrieves the button used for navigating to the order management section.
     * @return JButton that triggers navigation to manage orders.
     */
    public JButton getBtnOrder() {
        return btnOrder;
    }
}

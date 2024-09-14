package presentation;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private static MainView instance;

    private JButton btnClient = new JButton("Manage Clients");
    private JButton btnProduct = new JButton("Manage Products");
    private JButton btnOrder = new JButton("Manage Orders");

    private MainView() {
        initialize();
    }

    public static MainView getInstance() {
        if (instance == null) {
            instance = new MainView();
        }
        return instance;
    }

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

    public JButton getBtnClient() {
        return btnClient;
    }

    public JButton getBtnProduct() {
        return btnProduct;
    }

    public JButton getBtnOrder() {
        return btnOrder;
    }
}


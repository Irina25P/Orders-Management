package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BillView extends JFrame {
    private static BillView instance;
    private JTable billTable;
    private JButton btnBack = new JButton("Back to Orders");

    public BillView() {
        setTitle("Bill Log");
        billTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(billTable);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Order ID");
        model.addColumn("Client ID");
        model.addColumn("Amount");
        model.addColumn("Date/Time");
        billTable.setModel(model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnBack, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> {
            this.setVisible(false);
            OrderView.getInstance().setVisible(true);
        });

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 400);
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    public static BillView getInstance() {
        if (instance == null) {
            instance = new BillView();
        }
        return instance;
    }

    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) billTable.getModel();
    }
}

package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientView extends JFrame {
    private JButton btnAdd = new JButton("Add Client");
    private JButton btnEdit = new JButton("Edit Client");
    private JButton btnDelete = new JButton("Delete Client");
    private JTextField txtName = new JTextField(20);
    private JTextField txtAddress = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JTable tableClients = new JTable();

    public ClientView() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        panel.add(txtName);
        panel.add(new JLabel("Address:"));
        panel.add(txtAddress);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.NORTH);
        this.add(new JScrollPane(tableClients), BorderLayout.CENTER);
        this.setVisible(true);
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public JTextField getTxtAddress() {
        return txtAddress;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTable getTableClients() {
        return tableClients;
    }
}


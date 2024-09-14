package presentation;

import bll.ClientBLL;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ClientView extends JFrame {
    private static ClientView instance;

    private JButton btnAdd = new JButton("Add Client");
    private JButton btnEdit = new JButton("Edit Client");
    private JButton btnDelete = new JButton("Delete Client");
    private JTextField txtName = new JTextField(20);
    private JTextField txtAddress = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JTable tableClients = new JTable();
    private JButton btnBack = new JButton("Back to Main");
    private DefaultTableModel tableModel;

    private ClientView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Manage Clients");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Address", "Email"});
        tableClients = new JTable(tableModel);
        add(new JScrollPane(tableClients), BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new GridLayout(1, 4));
        topPanel.add(btnAdd);
        topPanel.add(btnEdit);
        topPanel.add(btnDelete);
        topPanel.add(btnBack);
        add(topPanel, BorderLayout.NORTH);

        btnBack.addActionListener(e -> {
            setVisible(false);
            MainView.getInstance().setVisible(true);
        });

        // Action Listeners for buttons
        btnAdd.addActionListener(e -> addClient());
        btnEdit.addActionListener(e -> editClient());
        btnDelete.addActionListener(e -> deleteClient());
    }
    private void addClient() {
        // Example dialog for adding
        JTextField nameField = new JTextField(10);
        JTextField addressField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Client",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Client newClient = new Client(nameField.getText(), addressField.getText(), emailField.getText());
            new ClientBLL().insertClient(newClient);
            refreshTable(new ClientBLL().getAllClients());
        }
    }

    private void editClient() {
        int selectedRow = tableClients.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String name = tableModel.getValueAt(selectedRow, 1).toString();
            String address = tableModel.getValueAt(selectedRow, 2).toString();
            String email = tableModel.getValueAt(selectedRow, 3).toString();

            JTextField nameField = new JTextField(name);
            JTextField addressField = new JTextField(address);
            JTextField emailField = new JTextField(email);

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Address:"));
            panel.add(addressField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Edit Client",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                Client client = new Client(id, nameField.getText(), addressField.getText(), emailField.getText());
                new ClientBLL().updateClient(client);
                refreshTable(new ClientBLL().getAllClients()); // Refresh the table with updated data
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a client to edit.");
        }
    }
    private void deleteClient() {
        int selectedRow = tableClients.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this client?", "Delete Client",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new ClientBLL().deleteClient(id);
                refreshTable(new ClientBLL().getAllClients()); // Refresh the table after deletion
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a client to delete.");
        }
    }


    public static ClientView getInstance() {
        if (instance == null) {
            instance = new ClientView();
        }
        return instance;
    }

    public void refreshTable(List<Client> clients) {
        tableModel.setRowCount(0); // Clear the table
        for (Client client : clients) {
            tableModel.addRow(new Object[]{client.getId(), client.getName(), client.getAddress(), client.getEmail()});
        }
    }

    public JButton getBtnBack() {
        return btnBack;
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


package presentation;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import presentation.ClientView;
import bll.ClientBLL;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ClientController {
    private ClientView view;
    private ClientBLL bll;

    public ClientController(ClientView view, ClientBLL bll) {
        this.view = view;
        this.bll = bll;
        initView();
    }

    private void initView() {
        view.getBtnAdd().addActionListener(e -> addClient());
        view.getBtnEdit().addActionListener(e -> editClient());
        view.getBtnDelete().addActionListener(e -> deleteClient());
        view.getBtnFindById().addActionListener(e -> findClientById());
        refreshTable();
    }

    private void addClient() {
        String name = view.getTxtName().getText();
        String address = view.getTxtAddress().getText();
        String email = view.getTxtEmail().getText();
        Client client = new Client(name, address, email);
        bll.insertClient(client);
        refreshTable();
    }

    private void editClient() {
        int selectedRow = view.getTableClients().getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(view.getTableClients().getValueAt(selectedRow, 0).toString());
            String name = view.getTxtName().getText();
            String address = view.getTxtAddress().getText();
            String email = view.getTxtEmail().getText();
            Client client = new Client(id, name, address, email);
            bll.updateClient(client);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Select a row to edit.");
        }
    }

    private void deleteClient() {
        int selectedRow = view.getTableClients().getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(view.getTableClients().getValueAt(selectedRow, 0).toString());
            bll.deleteClient(id);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Select a row to delete.");
        }
    }

    private void findClientById() {
        String idString = JOptionPane.showInputDialog(view, "Enter Client ID:");
        if (idString != null && !idString.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idString);
                System.out.println("Parsed ID: " + id); // Debugging statement

                Optional<Client> optionalClient = bll.findClientById(id);
                System.out.println("Retrieved Optional: " + optionalClient); // Debugging statement

                if (optionalClient.isPresent()) {
                    Client client = optionalClient.get();
                    JOptionPane.showMessageDialog(view, "Client Details: \n" + client.toString(), "Client Found", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "No client found with ID: " + idString, "Not Found", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Invalid ID format", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error searching for client: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }




    public void refreshTable() {
        List<Client> clients = bll.getAllClients();
        TableModelUtils.populateTableWithReflection(view.getTableClients(), clients);
    }
}

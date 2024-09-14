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
/**
 * Controller for managing client operations in the UI.
 * This class is responsible for handling user actions from the {@link ClientView} and invoking the corresponding
 * methods in {@link ClientBLL}. It manages adding, editing, deleting, and finding clients based on user interactions.
 */
public class ClientController {
    private ClientView view;
    private ClientBLL bll;
    /**
     * Constructs a ClientController with a specified view and business logic layer.
     * Initializes the view and sets up event listeners for the user interface.
     *
     * @param view The view associated with this controller.
     * @param bll The business logic layer this controller interacts with.
     */
    public ClientController(ClientView view, ClientBLL bll) {
        this.view = view;
        this.bll = bll;
        initView();
    }
    /**
     * Initializes the view by setting up action listeners on buttons and refreshing the table with client data.
     */
    private void initView() {
        view.getBtnAdd().addActionListener(e -> addClient());
        view.getBtnEdit().addActionListener(e -> editClient());
        view.getBtnDelete().addActionListener(e -> deleteClient());
        view.getBtnFindById().addActionListener(e -> findClientById());
        refreshTable();
    }
    /**
     * Adds a new client using data from the view's text fields.
     * Displays messages based on the outcome of the operation.
     */
    private void addClient() {
        String name = view.getTxtName().getText();
        String address = view.getTxtAddress().getText();
        String email = view.getTxtEmail().getText();
        Client client = new Client(name, address, email);
        try {
            bll.insertClient(client);
            refreshTable();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, "Validation error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error adding client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Edits an existing client selected in the table using data from the view's text fields.
     * Displays messages based on the outcome of the operation.
     */
    private void editClient() {
        int selectedRow = view.getTableClients().getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(view.getTableClients().getValueAt(selectedRow, 0).toString());
            String name = view.getTxtName().getText();
            String address = view.getTxtAddress().getText();
            String email = view.getTxtEmail().getText();
            Client client = new Client(id, name, address, email);
            try {
                bll.updateClient(client);
                refreshTable();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, "Validation error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error updating client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a row to edit.");
        }
    }
    /**
     * Deletes the client selected in the table.
     * Refreshes the table after the operation.
     */
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
    /**
     * Finds a client by ID specified through an input dialog.
     * Displays the client details if found, otherwise shows an error message.
     */
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
    /**
     * Refreshes the client table with current data from the business layer.
     */
    public void refreshTable() {
        List<Client> clients = bll.getAllClients();
        TableModelUtils.populateTableWithReflection(view.getTableClients(), clients);
    }
}

package presentation;


import javax.swing.*;
import presentation.ClientView;
import bll.ClientBLL;
import model.Client;

import java.util.List;

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

    public void refreshTable() {
        List<Client> clients = bll.getAllClients();
        TableModelUtils.populateTableWithReflection(view.getTableClients(), clients);
    }
}

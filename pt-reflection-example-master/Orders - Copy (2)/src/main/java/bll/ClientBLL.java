package bll;

import dao.ClientDAO;
import model.Client;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private ClientDAO clientDAO;

    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) {
        return clientDAO.findById(id).orElseThrow(() -> new NoSuchElementException("The client with id =" + id + " was not found!"));
    }

    public int insertClient(Client client) {
        return clientDAO.insert(client);
    }

    public void updateClient(Client client) {
        clientDAO.update(client);
    }

    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }
}

package bll;

import dao.ClientDAO;
import model.Client;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ClientBLL {
    private ClientDAO clientDAO;

    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    public Optional<Client> findClientById(int id) {
        // Assuming dao is an instance of your data access object that interacts with the database
        return clientDAO.findById(id);
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

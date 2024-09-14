package bll;

import bll.validators.EmailValidator;
import dao.ClientDAO;
import model.Client;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ClientBLL {
    private ClientDAO clientDAO;
    private EmailValidator emailValidator = new EmailValidator();

    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    public Optional<Client> findClientById(int id) {
        return clientDAO.findById(id);
    }


    public int insertClient(Client client) {
        emailValidator.validate(client);
        return clientDAO.insert(client);
    }

    public void updateClient(Client client) {
        emailValidator.validate(client);
        clientDAO.update(client);
    }

    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }
}

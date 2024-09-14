package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

public class ClientBLL {

    private List<Validator<Client>> validators;

    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
    }

    public Client findClientById(int id) {
        Client client = ClientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }

    public int insertClient(Client client) {
        for (Validator<Client> validator : validators) {
            validator.validate(client);
        }
        return ClientDAO.insert(client);
    }

    public void updateClient(Client client) {
        for (Validator<Client> validator : validators) {
            validator.validate(client);
        }
        ClientDAO.update(client);
    }

    public void deleteClient(int id) {
        ClientDAO.delete(id);
    }

    public List<Client> getAllClients() {
        return ClientDAO.findAll();
    }
}

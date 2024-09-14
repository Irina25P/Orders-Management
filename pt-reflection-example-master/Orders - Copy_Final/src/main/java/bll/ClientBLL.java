package bll;

import bll.validators.EmailValidator;
        import dao.ClientDAO;
        import model.Client;

        import java.util.List;
        import java.util.NoSuchElementException;
        import java.util.Optional;
/**
 * Handles business logic related to client operations including search, insertion, update, and deletion.
 * Ensures all client operations are validated, particularly the client's email, before proceeding with database transactions.
 */
public class ClientBLL {
    private ClientDAO clientDAO;
    private EmailValidator emailValidator = new EmailValidator();
    /**
     * Constructs a {@code ClientBLL} object, initializing the necessary data access object (DAO) for client operations.
     */
    public ClientBLL() {
        this.clientDAO = new ClientDAO();
    }
    /**
     * Retrieves a client by their ID from the database.
     * If no client is found, the result will be an empty {@code Optional}.
     * @param id the unique identifier of the client
     * @return an {@code Optional} containing the client if found, or an empty {@code Optional} if not found
     */
    public Optional<Client> findClientById(int id) {
        return clientDAO.findById(id);
    }
    /**
     * Inserts a new client into the database after validating the client's email address.
     * Throws {@code IllegalArgumentException} if the email is invalid.
     * @param client the client to be inserted into the database
     * @return the generated ID of the inserted client
     * @throws IllegalArgumentException if the email validation fails
     */
    public int insertClient(Client client) {
        emailValidator.validate(client);
        return clientDAO.insert(client);
    }
    /**
     * Updates an existing client's information in the database after validating the client's email.
     * Throws {@code IllegalArgumentException} if the email is invalid.
     * @param client the client whose information is to be updated
     * @throws IllegalArgumentException if the email validation fails
     */
    public void updateClient(Client client) {
        emailValidator.validate(client);
        clientDAO.update(client);
    }
    /**
     * Deletes a client from the database using the client's ID.
     * @param id the unique identifier of the client to be deleted
     */
    public void deleteClient(int id) {
        clientDAO.delete(id);
    }
    /**
     * Retrieves all clients from the database.
     * @return a list of all clients
     */
    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }
}

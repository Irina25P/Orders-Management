package model;
/**
 * Represents a client in the system, encapsulating key details about a client such as name, address, and email.
 * This class provides constructors for creating client instances in various contexts within the application,
 * and methods for retrieving and updating client information.
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;
    /**
     * Constructs a new Client with specified id, name, address, and email.
     * This constructor is typically used when retrieving existing client information from a data source
     * where the client's ID is already defined.
     *
     * @param id the unique identifier for the client
     * @param name the name of the client
     * @param address the physical address of the client
     * @param email the email address of the client
     */
    public Client(int id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }
    /**
     * Default constructor for creating an instance of Client without setting properties initially.
     * This constructor is typically used in situations where the client's properties will be set
     * post-creation using setter methods.
     */
    public Client() {
    }
    /**
     * Constructs a new Client with specified name, address, and email but without an id.
     * This constructor is typically used when creating new clients whose ID will be assigned automatically by the database.
     * @param name the name of the client
     * @param address the physical address of the client
     * @param email the email address of the client
     */
    public Client(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }
    /**
     * Returns the ID of the client.
     * @return the unique identifier for the client
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the ID of the client.
     * @param id the unique identifier to be set for the client
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Returns the name of the client.
     * @return the client's name
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the address of the client.
     * @return the client's physical address
     */
    public String getAddress() {
        return address;
    }
    /**
     * Returns the email address of the client.
     * @return the client's email address
     */
    public String getEmail() {
        return email;
    }
    /**
     * Returns a string representation of the client, typically used for logging and debugging purposes.
     * @return a string description of the client
     */
    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + "]";
    }
}

package model;
/**
 * Represents an order within the system, encapsulating all relevant details such as order ID, client ID,
 * product ID, and quantity. This class is designed to handle the creation and management of order records.
 */
public class Order {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;
    /**
     * Constructs a new Order with specified details.
     * This constructor is typically used when retrieving existing order data from a data source
     * or creating a new order with known attributes.
     * @param id the unique identifier of the order
     * @param clientId the unique identifier of the client who made the order
     * @param productId the unique identifier of the product ordered
     * @param quantity the amount of the product ordered
     */
    public Order(int id, int clientId, int productId, int quantity) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }
    /**
     * Returns the order ID.
     * @return the unique identifier for the order
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the order ID.
     * @param id the unique identifier to be set for the order
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Returns the product ID for the order.
     *
     * @return the unique identifier of the product
     */
    public int getProductId() {
        return productId;
    }
    /**
     * Returns the quantity of the product ordered.
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

}

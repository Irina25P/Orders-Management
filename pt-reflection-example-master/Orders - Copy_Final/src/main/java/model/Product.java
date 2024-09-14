package model;

import java.math.BigDecimal;
/**
 * Represents a product in the system, encapsulating essential details such as product ID, name, price, and stock levels.
 * This class provides constructors for creating product instances for various use cases, including database retrieval
 * and new product creation.
 */
public class Product {
    private int id;
    private String name;
    private BigDecimal price;
    private int stock;
    /**
     * Default constructor for creating an instance of Product.
     * This constructor is typically used in situations where product properties are set after creation using setters.
     */
    public Product() {
    }
    /**
     * Constructs a new Product with specified id, name, price, and stock level.
     * This constructor is primarily used for retrieving existing products from a data source where the product ID is known.
     * @param id the unique identifier for the product
     * @param name the name of the product
     * @param price the price of the product, encapsulated in a {@link BigDecimal} for precision
     * @param stock the stock level of the product
     */
    public Product(int id, String name, BigDecimal price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    /**
     * Constructs a new Product with specified name, price, and stock level but without an id.
     * This constructor is used for creating new products where the ID will be assigned automatically by the database.
     * @param name the name of the product
     * @param price the price of the product, encapsulated in a {@link BigDecimal} for precision
     * @param stock the stock level of the product
     */
    public Product(String name, BigDecimal price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    /**
     * Returns the ID of the product.
     * @return the unique identifier for the product
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the ID of the product.
     * @param id the unique identifier to be set for the product
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Returns the name of the product.
     * @return the product's name
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the price of the product.
     * @return the product's price, represented as a {@link BigDecimal}
     */
    public BigDecimal getPrice() {
        return price;
    }
    /**
     * Returns the stock level of the product.
     * @return the current stock level
     */
    public int getStock() {
        return stock;
    }
    /**
     * Sets the stock level of the product.
     * @param stock the stock level to be set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * Provides a string representation of the product, typically used for logging and display purposes.
     * @return a string description of the product
     */
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", stock=" + stock + "]";
    }
}

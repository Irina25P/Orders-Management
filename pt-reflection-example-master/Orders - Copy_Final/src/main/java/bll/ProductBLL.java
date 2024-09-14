package bll;

import bll.validators.PriceValidator;
import bll.validators.StockValidator;
import dao.ProductDAO;
import model.Client;
import model.Product;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
/**
 * Handles business logic for product operations, including search, insertion, update, and deletion of products.
 * This class ensures that all product-related actions are preceded by validations for price and stock,
 * ensuring that only valid products are processed and stored in the database.
 */
public class ProductBLL {
    private ProductDAO productDAO;
    private PriceValidator priceValidator = new PriceValidator();
    private StockValidator stockValidator = new StockValidator();
    /**
     * Constructs a {@code ProductBLL} object, initializing the necessary data access object (DAO) for product operations.
     */
    public ProductBLL() {
        productDAO = new ProductDAO();
    }
    /**
     * Retrieves a product by its ID from the database.
     * If no product is found, the result will be an empty {@code Optional}.
     *
     * @param id the unique identifier of the product
     * @return an {@code Optional} containing the product if found, or an empty {@code Optional} if not found
     */
    public Optional<Product> findProductById(int id) {
        return productDAO.findById(id);
    }
    /**
     * Inserts a new product into the database after validating its price and stock.
     * Validation ensures that the product has a non-negative stock and a price greater than zero.
     * @param product the product to be inserted into the database
     * @return the generated ID of the newly inserted product
     * @throws IllegalArgumentException if the product fails price or stock validation
     */
    public int insertProduct(Product product) {
        priceValidator.validate(product);
        stockValidator.validate(product);
        return productDAO.insert(product);
    }
    /**
     * Inserts a new product into the database after validating its price and stock.
     * Validation ensures that the product has a non-negative stock and a price greater than zero.
     * @param product the product to be inserted into the database
     * @return the generated ID of the newly inserted product
     * @throws IllegalArgumentException if the product fails price or stock validation
     */
    public void updateProduct(Product product) {
        priceValidator.validate(product);  // Validate price
        stockValidator.validate(product);
        productDAO.update(product);
    }
    /**
     * Deletes a product from the database using the product's ID.
     * @param id the unique identifier of the product to be deleted
     */
    public void deleteProduct(int id) {
        productDAO.delete(id);
    }
    /**
     * Retrieves all products currently stored in the database.
     * @return a list of all products
     */
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }
}

package bll.validators;

import java.util.NoSuchElementException;
import model.Order;
import model.Product;
import dao.ProductDAO;
/**
 * Validates an order against product availability in the database.
 * This validator checks if the product associated with the order exists and if there are sufficient quantities
 * in stock to fulfill the order. It uses {@code ProductDAO} to retrieve product details.
 */
public class OrderValidator implements Validator<Order> {
    private ProductDAO productDAO;
    /**
     * Constructs an {@code OrderValidator} with a new instance of {@code ProductDAO}.
     * This setup allows access to the product database necessary for validating orders.
     */
    public OrderValidator() {
        this.productDAO = new ProductDAO();
    }
    /**
     * Validates the specified order to ensure the product exists and the ordered quantity is available.
     *
     * @param order the order to be validated
     * @throws NoSuchElementException if no product with the specified ID is found in the database
     * @throws IllegalArgumentException if the ordered quantity exceeds the product's available stock
     */
    @Override
    public void validate(Order order) {
        Product product = productDAO.findById(order.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + order.getProductId()));

        if (product.getStock() < order.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock for the product.");
        }
    }
}

package bll;

import bll.validators.OrderValidator;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Client;
import model.Order;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
/**
 * Handles business logic for order operations, including insertion, updating, and retrieval.
 * This class ensures that all order-related actions are validated and that product inventory is adjusted accordingly.
 */
public class OrderBLL {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private OrderValidator orderValidator = new OrderValidator();
    /**
     * Constructs an {@code OrderBLL} object, initializing the necessary DAOs for order and product operations.
     */
    public OrderBLL() {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
    }
    /**
     * Inserts an order into the database after validating it and ensuring sufficient product stock.
     * The stock is updated to reflect the order quantity if the order is successfully inserted.
     *
     * @param order the order to be inserted
     * @return the generated ID of the newly inserted order
     * @throws NoSuchElementException if the product associated with the order is not found
     * @throws IllegalArgumentException if there is insufficient stock for the order
     */
    public int insertOrder(Order order) {
        orderValidator.validate(order);
        Product product = productDAO.findById(order.getProductId())
                .orElseThrow(() -> new NoSuchElementException("No product found with ID: " + order.getProductId()));
        if (product.getStock() >= order.getQuantity()) {
            product.setStock(product.getStock() - order.getQuantity());
            productDAO.update(product);
            return orderDAO.insert(order);
        }
        throw new IllegalArgumentException("Insufficient stock available.");
    }
}

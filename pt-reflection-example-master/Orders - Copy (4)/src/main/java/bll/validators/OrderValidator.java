package bll.validators;

import java.util.NoSuchElementException;
import model.Order;
import model.Product;
import dao.ProductDAO;

public class OrderValidator implements Validator<Order> {
    private ProductDAO productDAO;

    public OrderValidator() {
        this.productDAO = new ProductDAO();
    }

    @Override
    public void validate(Order order) {
        Product product = productDAO.findById(order.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + order.getProductId()));

        if (product.getStock() < order.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock for the product.");
        }
    }
}

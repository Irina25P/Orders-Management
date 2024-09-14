package bll.validators;

import model.Order;
import model.Product;
import dao.ProductDAO;

public class OrderValidator implements Validator<Order> {
    @Override
    public void validate(Order order) {
        Product product = ProductDAO.findById(order.getProductId());
        if (product == null || product.getStock() < order.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock for the product.");
        }
    }
}


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

public class OrderBLL {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private OrderValidator orderValidator = new OrderValidator();

    public OrderBLL() {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
    }

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

    public void updateOrder(Order order) {
        orderValidator.validate(order);
        Product product = productDAO.findById(order.getProductId())
                .orElseThrow(() -> new NoSuchElementException("No product found with ID: " + order.getProductId()));

        Order existingOrder = orderDAO.findById(order.getId())
                .orElseThrow(() -> new NoSuchElementException("No order found with ID: " + order.getId()));

        int restockAmount = existingOrder.getQuantity() - order.getQuantity();
        if (restockAmount >= 0 || product.getStock() + restockAmount >= 0) {
            product.setStock(product.getStock() + restockAmount);
            productDAO.update(product);
            orderDAO.update(order);
        } else {
            throw new IllegalArgumentException("Insufficient stock to reduce order quantity.");
        }
    }

    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }
}

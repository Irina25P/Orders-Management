package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.OrderValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;
import model.Product;

public class OrderBLL {

    private List<Validator<Order>> validators;

    public OrderBLL() {
        validators = new ArrayList<>();
        validators.add(new OrderValidator());
    }

    public Order findOrderById(int id) {
        Order order = OrderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return order;
    }

    public int insertOrder(Order order) {
        for (Validator<Order> validator : validators) {
            validator.validate(order);
        }
        Product product = ProductDAO.findById(order.getProductId());
        if (product.getStock() >= order.getQuantity()) {
            product.setStock(product.getStock() - order.getQuantity());
            ProductDAO.update(product);
            return OrderDAO.insert(order);
        } else {
            throw new IllegalArgumentException("Insufficient stock available.");
        }
    }

    public void updateOrder(Order order) {
        for (Validator<Order> validator : validators) {
            validator.validate(order);
        }
        Product product = ProductDAO.findById(order.getProductId());
        if (product.getStock() + OrderDAO.findById(order.getId()).getQuantity() >= order.getQuantity()) {
            product.setStock(product.getStock() - order.getQuantity() + OrderDAO.findById(order.getId()).getQuantity());
            ProductDAO.update(product);
            OrderDAO.update(order);
        } else {
            throw new IllegalArgumentException("Insufficient stock available.");
        }
    }

    public List<Order> getAllOrders() {
        return OrderDAO.findAll();
    }
}

package bll.validators;

import model.Product;
/**
 * Validates the stock quantity of a {@code Product} to ensure it is non-negative.
 * This validator checks that the stock is not below zero, enforcing that all products have
 * a valid stock count before processing or saving in the database.
 */
public class StockValidator implements Validator<Product> {
    /**
     * Validates the stock quantity of the given product.
     * <p>
     * This method checks if the stock count of the product is negative. It throws an
     * {@code IllegalArgumentException} if the stock is less than zero to ensure data integrity
     * and prevent logical errors in inventory management.
     *
     * @param product the product whose stock is to be validated
     * @throws IllegalArgumentException if the product's stock is negative
     */
    @Override
    public void validate(Product product) {
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("The stock cannot be negative.");
        }
    }
}

package bll.validators;

import model.Product;

import java.math.BigDecimal;
/**
 * Validates the price of a {@code Product} to ensure it is greater than zero.
 * This validator checks that the product price is not negative or zero, throwing an exception
 * if the price does not meet the specified criteria. This is essential for ensuring that
 * all products have a valid price before being processed or saved in the database.
 */
public class PriceValidator implements Validator<Product> {
    /**
     * Validates the price of the given product.
     * <p>
     * This method checks if the price of the product is greater than zero. It uses {@link BigDecimal}
     * for comparison to handle high precision floating-point operations and monetary values.
     *
     * @param product the product to be validated
     * @throws IllegalArgumentException if the product's price is less than or equal to zero
     */
    @Override
    public void validate(Product product) {
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The price must be greater than zero.");
        }
    }
}
package bll.validators;

import model.Product;

import java.math.BigDecimal;

public class PriceValidator implements Validator<Product> {
    @Override
    public void validate(Product product) {
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The price must be greater than zero.");
        }
    }
}
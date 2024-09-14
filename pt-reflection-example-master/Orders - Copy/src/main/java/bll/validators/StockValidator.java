package bll.validators;

import model.Product;

public class StockValidator implements Validator<Product> {
    @Override
    public void validate(Product product) {
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("The stock cannot be negative.");
        }
    }
}

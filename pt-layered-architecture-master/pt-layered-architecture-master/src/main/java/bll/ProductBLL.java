package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.PriceValidator;
import bll.validators.StockValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

public class ProductBLL {

    private List<Validator<Product>> validators;

    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new PriceValidator());
        validators.add(new StockValidator());
    }

    public Product findProductById(int id) {
        Product product = ProductDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }

    public int insertProduct(Product product) {
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }
        return ProductDAO.insert(product);
    }

    public void updateProduct(Product product) {
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }
        ProductDAO.update(product);
    }

    public void deleteProduct(int id) {
        ProductDAO.delete(id);
    }

    public List<Product> getAllProducts() {
        return ProductDAO.findAll();
    }
}

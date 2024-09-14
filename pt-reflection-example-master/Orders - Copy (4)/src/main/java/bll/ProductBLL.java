package bll;

import bll.validators.PriceValidator;
import bll.validators.StockValidator;
import dao.ProductDAO;
import model.Client;
import model.Product;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProductBLL {
    private ProductDAO productDAO;
    private PriceValidator priceValidator = new PriceValidator();
    private StockValidator stockValidator = new StockValidator();

    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    public Optional<Product> findProductById(int id) {
        return productDAO.findById(id);
    }

    public int insertProduct(Product product) {
        priceValidator.validate(product);
        stockValidator.validate(product);
        return productDAO.insert(product);
    }

    public void updateProduct(Product product) {
        priceValidator.validate(product);  // Validate price
        stockValidator.validate(product);
        productDAO.update(product);
    }

    public void deleteProduct(int id) {
        productDAO.delete(id);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }
}

package bll;

import dao.ProductDAO;
import model.Product;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private ProductDAO productDAO;

    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id) {
        return productDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("The product with id " + id + " was not found!"));
    }

    public int insertProduct(Product product) {
        return productDAO.insert(product);
    }

    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    public void deleteProduct(int id) {
        productDAO.delete(id);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }
}

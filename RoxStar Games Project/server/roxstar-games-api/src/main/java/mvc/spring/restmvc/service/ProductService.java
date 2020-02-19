package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Product;

import java.util.Set;

public interface ProductService {
    Set<Product> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    Product deleteProduct(Long id);
    Product getProductsByTitle(String title);
    Set<Product> getProductsByGenre(String genre);
    Set<Product> getProductsByStudio(String studio);
    Set<Product> getProductsByPlatform(String platform);
    Set<Product> getProductsByType(String type);
    Set<Product> getProductsByOnSale(boolean onSale);
    Set<Product> getNewProducts();

}

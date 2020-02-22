package mvc.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dao.ProductRepository;
import mvc.spring.restmvc.exception.EntityAlreadyExistsException;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.model.Product;
import mvc.spring.restmvc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository repo;

    @Autowired
    public void setProductRepository(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Set<Product> getAllProducts() {
        return new HashSet<>(repo.findAll());
    }

    @Override
    public Product getProductById(Long id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Game with ID=%s does not exist.", id)));
    }


    @Override
    @PreAuthorize("hasRole('PROD_SUPPLIER')")
    public Product createProduct(Product product) {
        Product result = repo.findByTitle(product.getTitle());
        if (result != null) {
            throw new EntityAlreadyExistsException(String.format("Game with title=%s already exists!", product.getTitle()));
        } else {
            log.info("Creating default game: {}", product);
            return insert(product);
        }
    }

    @Transactional
    private Product insert(Product product) {
        product.setId(null);
        return repo.save(product);
    }

    @Override
    @PreAuthorize("#product.studio == authentication.name and hasRole('PROD_SUPPLIER')")
    public Product updateProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product old = getProductById(id);
        repo.deleteById(id);
        return old;
    }

    @Override
    public Product getProductsByTitle(String title) {
        return repo.findByTitle(title);
    }

    @Override
    public Set<Product> getProductsByGenre(String genre) {
        return repo.findByGenre(genre);
    }

    @Override
    public Set<Product> getProductsByStudio(String studio) {
        return repo.findByStudio(studio);
    }

    @Override
    public Set<Product> getProductsByPlatform(String platform) {
        return repo.findByPlatform(platform);
    }

    @Override
    public Set<Product> getProductsByType(String type) {
        return repo.findByType(type);
    }

    @Override
    public Set<Product> getProductsByOnSale(boolean onSale) {
        return repo.findByOnSale(onSale);
    }

    @Override
    public Set<Product> getNewProducts() {
        List<Product> products = repo.findAll();
        products.sort((p2, p1) -> p1.getReleased().compareTo(p2.getReleased()));
        return new HashSet<>(products);

    }
}

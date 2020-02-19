package mvc.spring.restmvc.dao;

import mvc.spring.restmvc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional(readOnly = true)
    Set<Product> findByStudio(String studio);
    @Transactional(readOnly = true)
    Set<Product> findByPlatform(String platform);
    @Transactional(readOnly = true)
    Product findByTitle(String title); // TODO: make query with parameters for security
    @Transactional(readOnly = true)
    Set<Product> findByGenre(String genre);
    @Transactional(readOnly = true)
    Set<Product> findByType(String type);
    @Transactional(readOnly = true)
    Set<Product> findByOnSale(boolean onSale);
}

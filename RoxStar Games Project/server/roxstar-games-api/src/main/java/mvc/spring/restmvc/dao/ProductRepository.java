package mvc.spring.restmvc.dao;

import mvc.spring.restmvc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional(readOnly = true)
    List<Product> findByStudio(String studio);

    @Transactional(readOnly = true)
    List<Product> findByPlatform(String platform);

    @Transactional(readOnly = true)
    Product findByTitle(String title); // TODO: make query with parameters for security

    @Transactional(readOnly = true)
    List<Product> findByGenre(String genre);

    @Transactional(readOnly = true)
    List<Product> findByType(String type);

    @Transactional(readOnly = true)
    List<Product> findByOnSale(boolean onSale);
}

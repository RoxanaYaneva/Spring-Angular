package mvc.spring.restmvc.dao;

import mvc.spring.restmvc.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByStudio(String studio);
    List<Product> findByPlatform(String platform);
    List<Product> findByTitle(String title);
    List<Product> findByGenre(String genre);
    List<Product> findByType(String type);
}

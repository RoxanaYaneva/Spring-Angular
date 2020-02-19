package mvc.spring.restmvc.dao;

import mvc.spring.restmvc.model.Comment;
import mvc.spring.restmvc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional(readOnly = true)
    Set<Comment> findByProduct(Product product);
}

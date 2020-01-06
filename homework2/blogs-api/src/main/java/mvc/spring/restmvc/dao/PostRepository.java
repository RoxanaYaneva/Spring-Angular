package mvc.spring.restmvc.dao;

import mvc.spring.restmvc.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
//    List<Post> findFirst15OrderByPublishedDesc();
    List<Post> findByAuthorId(String authorId);
}

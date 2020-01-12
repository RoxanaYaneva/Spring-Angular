package mvc.spring.blogsapi.repositories;

import mvc.spring.blogsapi.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends MongoRepository<Post, String> {
    @Override
    void delete(Post post);
}

package mvc.spring.blogsapi.repositories;

import mvc.spring.blogsapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {
    @Query(collation = "en_US")
    Optional<User> findByEmail(String email);
}

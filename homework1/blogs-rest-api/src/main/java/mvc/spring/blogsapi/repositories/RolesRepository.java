package mvc.spring.blogsapi.repositories;

import mvc.spring.blogsapi.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String roleName);
}

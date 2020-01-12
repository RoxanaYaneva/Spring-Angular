package mvc.spring.blogsapi.repositories;

import mvc.spring.blogsapi.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionsRepository extends MongoRepository<Permission, String> {
    List<Permission> findByAsset(String asset);
}
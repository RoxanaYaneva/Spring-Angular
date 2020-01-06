package mvc.spring.restmvc.dao;

import mvc.spring.restmvc.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionsRepository extends MongoRepository<Permission, String> {
    List<Permission> findByAsset(String asset);
}
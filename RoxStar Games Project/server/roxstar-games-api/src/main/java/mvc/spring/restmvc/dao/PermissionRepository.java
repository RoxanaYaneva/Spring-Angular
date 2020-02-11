package mvc.spring.restmvc.dao;

import mvc.spring.restmvc.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Transactional(readOnly=true)
    List<Permission> findByAsset(String asset);
}

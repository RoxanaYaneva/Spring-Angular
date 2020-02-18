package mvc.spring.restmvc.dao;

import mvc.spring.restmvc.model.Role;
import mvc.spring.restmvc.model.enums.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Transactional(readOnly = true)
    Optional<Role> findByUserProfile(UserProfile userProfile);
}

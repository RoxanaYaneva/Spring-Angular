package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Role;
import mvc.spring.restmvc.model.enums.UserProfile;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    Role createRole(Role role);
    Role updateRole(Role role);
    Optional<Role> getRoleByUserProfile(UserProfile userProfile);
    void deleteRole(Role role);
}

package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(String id);
    Role createRole(Role role);
    Role updateRole(Role role);
    Optional<Role> getRoleByName(String name);
    void deleteRole(Role role);
}

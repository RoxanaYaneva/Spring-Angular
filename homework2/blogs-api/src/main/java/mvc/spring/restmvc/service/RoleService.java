package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getRoles();
    Role createIfNotExist(Role role);
    Role update(Role role);
    Optional<Role> getRoleByName(String roleName);
    void delete(Role role);
}

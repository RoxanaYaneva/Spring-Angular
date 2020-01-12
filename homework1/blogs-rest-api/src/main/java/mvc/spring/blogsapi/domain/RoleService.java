package mvc.spring.blogsapi.domain;

import mvc.spring.blogsapi.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getRoles();
    Role createIfNotExist(Role role);
    Role update(Role role);
    Optional<Role> getRoleByName(String roleName);
    void delete(Role role);
}

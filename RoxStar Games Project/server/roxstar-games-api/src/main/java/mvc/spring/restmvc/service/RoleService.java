package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Role;
import mvc.spring.restmvc.model.enums.UserProfile;

import java.util.Optional;

public interface RoleService {
    Role createRole(Role role);
    Optional<Role> getRoleByUserProfile(UserProfile userProfile);
}

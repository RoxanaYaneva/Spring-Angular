package mvc.spring.restmvc.init;

import mvc.spring.restmvc.exception.UserAlreadyExistsException;
import mvc.spring.restmvc.service.PermissionService;
import mvc.spring.restmvc.service.PostService;
import mvc.spring.restmvc.service.RoleService;
import mvc.spring.restmvc.service.UserService;
import mvc.spring.restmvc.model.Permission;
import mvc.spring.restmvc.model.Role;
import mvc.spring.restmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static mvc.spring.restmvc.model.Permission.*;
import static mvc.spring.restmvc.model.Role.ROLE_USER;
import static mvc.spring.restmvc.model.Role.ROLE_ADMIN;

@Component
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Permission ownPostRead = permissionService.createIfNotExist(new Permission(OWN, "POST", READ));
        Permission ownPostUpdate = permissionService.createIfNotExist(new Permission(OWN, "POST", UPDATE));
        Permission ownPostDelete = permissionService.createIfNotExist(new Permission(OWN, "POST", DELETE));
        Permission postsRead = permissionService.createIfNotExist(new Permission(ALL, "POST", READ));
        Permission postsCreate = permissionService.createIfNotExist(new Permission(ALL, "POST", CREATE));
        Permission postsUpdate = permissionService.createIfNotExist(new Permission(ALL, "POST", UPDATE));
        Permission postsDelete = permissionService.createIfNotExist(new Permission(ALL, "POST", DELETE));
        Permission ownUserRead = permissionService.createIfNotExist(new Permission(OWN, "USER", READ));
        Permission ownUserUpdate = permissionService.createIfNotExist(new Permission(OWN, "USER", UPDATE));
        Permission usersRead = permissionService.createIfNotExist(new Permission(ALL, "USER", READ));
        Permission usersCreate = permissionService.createIfNotExist(new Permission(ALL, "USER", CREATE));
        Permission usersUpdate = permissionService.createIfNotExist(new Permission(ALL, "USER", UPDATE));
        Permission usersDelete = permissionService.createIfNotExist(new Permission(ALL, "USER", DELETE));

        Role roleUser = roleService.createIfNotExist(new Role(ROLE_USER, Arrays.asList(ownPostRead, ownPostUpdate, ownPostDelete, postsCreate, ownUserRead, ownUserUpdate)));
        Role roleAdmin = roleService.createIfNotExist(new Role(ROLE_ADMIN, Arrays.asList(postsCreate, postsRead, postsUpdate, postsDelete, usersCreate, usersRead, usersUpdate, usersDelete)));


//        userService.createUser(new User("admin@gmail.com", "admin", "DEFAULT", "ADMIN",
//                Arrays.asList(roleAdmin)));
    }
}

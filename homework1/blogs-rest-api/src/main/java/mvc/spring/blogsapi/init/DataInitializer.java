package mvc.spring.blogsapi.init;

import mvc.spring.blogsapi.domain.PermissionService;
import mvc.spring.blogsapi.domain.PostService;
import mvc.spring.blogsapi.domain.RoleService;
import mvc.spring.blogsapi.domain.UserService;
import mvc.spring.blogsapi.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static mvc.spring.blogsapi.model.Permission.*;

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

//        Role roleUser = roleService.createIfNotExist(new Role(ROLE_USER, Arrays.asList(ownPostRead, ownPostUpdate, ownPostDelete, postsRead, postsCreate, ownUserRead, ownUserUpdate)));
//        Role roleAdmin = roleService.createIfNotExist(new Role(ROLE_ADMIN, Arrays.asList(postsCreate, postsRead, postsUpdate, postsDelete, usersCreate, usersRead, usersUpdate, usersDelete)));
//
//        userService.createUser(new User("admin@gmail.com", "admin123", "DEFAULT", "ADMIN",
//                Arrays.asList(roleAdmin)));
//        userService.createUser(new User("roxana@gmail.com", "12345678", "Roxana", "Yaneva",
//                Arrays.asList(roleUser)));
//        userService.createUser(new User("petar@gmail.com", "12345678", "Petar", "Petrov",
//                Arrays.asList(roleUser)));
    }
}

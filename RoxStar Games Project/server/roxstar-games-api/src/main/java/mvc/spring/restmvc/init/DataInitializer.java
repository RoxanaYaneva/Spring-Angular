package mvc.spring.restmvc.init;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.model.Permission;
import mvc.spring.restmvc.model.Role;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.service.GameService;
import mvc.spring.restmvc.service.PermissionService;
import mvc.spring.restmvc.service.RoleService;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import static mvc.spring.restmvc.model.Permission.*;
import static mvc.spring.restmvc.model.Role.*;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting data initialization  ...");
        Permission gameCreate = permService.createPermission(new Permission(ALL, "GAME", CREATE));
        Permission gameRead = permService.createPermission(new Permission(ALL, "GAME", READ));
        Permission gameUpdate = permService.createPermission(new Permission(ALL, "GAME", UPDATE));
        Permission gameDelete = permService.createPermission(new Permission(ALL, "GAME", DELETE));
        Permission ownUserRead = permService.createPermission(new Permission(OWN, "USER", READ));
        Permission ownUserUpdate = permService.createPermission(new Permission(OWN, "USER", UPDATE));
        Permission usersRead = permService.createPermission(new Permission(ALL, "USER", READ));
        Permission usersCreate = permService.createPermission(new Permission(ALL, "USER", CREATE));
        Permission usersUpdate = permService.createPermission(new Permission(ALL, "USER", UPDATE));
        Permission usersDelete = permService.createPermission(new Permission(ALL, "USER", DELETE));
        Role roleUser = roleService.createRole(new Role(ROLE_USER, Arrays.asList(gameRead, ownUserRead, ownUserUpdate)));
        Role roleAdmin = roleService.createRole(new Role(ROLE_ADMIN, Arrays.asList(gameRead, gameUpdate, gameCreate, gameDelete,
                ownUserRead, ownUserUpdate,
                usersRead, usersCreate, usersUpdate, usersDelete)));

        userService.createUser(new User("admin@gmail.com", "admin123", "DEFAULT", "ADMIN",
                Arrays.asList(roleAdmin)));
    }
}

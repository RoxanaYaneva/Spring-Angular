package mvc.spring.restmvc.init;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.model.Permission;
import mvc.spring.restmvc.model.Role;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.model.enums.Asset;
import mvc.spring.restmvc.model.enums.Operation;
import mvc.spring.restmvc.model.enums.Owner;
import mvc.spring.restmvc.model.enums.UserProfile;
import mvc.spring.restmvc.service.ProductService;
import mvc.spring.restmvc.service.PermissionService;
import mvc.spring.restmvc.service.RoleService;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private ProductService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting data initialization  ...");
        Permission gameCreate = permService.createPermission(new Permission(Owner.OWN, Asset.GAME, Operation.WRITE));
        Permission gameRead = permService.createPermission(new Permission(Owner.ALL, Asset.GAME, Operation.READ));
        Permission gameUpdate = permService.createPermission(new Permission(Owner.ALL, Asset.GAME, Operation.UPDATE));
        Permission gameDelete = permService.createPermission(new Permission(Owner.ALL, Asset.GAME, Operation.DELETE));
        Permission ownUserRead = permService.createPermission(new Permission(Owner.OWN, Asset.USER, Operation.READ));
        Permission ownUserUpdate = permService.createPermission(new Permission(Owner.OWN, Asset.USER, Operation.UPDATE));
        Permission usersRead = permService.createPermission(new Permission(Owner.ALL, Asset.USER, Operation.READ));
        Permission usersCreate = permService.createPermission(new Permission(Owner.ALL, Asset.USER, Operation.WRITE));
        Permission usersUpdate = permService.createPermission(new Permission(Owner.ALL, Asset.USER, Operation.UPDATE));
        Permission usersDelete = permService.createPermission(new Permission(Owner.ALL, Asset.USER, Operation.DELETE));
        Role roleCustomer = roleService.createRole(new Role(UserProfile.CUSTOMER, Arrays.asList(gameRead, ownUserRead, ownUserUpdate)));
        Role roleProdSupplier = roleService.createRole(new Role(UserProfile.PROD_SUPPLIER, Arrays.asList(gameRead, gameCreate, gameUpdate, gameDelete, ownUserRead, ownUserUpdate)));
        Role roleAdmin = roleService.createRole(new Role(UserProfile.ADMIN, Arrays.asList(gameRead, ownUserRead, ownUserUpdate,
                usersRead, usersCreate, usersUpdate, usersDelete)));

        userService.createUser(new User("admin@gmail.com", "admin123", "DEFAULT", "ADMIN",
                Arrays.asList(roleAdmin)));
    }
}

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private ProductService productService;
    private RoleService roleService;
    private UserService userService;
    private PermissionService permService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(PermissionService permService) {
        this.permService = permService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting data initialization  ...");

        Permission productCreate = permService.createPermission(new Permission(Owner.OWN, Asset.GAME, Operation.CREATE));
        Permission productRead = permService.createPermission(new Permission(Owner.ALL, Asset.GAME, Operation.READ));
        Permission productUpdate = permService.createPermission(new Permission(Owner.OWN, Asset.GAME, Operation.UPDATE));
        Permission productDelete = permService.createPermission(new Permission(Owner.OWN, Asset.GAME, Operation.DELETE));

        Permission orderCreate = permService.createPermission(new Permission(Owner.ALL, Asset.ORDER, Operation.CREATE));
        Permission orderRead = permService.createPermission(new Permission(Owner.ALL, Asset.ORDER, Operation.READ));
        Permission orderUpdate = permService.createPermission(new Permission(Owner.ALL, Asset.ORDER, Operation.UPDATE));
        Permission orderDelete = permService.createPermission(new Permission(Owner.ALL, Asset.ORDER, Operation.DELETE));
        Permission ownOrderRead = permService.createPermission(new Permission(Owner.OWN, Asset.ORDER, Operation.READ));
        Permission ownOrderDelete = permService.createPermission(new Permission(Owner.OWN, Asset.ORDER, Operation.DELETE));

        Permission usersCreate = permService.createPermission(new Permission(Owner.ALL, Asset.USER, Operation.CREATE));
        Permission usersRead = permService.createPermission(new Permission(Owner.ALL, Asset.USER, Operation.READ));
        Permission usersUpdate = permService.createPermission(new Permission(Owner.ALL, Asset.USER, Operation.UPDATE));
        Permission usersDelete = permService.createPermission(new Permission(Owner.ALL, Asset.USER, Operation.DELETE));
        Permission ownUserRead = permService.createPermission(new Permission(Owner.OWN, Asset.USER, Operation.READ));
        Permission ownUserUpdate = permService.createPermission(new Permission(Owner.OWN, Asset.USER, Operation.UPDATE));

        roleService.createRole(new Role(UserProfile.ROLE_CUSTOMER, new HashSet<>(Arrays.asList(productRead, ownUserRead, ownUserUpdate, orderCreate, ownOrderRead, ownOrderDelete))));
        roleService.createRole(new Role(UserProfile.ROLE_PROD_SUPPLIER, new HashSet<>(Arrays.asList(productRead, productCreate, productUpdate, productDelete, ownUserRead, ownUserUpdate))));

        Role roleAdmin = roleService.createRole(new Role(UserProfile.ROLE_ADMIN, new HashSet<>(Arrays.asList(productRead, ownUserRead, ownUserUpdate,
                usersRead, usersCreate, usersUpdate, usersDelete, orderCreate, orderRead, orderUpdate, orderDelete))));

        userService.createUser(new User("admin@gmail.com", passwordEncoder.encode("admin123"), "DEFAULT", "ADMIN",
                new HashSet<>(Arrays.asList(roleAdmin))));
    }
}

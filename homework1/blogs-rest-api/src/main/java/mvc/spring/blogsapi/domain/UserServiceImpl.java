package mvc.spring.blogsapi.domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mvc.spring.blogsapi.exception.EntityNotFoundException;
import mvc.spring.blogsapi.exception.UserAlreadyExistsException;
import mvc.spring.blogsapi.model.Role;
import mvc.spring.blogsapi.model.User;
import mvc.spring.blogsapi.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mvc.spring.blogsapi.model.Role.ROLE_USER;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository repo;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @PostFilter("filterObject.email == authentication.name or hasAuthority('ALL_USER_READ')")
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserById(String userId) {
        if (userId == null) return null;
        return repo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " was not found."));
    }

    @Override
    @PostFilter("filterObject.email == authentication.name or hasAuthority('ALL_USER_READ')")
    public User getUserByEmail(@NonNull String email) {
        if (email == null) return null;
        return repo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User with email " + email + "was not found"));
    }

    @Override
    public User createUser(User user) {
        Optional<User> result = repo.findByEmail(user.getEmail());
        if (result.isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", user.getEmail()));
        } else {
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                user.setRoles(Arrays.asList(roleService.getRoleByName(ROLE_USER).get()));
            } else {
                List<Role> expandedRoles = user.getRoles().stream()
                        .map(role -> roleService.getRoleByName(role.getName()))
                        .filter(roleOpt -> roleOpt.isPresent())
                        .map(roleOpt -> roleOpt.get())
                        .collect(Collectors.toList());
                log.info(">>> Expanded roles: {}", expandedRoles);
                user.setRoles(expandedRoles);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            log.info("Creating default user: {}", user);
            return repo.insert(user);
        }
    }

    @Override
    @PreAuthorize("#email == authentication.name or hasAuthority('ALL_USER_UPDATE')")
    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public User deleteUser(String id) {
        User old = getUserById(id);
        repo.deleteById(id);
        return old;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> result = repo.findByEmail(email);
        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(result.get().getEmail(),
                result.get().getPassword(),
                mapRolesToAuthorities(result.get().getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .flatMap(role ->
                    Stream.concat(
                        Stream.of(new SimpleGrantedAuthority(role.getName())),
                        role.getPermissions().stream().map(perm -> new SimpleGrantedAuthority(perm.toString()))
                    )
                ).collect(Collectors.toSet());
    }
}

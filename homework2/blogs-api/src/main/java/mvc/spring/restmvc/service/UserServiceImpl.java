package mvc.spring.restmvc.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dao.UserRepository;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.exception.UserAlreadyExistsException;
import mvc.spring.restmvc.model.Role;
import mvc.spring.restmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mvc.spring.restmvc.model.Role.ROLE_USER;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;


@Service
@Slf4j
@Validated
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @PostFilter("filterObject.email == authentication.name or hasAuthority('ALL_USER_READ')")
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserById(String id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id=%s does not exist", id)));
    }

    @Override
    public User getUserByEmail(@NonNull String email) {
        return repo.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with email=%s does not exist", email)));
    }

    @Override
    public User createUser(User user) {
        Optional<User> result = repo.findByEmail(user.getEmail());
        if (result.isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with email=%s already exists.", user.getEmail()));
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
            throw new UsernameNotFoundException("Invalid username or password.");
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
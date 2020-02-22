package mvc.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dao.UserRepository;
import mvc.spring.restmvc.exception.EntityNotFoundException;
import mvc.spring.restmvc.model.Role;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.model.enums.UserProfile;
import mvc.spring.restmvc.service.RoleService;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Validated
public class UserServiceImpl implements UserService {

    private UserRepository repo;
    private RoleService roleService;

    @Autowired
    public void setUserRepository(UserRepository repo) {
        this.repo = repo;
    }

    @Autowired
    public void setRoleService(RoleService service) {
        this.roleService = service;
    }

    @Override
    @PostFilter("filterObject.email == authentication.name or hasAuthority('ALL_USER_READ')")
    public Set<User> getAllUsers() {
        return new HashSet<>(repo.findAll());
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) return null;
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id=%s does not exist", id)));
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> result = repo.findByEmail(email);
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public User createUser(User user) {
        Optional<User> result = repo.findByEmail(user.getEmail());
        if (result.isPresent()) {
            return result.get();
//            throw new UserAlreadyExistsException(String.format("User with email=%s already exists.", user.getEmail()));
        } else {
            user.setRegistered(LocalDateTime.now());
            user.setUpdated((LocalDateTime.now()));
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                user.setRoles(new HashSet<>(Arrays.asList(roleService.getRoleByUserProfile(UserProfile.ROLE_CUSTOMER).get())));
            } else {
                Set<Role> expandedRoles = user.getRoles().stream()
                        .map(role -> roleService.getRoleByUserProfile(role.getRole()))
                        .filter(roleOpt -> roleOpt.isPresent())
                        .map(roleOpt -> roleOpt.get())
                        .collect(Collectors.toSet());
                log.info(">>> Expanded roles: {}", expandedRoles);
                user.setRoles(expandedRoles);
            }
            user.setActive(true);
            log.info("Creating default user: {}", user);
            return insert(user);
        }
    }

    @Transactional
    private User insert(User user) {
        user.setId(null);
        return repo.save(user);
    }

    @Override
    @PreAuthorize("#user.email == authentication.name or hasAuthority('ALL_USER_UPDATE')")
    public User updateUser(User user) {
        user.setUpdated(LocalDateTime.now());
        return repo.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public User deleteUser(Long id) {
        User old = getUserById(id);
        repo.deleteById(id);
        return old;
    }
}

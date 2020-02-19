package mvc.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dao.RoleRepository;
import mvc.spring.restmvc.model.Role;
import mvc.spring.restmvc.model.enums.UserProfile;
import mvc.spring.restmvc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private RoleRepository repo;

    @Autowired
    public void setRoleRepository(RoleRepository repo) {
        this.repo = repo;
    }

    @Override
    public Role createRole(Role role) {
        Role probe = new Role(role.getUserProfile(), null); // TODO:  check what happens here !!!!!!
        Optional<Role> result = repo.findOne(Example.of(probe));
        if (result.isPresent()) {
            return result.get();
        } else {
            log.info(String.format("Inserting new role: {}", role));
            return insert(role);
        }
    }

    @Transactional
    public Role insert(Role role) {
        role.setId(null);
        return repo.save(role);
    }

    @Override
    public Optional<Role> getRoleByUserProfile(UserProfile userProfile) {
        return repo.findByUserProfile(userProfile);
    }

}

package mvc.spring.blogsapi.domain;

import mvc.spring.blogsapi.model.Role;
import mvc.spring.blogsapi.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements  RoleService {
    @Autowired
    private RolesRepository repo;

    @Override
    public List<Role> getRoles() {
        return repo.findAll();
    }

    @Override
    public Role createIfNotExist(Role role) {
        Role probe = new Role(role.getName(), role.getPermissions());
        Optional<Role> result = repo.findOne(Example.of(probe));
        if(result.isPresent()) {
            return result.get();
        } else {
            return repo.insert(role);
        }
    }

    @Override
    public Role update(Role role) {
        return repo.save(role);
    }

    @Override
    public Optional<Role> getRoleByName(String roleName) {
        return repo.findByName(roleName);
    }

    @Override
    public void delete(Role role) {
        repo.delete(role);
    }
}

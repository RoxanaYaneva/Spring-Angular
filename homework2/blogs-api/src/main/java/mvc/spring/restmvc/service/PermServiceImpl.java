package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Permission;
import mvc.spring.restmvc.dao.PermissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermServiceImpl implements PermissionService {
    @Autowired
    private PermissionsRepository repo;

    @Override
    public List<Permission> getPermissions() {
        return repo.findAll();
    }

    @Override
    public Permission createIfNotExist(Permission perm) {
        Permission probe = new Permission(perm.getOwner(), perm.getAsset(), perm.getOperation());
        Optional<Permission> result = repo.findOne(Example.of(probe));
        if(result.isPresent()) {
            return result.get();
        } else {
            return repo.insert(perm);
        }
    }

    @Override
    public Permission update(Permission permission) {
        return repo.save(permission);
    }

    @Override
    public List<Permission> getPermissionsByAsset(String asset) {
        return repo.findByAsset(asset);
    }

    @Override
    public void delete(Permission  permission) {
        repo.delete(permission);
    }
}
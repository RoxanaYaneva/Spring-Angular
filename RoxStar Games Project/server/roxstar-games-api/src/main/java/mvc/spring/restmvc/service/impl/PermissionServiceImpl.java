package mvc.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.dao.PermissionRepository;
import mvc.spring.restmvc.model.Permission;
import mvc.spring.restmvc.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private PermissionRepository repo;

    @Autowired
    public void setPermissionRepository(PermissionRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Permission> getAllPermissions() {
        return repo.findAll();
    }

    @Override
    public Permission createPermission(Permission permission) {
        Permission probe = new Permission(permission.getOwner(), permission.getAsset(), permission.getOperation());
        Optional<Permission> result = repo.findOne(Example.of(probe));
        if (result.isPresent()) {
            return result.get();
        } else {
            log.info(String.format("Inserting new permission: {}", permission));
            return insert(permission);
        }
    }

    @Transactional
    public Permission insert(Permission perm) {
        perm.setId(null);
        return repo.save(perm);
    }

    @Override
    public Permission updatePermission(Permission permission) {
        return repo.save(permission);
    }

    @Override
    public List<Permission> getPermissionsByAsset(String asset) {
        return repo.findByAsset(asset);
    }

    @Override
    public void deletePermission(Permission permission) {
        repo.delete(permission);
    }
}

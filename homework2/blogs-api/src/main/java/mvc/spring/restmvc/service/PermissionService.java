package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> getPermissions();
    Permission createIfNotExist(Permission perm);
    List<Permission> getPermissionsByAsset(String asset);
    Permission update(Permission perm);
    void delete(Permission perm);
}

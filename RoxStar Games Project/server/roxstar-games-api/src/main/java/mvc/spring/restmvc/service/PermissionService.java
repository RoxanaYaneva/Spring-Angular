package mvc.spring.restmvc.service;

import mvc.spring.restmvc.model.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> getAllPermissions();
    Permission createPermission(Permission permission);
    Permission updatePermission(Permission permission);
    List<Permission> getPermissionsByAsset(String asset);
    void deletePermission(Permission permission);
}

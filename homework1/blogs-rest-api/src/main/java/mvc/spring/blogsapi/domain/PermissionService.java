package mvc.spring.blogsapi.domain;

import mvc.spring.blogsapi.model.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> getPermissions();
    Permission createIfNotExist(Permission perm);
    List<Permission> getPermissionsByAsset(String asset);
    Permission update(Permission perm);
    void delete(Permission perm);
}

package app.services;

import app.models.entity.Role;
import app.models.service.RoleServiceModel;

public interface RoleService{
    void seedRolesInDb();

    RoleServiceModel findByAuthority(String student);

    Role findAuthorityByName(String role_teacher);
}

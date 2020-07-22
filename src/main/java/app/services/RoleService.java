package app.services;

import app.models.entity.Role;
import app.models.service.RoleServiceModel;

import javax.management.relation.RoleNotFoundException;

public interface RoleService{
    void seedRolesInDb();

    RoleServiceModel findByAuthority(String student) throws RoleNotFoundException;

    Role findAuthorityByName(String role_teacher);
}

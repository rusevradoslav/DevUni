package app.services;

import app.models.entity.Role;
import app.models.service.RoleServiceModel;

import javax.management.relation.RoleNotFoundException;

public interface RoleService{
    void seedRolesInDb();

    RoleServiceModel findByAuthority(String authority) throws RoleNotFoundException;

    Role findAuthorityByName(String authority) throws RoleNotFoundException;
}

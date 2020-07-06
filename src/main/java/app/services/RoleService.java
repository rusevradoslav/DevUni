package app.services;

import app.models.service.RoleServiceModel;

public interface RoleService{
    void seedRolesInDb();

    RoleServiceModel findByAuthority(String student);
}

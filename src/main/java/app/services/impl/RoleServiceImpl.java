package app.services.impl;

import app.models.entity.Role;
import app.models.service.RoleServiceModel;
import app.repositories.RoleRepository;
import app.services.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new Role("ROLE_ROOT_ADMIN"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            this.roleRepository.saveAndFlush(new Role("ROLE_TEACHER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_STUDENT"));
        }
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) throws RoleNotFoundException {

        return this.roleRepository.findFirstByAuthority(authority)
                .map(role -> this.modelMapper.map(role, RoleServiceModel.class))
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
    }

    @Override
    public Role findAuthorityByName(String authorityName) {

        return this.roleRepository.findFirstByAuthority(authorityName).orElse(null);
    }

}

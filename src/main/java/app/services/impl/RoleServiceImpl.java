package app.services.impl;

import app.models.entity.Role;
import app.models.service.RoleServiceModel;
import app.repositories.RoleRepository;
import app.services.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            this.roleRepository.saveAndFlush(new Role("ADMIN"));
            this.roleRepository.saveAndFlush(new Role("TEACHER"));
            this.roleRepository.saveAndFlush(new Role("STUDENT"));
        }
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        Role role = roleRepository.findFirstByAuthority(authority).orElse(null);
        System.out.println();
        RoleServiceModel roleServiceModel = this.modelMapper.map(role,RoleServiceModel.class);

        return roleServiceModel;
    }

}
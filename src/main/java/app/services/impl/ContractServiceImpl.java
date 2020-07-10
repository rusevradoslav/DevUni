package app.services.impl;

import app.models.entity.User;
import app.models.service.UserServiceModel;
import app.services.ContractService;
import app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContractServiceImpl implements ContractService {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public UserServiceModel currentUser() {
        System.out.println();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.findByName(username);
        System.out.println();
        if (!username.equals("anonymousUser") && user != null) {
            return modelMapper.map(user, UserServiceModel.class);
        }
        return null;
    }


}

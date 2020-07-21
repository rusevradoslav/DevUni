package app.services.impl;

import app.models.service.UserServiceModel;
import app.services.ContractService;
import app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContractServiceImpl implements ContractService {

    private final UserService userService;

    @Override
    public UserServiceModel currentUser() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserServiceModel user = this.userService.findByName(username);

        if (!username.equals("anonymousUser") && user != null) {
            return user;
        }
        return null;
    }


}

package app.services.impl;

import app.models.entity.User;
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
    public User currentUser() {
        System.out.println();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.findByName(username);
        System.out.println();
        if (!username.equals("anonymousUser") && user != null) {
            return user;
        }
        return null;
    }


}

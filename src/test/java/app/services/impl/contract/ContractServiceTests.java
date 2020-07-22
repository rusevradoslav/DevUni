package app.services.impl.contract;

import app.models.service.UserServiceModel;
import app.services.ContractService;
import app.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(MockitoJUnitRunner.class)
public class ContractServiceTests {

    @Mock
    ContractService service;

    @Mock
    UserService userService;

    @Mock
    SecurityContext context;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void currentUser_Should_ReturnNull_When_UserIsAnonymous() {
        Authentication auth = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return new ArrayList<>();
            }

            @Override
            public Object getCredentials() {
                return new Object();
            }

            @Override
            public Object getDetails() {
                return new Object();
            }

            @Override
            public Object getPrincipal() {
                return (Principal) () -> "anonymousUser";
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "anonymousUser";
            }
        };
        //Arrange
        Mockito.lenient().when(userService.findByName("anonymousUser"))
                .thenReturn(new UserServiceModel());
        Mockito.when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        UserServiceModel userServiceModel = service.currentUser();

        Assert.assertNull(userServiceModel);
    }

}
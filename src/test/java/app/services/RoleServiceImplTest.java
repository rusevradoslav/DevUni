package app.services;


import app.models.entity.Role;
import app.models.service.RoleServiceModel;
import app.repositories.RoleRepository;
import app.services.impl.RoleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {
    private static final String ROLE_ROOT_ADMIN = "ROLE_ROOT_ADMIN";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_TEACHER = "ROLE_TEACHER";
    private static final String ROLE_STUDENT = "ROLE_STUDENT";

    private static List<Role> fakeRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void init() {
        System.out.println();
        fakeRepository = new ArrayList<>();

        //instead of saving in db add in fake repository
        when(roleRepository.saveAndFlush(isA(Role.class)))
                .thenAnswer(invocation -> {

                    fakeRepository.add((Role) invocation.getArguments()[0]);
                    return null;
                });

        //actual mappings
        ModelMapper actualMapper = new ModelMapper();
        when(modelMapper.map(any(Role.class), eq(RoleServiceModel.class)))
                .thenAnswer(invocation -> actualMapper.map(invocation.getArguments()[0], RoleServiceModel.class));
    }

    @Test
    public void seedRoles_shouldSeedAllRoles_whenRepositoryEmpty() {

        //Arrange
        when(roleRepository.count()).thenReturn(0L);

        //Act
        roleService.seedRolesInDb();

        //Assert
        int expected = 4;
        int actual = fakeRepository.size();
        assertEquals(expected, actual);
        assertEquals(fakeRepository.get(0).getAuthority(), ROLE_ROOT_ADMIN);
        assertEquals(fakeRepository.get(1).getAuthority(), ROLE_ADMIN);
        assertEquals(fakeRepository.get(2).getAuthority(), ROLE_TEACHER);
        assertEquals(fakeRepository.get(3).getAuthority(), ROLE_STUDENT);
    }

    @Test
    public void seedRoles_shouldDoNothing_whenRepositoryIsNotEmpty() {
        //Arrange
        when(roleRepository.count()).thenReturn(4L);

        //Act
        roleService.seedRolesInDb();

        //Assert
        int expected = 0;
        int actual = fakeRepository.size();

        assertEquals(expected, actual);
    }

    @Test
    public void findByAuthority_shouldReturnCorrectRoleServiceImpl_WhenAuthorityExist() throws RoleNotFoundException {

        //Arrange
        when(roleRepository.findFirstByAuthority(ROLE_ROOT_ADMIN))
                .thenReturn(Optional.of(new Role(ROLE_ROOT_ADMIN)));

        //Act
        RoleServiceModel roleServiceModel = this.roleService.findByAuthority(ROLE_ROOT_ADMIN);

        //Assert

        String expected = ROLE_ROOT_ADMIN;
        String actual = roleServiceModel.getAuthority();

        assertEquals(expected, actual);
    }

    @Test(expected = RoleNotFoundException.class)
    public void findByAuthority_shouldThrowException_WhenRoleDoesNotExist() throws RoleNotFoundException {

        //Arrange
        when(roleRepository.findFirstByAuthority("INVALID_ROLE")).thenReturn(Optional.empty());

        //Act
        roleService.findByAuthority("INVALID_ROLE");
    }


    public void findByAuthorityByName_shouldReturnCorrectRole_WhenAuthorityExist() throws RoleNotFoundException {
        //Arrange
        when(roleRepository.findFirstByAuthority(ROLE_ROOT_ADMIN)).thenReturn(Optional.of(new Role(ROLE_ROOT_ADMIN)));

        //Act
        Role authority = this.roleService.findAuthorityByName(ROLE_ROOT_ADMIN);

        //Assert
        String actual = ROLE_ROOT_ADMIN;
        String expected = authority.getAuthority();

        assertEquals(actual, expected);

    }

    @Test(expected = RoleNotFoundException.class)
    public void findByAuthorityByName_shouldThrowException_WhenAuthorityDoesNotExist() throws RoleNotFoundException {
        //Arrange
        when(roleRepository.findFirstByAuthority("INVALID_ROLE")).thenReturn(Optional.empty());

        //Act
        this.roleService.findAuthorityByName("INVALID_ROLE");

    }
}
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

    private static List<Role> testRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void init() {

        testRepository = new ArrayList<>();

        //instead of saving in db add in fake repository
        when(roleRepository.saveAndFlush(isA(Role.class)))
                .thenAnswer(invocation -> {

                    testRepository.add((Role) invocation.getArguments()[0]);
                    return null;
                });

        //actual mappings
        ModelMapper actualMapper = new ModelMapper();
        when(modelMapper.map(any(Role.class), eq(RoleServiceModel.class)))
                .thenAnswer(invocation -> actualMapper.map(invocation.getArguments()[0], RoleServiceModel.class));
        Role root_admin = new Role(ROLE_ROOT_ADMIN);
        Role role_admin = new Role(ROLE_ADMIN);
        Role role_teacher = new Role(ROLE_TEACHER);
        Role role_student = new Role(ROLE_STUDENT);

        when(roleRepository.findFirstByAuthority(ROLE_ROOT_ADMIN)).thenReturn(Optional.of(root_admin));
        when(roleRepository.findFirstByAuthority(ROLE_ADMIN)).thenReturn(Optional.of(role_admin));
        when(roleRepository.findFirstByAuthority(ROLE_TEACHER)).thenReturn(Optional.of(role_teacher));
        when(roleRepository.findFirstByAuthority(ROLE_STUDENT)).thenReturn(Optional.of(role_student));
    }

    @Test
    public void seedRoles_shouldSeedAllRoles_whenRepositoryEmpty() {
        //Arrange
        when(roleRepository.count()).thenReturn(0L);


        //Act
        roleService.seedRolesInDb();

        //Assert
        int expected = 4;
        int actual = testRepository.size();
        Role rootAdmin = roleRepository.findFirstByAuthority("ROLE_ROOT_ADMIN").orElse(null);
        String roleRootAdmin = rootAdmin.getAuthority();

        Role admin = roleRepository.findFirstByAuthority("ROLE_ADMIN").orElse(null);
        String roleAdmin = admin.getAuthority();

        Role teacher = roleRepository.findFirstByAuthority("ROLE_TEACHER").orElse(null);
        String roleTeacher = teacher.getAuthority();

        Role student = roleRepository.findFirstByAuthority("ROLE_STUDENT").orElse(null);
        String roleStudent = student.getAuthority();

        assertEquals(expected, actual);
        assertEquals(roleRootAdmin, ROLE_ROOT_ADMIN);
        assertEquals(roleAdmin, ROLE_ADMIN);
        assertEquals(roleTeacher, ROLE_TEACHER);
        assertEquals(roleStudent, ROLE_STUDENT);
    }

    @Test
    public void seedRoles_shouldDoNothing_whenRepositoryIsNotEmpty() {
        //Arrange
        when(roleRepository.count()).thenReturn(4L);

        //Act
        roleService.seedRolesInDb();

        //Assert
        long expected = 4;
        long actual = roleRepository.count();

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

        //Act //Assert
        this.roleService.findAuthorityByName("INVALID_ROLE");


    }
}
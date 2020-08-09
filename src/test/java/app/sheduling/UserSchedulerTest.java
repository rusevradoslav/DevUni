package app.sheduling;

import app.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserSchedulerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserScheduler userScheduler;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void findAllStudentsWithRequest_shouldReturnCorrectMethod() {
        this.userScheduler.cancelTeacherRequest();

        verify(this.userService, times(1)).findAllStudentsWithRequests();

    }


}
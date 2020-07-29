package app.sheduling;

import app.models.service.UserServiceModel;
import app.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserScheduler {

    private UserService userService;

    @Scheduled(cron = "0 0 */12 ? * *")
    public void cancelTeacherRequest(){
        this.userService.findAllStudentsWithRequests()
                .forEach(userDetailsViewModel -> {
                    UserServiceModel user = userService.findByName(userDetailsViewModel.getUsername());
                    this.userService.cancelTeacherRequest(user);
                });
    }
}

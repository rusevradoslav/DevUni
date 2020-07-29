package app.init;

import app.services.RoleService;
import app.services.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInit implements CommandLineRunner {
    private final RoleService roleService;
    private final TopicService topicService;

    @Override
    public void run(String... args) throws Exception {
        roleService.seedRolesInDb();
        roleService.seedRolesInDb();
        topicService.seedTopicsInDb();
    }
}

package app.services;

import app.models.service.AboutMeServiceModel;
import app.models.service.UserServiceModel;

public interface AboutMeService {

    void addTeacherAboutMe(UserServiceModel userServiceModel, AboutMeServiceModel aboutMeAddBindingModel);

}

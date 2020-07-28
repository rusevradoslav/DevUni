package app.services;

import app.models.service.AboutMeServiceModel;
import app.models.service.UserServiceModel;

public interface AboutMeService {

    AboutMeServiceModel addTeacherAboutMe(UserServiceModel userServiceModel, AboutMeServiceModel aboutMeAddBindingModel);

}

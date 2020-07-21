package app.services.impl;

import app.models.entity.AboutMe;
import app.models.entity.User;
import app.models.service.AboutMeServiceModel;
import app.models.service.UserServiceModel;
import app.repositories.AboutMeRepository;
import app.services.AboutMeService;
import app.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AboutMeServiceImpl implements AboutMeService {
    private final ModelMapper modelMapper;
    private final AboutMeRepository aboutMeRepository;
    private final UserService userService;

    @Override
    public void addTeacherAboutMe(UserServiceModel userServiceModel, AboutMeServiceModel aboutMeAddBindingModel) {
        AboutMe aboutMe = this.modelMapper.map(aboutMeAddBindingModel, AboutMe.class);
        this.aboutMeRepository.saveAndFlush(aboutMe);
        User user = this.modelMapper.map(userServiceModel, User.class);

        this.userService.setAboutMeToTheTeacher(aboutMe, user);

    }

}
package app.services.impl;

import app.repositories.CustomFileRepository;
import app.services.CustomFileService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomFileServiceImpl implements CustomFileService {
    CustomFileRepository customFileRepository;


}

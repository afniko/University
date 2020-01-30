package ua.com.foxminded.task.validation.validator.property.unique.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.Faculty;
import ua.com.foxminded.task.service.FacultyService;
import ua.com.foxminded.task.validation.validator.property.unique.Command;

@Component
public class FacultyCommand implements Command {

    @Autowired
    private FacultyService facultyService;

    @Override
    public boolean check(String fieldId, String fieldUnique) {
        boolean result = true;
        Faculty objectExist = facultyService.findByTitle(fieldUnique);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(fieldId));
        }
        return result;
    }

}

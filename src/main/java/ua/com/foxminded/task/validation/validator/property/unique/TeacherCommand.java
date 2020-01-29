package ua.com.foxminded.task.validation.validator.property.unique;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.Teacher;
import ua.com.foxminded.task.service.TeacherService;

@Component
public class TeacherCommand implements Command {

    @Autowired
    private TeacherService teacherService;

    @Override
    public boolean check(String fieldId, String fieldUnique) {
        boolean result = true;
        Teacher objectExist = teacherService.findByIdFees(Integer.valueOf(fieldUnique));
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(fieldId));
        }
        return result;
    }

}

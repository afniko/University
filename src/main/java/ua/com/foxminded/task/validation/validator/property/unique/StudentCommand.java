package ua.com.foxminded.task.validation.validator.property.unique;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.service.StudentService;

@Component
public class StudentCommand implements Command {

    @Autowired
    private StudentService studentService;

    @Override
    public boolean check(String fieldId, String fieldUnique) {
        boolean result = true;
        Student objectExist = studentService.findByIdFees(Integer.valueOf(fieldUnique));
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(fieldId));
        }
        return result;
    }

}

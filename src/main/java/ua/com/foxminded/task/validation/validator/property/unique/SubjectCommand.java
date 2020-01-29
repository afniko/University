package ua.com.foxminded.task.validation.validator.property.unique;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.Subject;
import ua.com.foxminded.task.service.SubjectService;

@Component
public class SubjectCommand implements Command {

    @Autowired
    private SubjectService subjectService;

    @Override
    public boolean check(String fieldId, String fieldUnique) {
        boolean result = true;
        Subject objectExist = subjectService.findByTitle(fieldUnique);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(fieldId));
        }
        return result;
    }

}

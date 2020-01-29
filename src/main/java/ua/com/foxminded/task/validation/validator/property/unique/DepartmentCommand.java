package ua.com.foxminded.task.validation.validator.property.unique;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.service.DepartmentService;

@Component
public class DepartmentCommand implements Command {

    @Autowired
    private DepartmentService departmentService;

    @Override
    public boolean check(String fieldId, String fieldUnique) {
        boolean result = true;
        Department objectExist = departmentService.findByTitle(fieldUnique);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(fieldId));
        }
        return result;
    }

}

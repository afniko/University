package ua.com.foxminded.task.validation.validator.property.unique;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.service.GroupService;

@Component
public class GroupCommand implements Command {

    @Autowired
    private GroupService groupService;

    @Override
    public boolean check(String fieldId, String fieldUnique) {
        boolean result = true;
        Group objectExist = groupService.findByTitle(fieldUnique);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(fieldId));
        }
        return result;
    }

}

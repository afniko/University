package ua.com.foxminded.task.validation.validator.property.unique.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.AuditoryType;
import ua.com.foxminded.task.service.AuditoryTypeService;
import ua.com.foxminded.task.validation.validator.property.unique.Command;

@Component
public class AuditoryTypeCommand implements Command {

    @Autowired
    private AuditoryTypeService auditoryTypeService;

    @Override
    public boolean check(String fieldId, String fieldUnique) {
        boolean result = true;
        AuditoryType objectExist = auditoryTypeService.findByType(fieldUnique);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(fieldId));
        }
        return result;
    }

}

package ua.com.foxminded.task.validation.validator.property.unique;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.service.AuditoryService;

@Component
public class AuditoryCommand implements Command {

    @Autowired
    private AuditoryService auditoryService;

    @Override
    public boolean check(String fieldId, String fieldUnique) {
        boolean result = true;
        Auditory objectExist = auditoryService.findByAuditoryNumber(fieldUnique);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(fieldId));
        }
        return result;
    }

}

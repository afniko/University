package ua.com.foxminded.task.validation.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.AuditoryDto;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.AuditoryService;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.validation.annotation.PropertyValueUnique;

public class PropertyUniqueValidator implements ConstraintValidator<PropertyValueUnique, Object> {

    private String message;
    private String fieldError;
    private String nameProperty;

    @Autowired
    private AuditoryService auditoryService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private Logger logger;

    @Override
    public void initialize(PropertyValueUnique annotation) {
        this.message = annotation.message();
        this.fieldError = annotation.fieldError();
        this.nameProperty = annotation.nameProperty();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean result = true;
        String uniqueField;
        String idField;

        try {
            uniqueField = BeanUtils.getProperty(value, nameProperty);
            idField = BeanUtils.getProperty(value, "id");

            if (value instanceof AuditoryDto) {
                result = checkAuditory(idField, uniqueField);
            }
            if (value instanceof GroupDto) {
                result = checkGroup(idField, uniqueField);
            }

            if (!result) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldError).addConstraintViolation();
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.debug("isValid() [Object:{}]", value);
        }
        return result;
    }

    private boolean checkAuditory(String idField, String uniqueField) {
        boolean result = true;
        Auditory auditoryExist = auditoryService.findByAuditoryNumber(uniqueField);
        if (!Objects.isNull(auditoryExist)) {
            result = (auditoryExist.getId() == Integer.valueOf(idField));
        }
        return result;
    }

    private boolean checkGroup(String idField, String uniqueField) {
        boolean result = true;
        Group groupExist = groupService.findByTitle(uniqueField);
        if (!Objects.isNull(groupExist)) {
            result = (groupExist.getId() == Integer.valueOf(idField));
        }
        return result;
    }

}

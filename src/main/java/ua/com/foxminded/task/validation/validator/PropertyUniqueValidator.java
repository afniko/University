package ua.com.foxminded.task.validation.validator;

import static java.util.Objects.nonNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ua.com.foxminded.task.validation.annotation.PropertyValueUnique;
import ua.com.foxminded.task.validation.validator.property.unique.Command;

public class PropertyUniqueValidator implements ConstraintValidator<PropertyValueUnique, Object> {

    private String message;
    private String fieldError;
    private String nameProperty;

    @Autowired
    @Qualifier("uniqueValidationCommandMap")
    private Map<String, Command> uniqueValidationCommandMap;
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
            Command command = uniqueValidationCommandMap.get(value.getClass().getName());
            if (nonNull(command)) {
                result = command.check(idField, uniqueField);
            } else {
                throw new IllegalAccessException("isValid() Annotation not usabilety for this entity!");
            }

            if (!result) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldError).addConstraintViolation();
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.debug("isValid() [Object:{}], {}", value, e);
        }
        return result;
    }

}

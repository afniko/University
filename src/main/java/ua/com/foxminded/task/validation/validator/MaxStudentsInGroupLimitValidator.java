package ua.com.foxminded.task.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.foxminded.task.dao.StudentRepository;
import ua.com.foxminded.task.validation.annotation.MaxStudentsInGroupLimit;

public class MaxStudentsInGroupLimitValidator implements ConstraintValidator<MaxStudentsInGroupLimit, String> {

    private int value;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void initialize(MaxStudentsInGroupLimit constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String idGroup, ConstraintValidatorContext context) {
        boolean result = true;
        if (StringUtils.isNotBlank(idGroup)) {
            int id = Integer.parseInt(idGroup);
            long countStudentsInGroup = studentRepository.countByGroupId(id);
            if (value <= countStudentsInGroup) {
                result = false;
            }
        }
        return result;
    }

}

package ua.com.foxminded.task.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.foxminded.task.dao.GroupRepository;
import ua.com.foxminded.task.validator.GroupTitleUnique;

public class GroupTitleUniqueValidator implements ConstraintValidator<GroupTitleUnique, String> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !groupRepository.existsByTitle(value);
    }

}

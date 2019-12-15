package ua.com.foxminded.task.validator.constraint;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.foxminded.task.dao.GroupRepository;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.validator.GroupTitleUnique;

public class GroupTitleUniqueValidator implements ConstraintValidator<GroupTitleUnique, GroupDto> {

    private String message;
    private String fieldName;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void initialize(GroupTitleUnique constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(GroupDto groupDto, ConstraintValidatorContext context) {
        String title = groupDto.getTitle();
        Group groupExist = groupRepository.findByTitle(title);
        boolean result = true;
        if (!Objects.isNull(groupExist)) {
            result = (groupExist.getId() == groupDto.getId());
        }
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldName).addConstraintViolation();
        }
        return result;
    }

}

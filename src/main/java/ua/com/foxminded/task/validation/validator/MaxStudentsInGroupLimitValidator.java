package ua.com.foxminded.task.validation.validator;

import java.util.Objects;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.validation.annotation.MaxStudentsInGroupLimit;

public class MaxStudentsInGroupLimitValidator implements ConstraintValidator<MaxStudentsInGroupLimit, StudentDto> {

    private String message;
    private String fieldName;

    @Resource
    private Environment env;

    @Autowired
    private StudentService studentService;

    @Override
    public void initialize(MaxStudentsInGroupLimit constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(StudentDto studentDto, ConstraintValidatorContext context) {
        boolean result = true;
        String idGroup = studentDto.getIdGroup();

        if (StringUtils.isNotBlank(idGroup)) {
            String limit = env.getRequiredProperty("annotation.validator.maxstudentingrouplimit");
            int limitInt = Integer.parseInt(limit);
            int idGroupInt = Integer.parseInt(idGroup);
            long countStudentsInGroup = studentService.countByGroupId(idGroupInt);

            if (countStudentsInGroup >= limitInt) {
                int studentId = studentDto.getId();

                if (studentId != 0) {
                    StudentDto studentExisting = studentService.findByIdDto(studentId);
                    String idGroupExistingStudent = studentExisting.getIdGroup();
                    if (Objects.nonNull(idGroupExistingStudent)) {
                        if (!idGroup.equals(idGroupExistingStudent)) {
                            result = false;
                        }

                    } else {
                        result = false;
                    }

                } else {
                    result = false;
                }
            }
        }

        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldName).addConstraintViolation();
        }
        return result;
    }

}

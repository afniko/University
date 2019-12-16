package ua.com.foxminded.task.validation.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.validation.annotation.StudentIdFeesUnique;

public class StudentIdFeesUniqueValidator implements ConstraintValidator<StudentIdFeesUnique, StudentDto> {
    
    private String message;
    private String fieldName;

    @Autowired
    private StudentService studentService;

    @Override
    public void initialize(StudentIdFeesUnique constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(StudentDto studentDto, ConstraintValidatorContext context) {
        int idFees = studentDto.getIdFees();
        boolean result = true;
        Student studentExisting = studentService.findByIdFees(idFees);
        if (!Objects.isNull(studentExisting)) {
            result = (studentDto.getId() == studentExisting.getId());
        }
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldName).addConstraintViolation();
        }
        return result;
    }

}

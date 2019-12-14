package ua.com.foxminded.task.validator.constraint;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.foxminded.task.dao.StudentRepository;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.validator.StudentIdFeesUnique;

public class StudentIdFeesUniqueValidator implements ConstraintValidator<StudentIdFeesUnique, StudentDto> {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public boolean isValid(StudentDto studentDto, ConstraintValidatorContext context) {
        int idFees = studentDto.getIdFees();
        boolean result = true;
        Student studentExisting = studentRepository.findByIdFees(idFees);
        if (!Objects.isNull(studentExisting)) {
            result = (studentDto.getId() == studentExisting.getId());
        }
        return result;
    }

}

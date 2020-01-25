package ua.com.foxminded.task.validation.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.AuditoryType;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Faculty;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Lecture;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.AuditoryDto;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.AuditoryService;
import ua.com.foxminded.task.service.AuditoryTypeService;
import ua.com.foxminded.task.service.DepartmentService;
import ua.com.foxminded.task.service.FacultyService;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.LectureService;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.SubjectService;
import ua.com.foxminded.task.service.TeacherService;
import ua.com.foxminded.task.validation.annotation.PropertyValueUnique;

public class PropertyUniqueValidator implements ConstraintValidator<PropertyValueUnique, Object> {

    private String message;
    private String fieldError;
    private String nameProperty;

    @Autowired
    private AuditoryService auditoryService;
    @Autowired
    private AuditoryTypeService auditoryTypeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TeacherService teacherService;
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
        Auditory objectExist = auditoryService.findByAuditoryNumber(uniqueField);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(idField));
        }
        return result;
    }

    private boolean checkAuditoryType(String idField, String uniqueField) {
        boolean result = true;
        AuditoryType objectExist = auditoryTypeService.findByType(uniqueField);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(idField));
        }
        return result;
    }

    private boolean checkDepartment(String idField, String uniqueField) {
        boolean result = true;
        Department objectExist = departmentService.findByTitle(uniqueField); 
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(idField));
        }
        return result;
    }

    private boolean checkFaculty(String idField, String uniqueField) {
        boolean result = true;
        Faculty objectExist = facultyService.findByTitle(uniqueField);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(idField));
        }
        return result;
    }

    private boolean checkGroup(String idField, String uniqueField) {
        boolean result = true;
        Group objectExist = groupService.findByTitle(uniqueField);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(idField));
        }
        return result;
    }

    private boolean checkLecture(String idField, String uniqueField) {
        boolean result = true;
        Lecture objectExist = lectureService.findByNumber(uniqueField);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(idField));
        }
        return result;
    }

    private boolean checkStudent(String idField, String uniqueField) {
        boolean result = true;
        Student objectExist = studentService.findByIdFees(Integer.valueOf(uniqueField));
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(idField));
        }
        return result;
    }

//    private boolean checkSubject(String idField, String uniqueField) {
//        boolean result = true;
//        Subject objectExist = subjectRepository
//        if (!Objects.isNull(objectExist)) {
//            result = (objectExist.getId() == Integer.valueOf(idField));
//        }
//        return result;
//    }

    private boolean checkTeacher(String idField, String uniqueField) {
        boolean result = true;
        AuditoryType objectExist = auditoryTypeService.findByType(uniqueField);
        if (!Objects.isNull(objectExist)) {
            result = (objectExist.getId() == Integer.valueOf(idField));
        }
        return result;
    }

}

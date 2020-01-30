package ua.com.foxminded.task.validation.validator.property.unique;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.com.foxminded.task.domain.dto.AuditoryDto;
import ua.com.foxminded.task.domain.dto.AuditoryTypeDto;
import ua.com.foxminded.task.domain.dto.DepartmentDto;
import ua.com.foxminded.task.domain.dto.FacultyDto;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.LectureDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.dto.SubjectDto;
import ua.com.foxminded.task.domain.dto.TeacherDto;
import ua.com.foxminded.task.validation.validator.property.unique.impl.AuditoryCommand;
import ua.com.foxminded.task.validation.validator.property.unique.impl.AuditoryTypeCommand;
import ua.com.foxminded.task.validation.validator.property.unique.impl.DepartmentCommand;
import ua.com.foxminded.task.validation.validator.property.unique.impl.FacultyCommand;
import ua.com.foxminded.task.validation.validator.property.unique.impl.GroupCommand;
import ua.com.foxminded.task.validation.validator.property.unique.impl.LectureCommand;
import ua.com.foxminded.task.validation.validator.property.unique.impl.StudentCommand;
import ua.com.foxminded.task.validation.validator.property.unique.impl.TeacherCommand;

@Configuration
public class Switcher {

    private Map<String, Command> uniqueValidationCommandMap;

    @Autowired
    private AuditoryCommand auditoryCommand;
    @Autowired
    private AuditoryTypeCommand auditoryTypeCommand;
    @Autowired
    private DepartmentCommand departmentCommand;
    @Autowired
    private FacultyCommand facultyCommand;
    @Autowired
    private GroupCommand groupCommand;
    @Autowired
    private LectureCommand lectureCommand;
    @Autowired
    private StudentCommand studentCommand;
    @Autowired
    private SubjectCommand subjectCommand;
    @Autowired
    private TeacherCommand teacherCommand;

    @Bean
    @Qualifier("uniqueValidationCommandMap")
    public Map<String, Command> getUniqueValidationCommandMap() {
        uniqueValidationCommandMap = new HashMap<>();
        uniqueValidationCommandMap.put(AuditoryDto.class.getName(), auditoryCommand);
        uniqueValidationCommandMap.put(AuditoryTypeDto.class.getName(), auditoryTypeCommand);
        uniqueValidationCommandMap.put(DepartmentDto.class.getName(), departmentCommand);
        uniqueValidationCommandMap.put(FacultyDto.class.getName(), facultyCommand);
        uniqueValidationCommandMap.put(GroupDto.class.getName(), groupCommand);
        uniqueValidationCommandMap.put(LectureDto.class.getName(), lectureCommand);
        uniqueValidationCommandMap.put(StudentDto.class.getName(), studentCommand);
        uniqueValidationCommandMap.put(SubjectDto.class.getName(), subjectCommand);
        uniqueValidationCommandMap.put(TeacherDto.class.getName(), teacherCommand);
        return uniqueValidationCommandMap;
    }

}

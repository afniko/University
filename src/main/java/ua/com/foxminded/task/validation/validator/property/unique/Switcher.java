package ua.com.foxminded.task.validation.validator.property.unique;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.dto.AuditoryDto;
import ua.com.foxminded.task.domain.dto.AuditoryTypeDto;
import ua.com.foxminded.task.domain.dto.DepartmentDto;
import ua.com.foxminded.task.domain.dto.FacultyDto;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.LectureDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.dto.SubjectDto;
import ua.com.foxminded.task.domain.dto.TeacherDto;

@Component
public class Switcher {

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

    private Map<String, Command> commandMap = new HashMap<>();

    public Map<String, Command> getCommandMap() {
        commandMap.put(AuditoryDto.class.getName(), auditoryCommand);
        commandMap.put(AuditoryTypeDto.class.getName(), auditoryTypeCommand);
        commandMap.put(DepartmentDto.class.getName(), departmentCommand);
        commandMap.put(FacultyDto.class.getName(), facultyCommand);
        commandMap.put(GroupDto.class.getName(), groupCommand);
        commandMap.put(LectureDto.class.getName(), lectureCommand);
        commandMap.put(StudentDto.class.getName(), studentCommand);
        commandMap.put(SubjectDto.class.getName(), subjectCommand);
        commandMap.put(TeacherDto.class.getName(), teacherCommand);
        return commandMap;
    }

    public void setCommandMap(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

}

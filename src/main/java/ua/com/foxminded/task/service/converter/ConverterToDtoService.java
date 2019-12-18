package ua.com.foxminded.task.service.converter;

import static java.util.Objects.nonNull;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;

public final class ConverterToDtoService {

    private ConverterToDtoService() {
    }

    public static StudentDto convert(Student student) {
        StudentDto studentDto = new StudentDto();
        if (nonNull(student.getId())) {
            studentDto.setId(student.getId());
        }
        studentDto.setFirstName(student.getFirstName());
        studentDto.setMiddleName(student.getMiddleName());
        studentDto.setLastName(student.getLastName());
        studentDto.setBirthday(student.getBirthday());
        studentDto.setIdFees(student.getIdFees());
        if (nonNull(student.getGroup())) {
            studentDto.setIdGroup(student.getGroup().getId());
            studentDto.setGroupTitle(student.getGroup().getTitle());
        }
        return studentDto;
    }

    public static GroupDto convert(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setTitle(group.getTitle());
        groupDto.setYearEntry(group.getYearEntry());
        return groupDto;
    }
}

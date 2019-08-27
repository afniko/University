package ua.com.foxminded.task.service.converter;

import java.util.Objects;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;

public final class ConverterFromDtoService {

    private ConverterFromDtoService() {
    }

    public static Student convert(StudentDto studentDto) {
        Student student = new Student();
        Group group = null;
        if (Objects.nonNull(studentDto.getId())) {
            student.setId(studentDto.getId());
        }
        student.setFirstName(studentDto.getFirstName());
        student.setMiddleName(studentDto.getMiddleName());
        student.setLastName(studentDto.getLastName());
        student.setBirthday(studentDto.getBirthday());
        student.setIdFees(studentDto.getIdFees());
        if (Objects.nonNull(studentDto.getIdGroup())) {
            group = new Group();
            group.setId(studentDto.getId());
        }
        if (Objects.nonNull(studentDto.getGroupTitle())) {
            group.setTitle(studentDto.getGroupTitle());
        }
        student.setGroup(group);
        return student;
    }

    public static Group convert(GroupDto groupDto) {
        Group group = new Group();
        if (Objects.nonNull(groupDto.getId())) {
            group.setId(groupDto.getId());
        }
        group.setTitle(groupDto.getTitle());
        group.setYearEntry(groupDto.getYearEntry());
        return group;
    }
}

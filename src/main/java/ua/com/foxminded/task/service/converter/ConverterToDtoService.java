package ua.com.foxminded.task.service.converter;

import java.util.Objects;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;

public final class ConverterToDtoService {

    private ConverterToDtoService() {
    }

    public static StudentDto convert(Student student) {
        StudentDto studentDto = new StudentDto();
        if (Objects.nonNull(student.getId())) {
            studentDto.setId(student.getId());
        }
        studentDto.setFirstName(student.getFirstName());
        studentDto.setMiddleName(student.getMiddleName());
        studentDto.setLastName(student.getLastName());
        studentDto.setBirthday(student.getBirthday().toLocalDate());
        studentDto.setIdFees(student.getIdFees());
        if (Objects.nonNull(student.getGroup())) {
            int idGroup = student.getGroup().getId();
            studentDto.setIdGroup(String.valueOf(idGroup));
            studentDto.setGroupTitle(student.getGroup().getTitle());
        }
        return studentDto;
    }

    public static GroupDto convert(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setTitle(group.getTitle());
        groupDto.setYearEntry(group.getYearEntry().getYear());
        return groupDto;
    }
}

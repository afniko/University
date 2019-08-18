package ua.com.foxminded.task.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.StudentModelRepository;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;

@RunWith(JUnitPlatform.class)
public class ConverterToDtoServiceTest {

    @Test
    void WhenConvertStudent_thenRetriveStudentDtoObject() {
        Student student = StudentModelRepository.getModel1();
        StudentDto studentExpected = StudentDtoModelRepository.getModel1();
        StudentDto studentActual = ConverterToDtoService.convert(student);
        assertEquals(studentExpected, studentActual);
    }

    @Test
    void WhenConvertGroup_thenRetriveGroupDtoObject() {
        Group group = GroupModelRepository.getModel1();
        GroupDto groupExpected = GroupDtoModelRepository.getModel1();
        GroupDto groupActual = ConverterToDtoService.convert(group);
        assertEquals(groupExpected, groupActual);
    }
}

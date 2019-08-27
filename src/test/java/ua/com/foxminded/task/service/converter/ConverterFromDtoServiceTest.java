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
public class ConverterFromDtoServiceTest {

    @Test
    void WhenConvertStudentDto_thenRetriveStudentObject() {
        Student studentExpected = StudentModelRepository.getModel1();
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        Student studentActual = ConverterFromDtoService.convert(studentDto);
        assertEquals(studentExpected.getFirstName(), studentActual.getFirstName());
        assertEquals(studentExpected.getMiddleName(), studentActual.getMiddleName());
        assertEquals(studentExpected.getLastName(), studentActual.getLastName());
        assertEquals(studentExpected.getBirthday(), studentActual.getBirthday());
        assertEquals(studentExpected.getIdFees(), studentActual.getIdFees());
        assertEquals(studentExpected.getGroup().getTitle(), studentActual.getGroup().getTitle());
    }

    @Test
    void WhenConvertGroupDto_thenRetriveGroupObject() {
        Group groupExpected = GroupModelRepository.getModel1();
        GroupDto group = GroupDtoModelRepository.getModel1();
        Group groupActual = ConverterFromDtoService.convert(group);
        assertEquals(groupExpected, groupActual);
    }
}

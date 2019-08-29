package ua.com.foxminded.task.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.StudentModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;

@RunWith(JUnitPlatform.class)
public class StudentServiceImplTest {
    private StudentDao studentDao = mock(StudentDaoImpl.class);
    private StudentServiceImpl studentService = new StudentServiceImpl(studentDao);

    @Test
    void whenFindById_thenFindStudentAndConvertItToDto() {
        Student student = StudentModelRepository.getModel1();
        StudentDto studentDtoExpected = StudentDtoModelRepository.getModel1();
        doReturn(student).when(studentDao).findById(1);

        StudentDto studentDtoActually = studentService.findByIdDto(1);

        verify(studentDao, times(1)).findById(any(Integer.class));
        assertEquals(studentDtoExpected, studentDtoActually);
    }

    @Test
    void whenFindByAll_thenFindStudentsAndConvertItToDto() {
        List<Student> students = StudentModelRepository.getModels1();
        List<StudentDto> studentDtosExpected = StudentDtoModelRepository.getModels1();
        doReturn(students).when(studentDao).findAll();

        List<StudentDto> studentDtosActually = studentService.findAllDto();

        verify(studentDao, times(1)).findAll();
        assertEquals(studentDtosExpected, studentDtosActually);
    }

//    @Test
    void whenCreate_thenInvocCreateDaoClass() {
        Student studentConverted = StudentModelRepository.getModel1();
        Student studentExpected = StudentModelRepository.getModel2();
        StudentDto studentInput = StudentDtoModelRepository.getModel1();
        doReturn(studentExpected).when(studentDao).create(studentConverted);

        studentService.create(studentInput);

        verify(studentDao, times(1)).create(studentConverted);
    }

//    @Test
    void whenUpdate_thenInvocUpdateDaoClass() {
        Student student = StudentModelRepository.getModel1();
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        doReturn(student).when(studentDao).update(student);

         studentService.update(studentDto);

        verify(studentDao, times(1)).update(student);
    }

}

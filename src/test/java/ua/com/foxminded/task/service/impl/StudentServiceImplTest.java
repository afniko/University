package ua.com.foxminded.task.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.hibernate.GroupDaoImpl;
import ua.com.foxminded.task.dao.impl.hibernate.StudentDaoImpl;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.StudentModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;
import ua.com.foxminded.task.service.impl.StudentServiceImpl;

public class StudentServiceImplTest {
    private StudentDao studentDao = mock(StudentDaoImpl.class);
    private GroupDao groupDao = mock(GroupDaoImpl.class);
    private StudentServiceImpl studentService = new StudentServiceImpl(studentDao, groupDao);

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

    @Test
    void whenCreate_thenInvocCreateDaoClass() {
        Student student = StudentModelRepository.getModel1();
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        doReturn(student).when(studentDao).create(student);
        doReturn(student.getGroup()).when(groupDao).findById(student.getGroup().getId());

        StudentDto studentDtoActually = studentService.create(studentDto);

        verify(studentDao, times(1)).create(student);
        verify(groupDao, times(1)).findById(student.getGroup().getId());
        assertEquals(studentDto, studentDtoActually);
    }

    @Test
    void whenUpdate_thenInvocUpdateDaoClass() {
        Student student = StudentModelRepository.getModel1();
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        studentDto.setId(1);
        student.setId(1);
        doReturn(student).when(studentDao).update(student);
        doReturn(student).when(studentDao).findById(student.getId());
        doReturn(student.getGroup()).when(groupDao).findById(student.getGroup().getId());

        StudentDto studentDtoActually = studentService.update(studentDto);

        verify(studentDao, times(1)).update(student);
        verify(studentDao, times(1)).findById(student.getId());
        verify(groupDao, times(1)).findById(student.getGroup().getId());
        assertEquals(studentDto, studentDtoActually);
    }

}

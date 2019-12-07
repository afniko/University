package ua.com.foxminded.task.service.impl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.GroupRepository;
import ua.com.foxminded.task.dao.StudentRepository;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.StudentModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;


public class StudentServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private StudentRepository studentRepository = mock(StudentRepository.class);
    private GroupRepository groupRepository = mock(GroupRepository.class);
    private StudentServiceImpl studentService = new StudentServiceImpl(logger, studentRepository, groupRepository);

    @Test
    void whenFindById_thenFindStudentAndConvertItToDto() {
        Student student = StudentModelRepository.getModel1();
        StudentDto studentDtoExpected = StudentDtoModelRepository.getModel1();
        doReturn(student).when(studentRepository).getOne(1);

        StudentDto studentDtoActually = studentService.findByIdDto(1);

        verify(studentRepository, times(1)).getOne(any(Integer.class));
        assertEquals(studentDtoExpected, studentDtoActually);
    }

    @Test
    void whenFindByAll_thenFindStudentsAndConvertItToDto() {
        List<Student> students = StudentModelRepository.getModels1();
        List<StudentDto> studentDtosExpected = StudentDtoModelRepository.getModels1();
        doReturn(students).when(studentRepository).findAll();

        List<StudentDto> studentDtosActually = studentService.findAllDto();

        verify(studentRepository, times(1)).findAll();
        assertEquals(studentDtosExpected, studentDtosActually);
    }

    @Test
    void whenCreate_thenInvocCreateDaoClass() {
        Student student = StudentModelRepository.getModel1();
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        doReturn(student).when(studentRepository).saveAndFlush(student);
        doReturn(student.getGroup()).when(groupRepository).getOne(student.getGroup().getId());

        StudentDto studentDtoActually = studentService.create(studentDto);

        verify(studentRepository, times(1)).saveAndFlush(student);
        verify(groupRepository, times(1)).getOne(student.getGroup().getId());
        assertEquals(studentDto, studentDtoActually);
    }

    @Test
    void whenUpdate_thenInvocUpdateDaoClass() {
        Student student = StudentModelRepository.getModel1();
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        studentDto.setId(1);
        student.setId(1);
        doReturn(student).when(studentRepository).saveAndFlush(student);
        doReturn(true).when(studentRepository).existsById(studentDto.getId());
        doReturn(student).when(studentRepository).getOne(student.getId());
        doReturn(student.getGroup()).when(groupRepository).getOne(student.getGroup().getId());

        StudentDto studentDtoActually = studentService.update(studentDto);

        verify(studentRepository, times(1)).saveAndFlush(student);
        verify(studentRepository, times(1)).getOne(student.getId());
        verify(groupRepository, times(1)).getOne(student.getGroup().getId());
        assertEquals(studentDto, studentDtoActually);
    }

}

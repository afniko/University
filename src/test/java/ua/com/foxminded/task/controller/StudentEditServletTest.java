package ua.com.foxminded.task.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;

@RunWith(JUnitPlatform.class)
public class StudentEditServletTest {

    @Test
    public void whenPutAtRequestStudentWithoutId_thenCreateRecord() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
        GroupService groupService = mock(GroupService.class);
        StudentService studentService = mock(StudentService.class);
        StudentEditServlet studentEditServlet = new StudentEditServlet(studentService, groupService);

        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        StudentDto studentDtoExpected = StudentDtoModelRepository.getModel1();
        String successMessageExpected = "Record student was created";

        when(request.getParameter("id")).thenReturn(String.valueOf(studentDto.getId()));
        when(request.getParameter("first_name")).thenReturn(studentDto.getFirstName());
        when(request.getParameter("middle_name")).thenReturn(studentDto.getMiddleName());
        when(request.getParameter("last_name")).thenReturn(studentDto.getLastName());
        when(request.getParameter("birthday")).thenReturn(studentDto.getBirthday().toString());
        when(request.getParameter("idFees")).thenReturn(String.valueOf(studentDto.getIdFees()));
        when(request.getParameter("id_group")).thenReturn(studentDto.getIdGroup());

        when(studentService.create(studentDto)).thenReturn(studentDtoExpected);
        when(request.getRequestDispatcher("student.jsp")).thenReturn(mockDispatcher);

        studentEditServlet.doPost(request, response);
        verify(studentService, times(1)).create(any(StudentDto.class));
        verify(request, times(1)).setAttribute("student", studentDtoExpected);
        verify(request).setAttribute("successMessage", successMessageExpected);
    }

    @Test
    public void whenPutAtRequestStudentWithId_thenUpdateRecord() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
        GroupService groupService = mock(GroupService.class);
        StudentService studentService = mock(StudentService.class);
        StudentEditServlet studentEditServlet = new StudentEditServlet(studentService, groupService);

        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        studentDto.setId(1);
        StudentDto studentDtoExpected = StudentDtoModelRepository.getModel1();
        String successMessageExpected = "Record student was updated!";

        when(request.getParameter("id")).thenReturn(String.valueOf(studentDto.getId()));
        when(request.getParameter("first_name")).thenReturn(studentDto.getFirstName());
        when(request.getParameter("middle_name")).thenReturn(studentDto.getMiddleName());
        when(request.getParameter("last_name")).thenReturn(studentDto.getLastName());
        when(request.getParameter("birthday")).thenReturn(studentDto.getBirthday().toString());
        when(request.getParameter("idFees")).thenReturn(String.valueOf(studentDto.getIdFees()));
        when(request.getParameter("id_group")).thenReturn(studentDto.getIdGroup());

        when(studentService.update(studentDto)).thenReturn(studentDtoExpected);
        when(request.getRequestDispatcher("student.jsp")).thenReturn(mockDispatcher);

        studentEditServlet.doPost(request, response);
        verify(studentService, times(1)).update(any(StudentDto.class));
        verify(request, times(1)).setAttribute("student", studentDtoExpected);
        verify(request).setAttribute("successMessage", successMessageExpected);
    }
}

package ua.com.foxminded.task.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;
import ua.com.foxminded.task.service.StudentService;

public class StudentsServletTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
    private StudentService studentService = mock(StudentService.class);
//    private StudentsServlet studentsServlet = new StudentsServlet(studentService);

//    @Test
    public void whenRequestGet_thenOpenStudentsViewPage() throws ServletException, IOException {
        List<StudentDto> students = StudentDtoModelRepository.getModels();
        when(studentService.findAllDto()).thenReturn(students);
        when(request.getRequestDispatcher("student/students.jsp")).thenReturn(mockDispatcher);

//        studentsServlet.doGet(request, response);
        verify(studentService, times(1)).findAllDto();
        verify(request, times(1)).setAttribute("students", students);
    }
}

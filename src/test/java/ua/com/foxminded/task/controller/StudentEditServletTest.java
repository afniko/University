package ua.com.foxminded.task.controller;

import static org.mockito.ArgumentMatchers.any;
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

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;

public class StudentEditServletTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
    private GroupService groupService = mock(GroupService.class);
    private StudentService studentService = mock(StudentService.class);
//    private StudentEditServlet studentEditServlet = new StudentEditServlet(studentService, groupService);

//    @Test
    public void whenPutAtRequestGetPatametrId_thenOpenStudentEditPage() throws ServletException, IOException {
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        List<GroupDto> groups = GroupDtoModelRepository.getModels();
        when(request.getParameter("id")).thenReturn(String.valueOf(studentDto.getId()));
        when(studentService.findByIdDto(studentDto.getId())).thenReturn(studentDto);
        when(groupService.findAllDto()).thenReturn(groups);
        when(request.getRequestDispatcher("student_edit.jsp")).thenReturn(mockDispatcher);

//        studentEditServlet.doGet(request, response);
        verify(studentService, times(1)).findByIdDto(studentDto.getId());
        verify(groupService, times(1)).findAllDto();
        verify(request, times(1)).setAttribute("student", studentDto);
        verify(request, times(1)).setAttribute("groups", groups);
    }

//    @Test
    public void whenPutAtRequestStudentWithoutId_thenCreateRecord() throws ServletException, IOException {
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

//        studentEditServlet.doPost(request, response);
        verify(studentService, times(1)).create(any(StudentDto.class));
        verify(request, times(1)).setAttribute("student", studentDtoExpected);
        verify(request).setAttribute("successMessage", successMessageExpected);
    }

//    @Test
    public void whenPutAtRequestStudentWithId_thenUpdateRecord() throws ServletException, IOException {
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

//        studentEditServlet.doPost(request, response);
        verify(studentService, times(1)).update(any(StudentDto.class));
        verify(request, times(1)).setAttribute("student", studentDtoExpected);
        verify(request).setAttribute("successMessage", successMessageExpected);
    }
}

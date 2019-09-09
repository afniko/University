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

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;

@RunWith(JUnitPlatform.class)
public class StudentServletTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
    private GroupService groupService = mock(GroupService.class);
    private StudentService studentService = mock(StudentService.class);
    private StudentServlet studentServlet = new StudentServlet(studentService, groupService);

    @Test
    public void whenPutAtRequestGetPatametrId_thenOpenStudentViewPage() throws ServletException, IOException {
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        List<GroupDto> groups = GroupDtoModelRepository.getModels();
        when(request.getParameter("id")).thenReturn(String.valueOf(studentDto.getId()));
        when(studentService.findByIdDto(studentDto.getId())).thenReturn(studentDto);
        when(groupService.findAllDto()).thenReturn(groups);
        when(request.getRequestDispatcher("student/student.jsp")).thenReturn(mockDispatcher);

        studentServlet.doGet(request, response);
        verify(studentService, times(1)).findByIdDto(studentDto.getId());
        verify(groupService, times(1)).findAllDto();
        verify(request, times(1)).setAttribute("student", studentDto);
        verify(request, times(1)).setAttribute("groups", groups);
    }
}

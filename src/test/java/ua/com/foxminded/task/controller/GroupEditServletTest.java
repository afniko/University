package ua.com.foxminded.task.controller;

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

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;

public class GroupEditServletTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
    private GroupService groupService = mock(GroupService.class);
    private GroupEditServlet groupEditServlet = new GroupEditServlet(groupService);

    @Test
    public void whenPutAtRequestGetParametrId_thenOpenGroupEditPage() throws ServletException, IOException {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();

        when(request.getParameter("id")).thenReturn(String.valueOf(groupDto.getId()));
        when(groupService.findByIdDto(groupDto.getId())).thenReturn(groupDto);
        when(request.getRequestDispatcher("group_edit.jsp")).thenReturn(mockDispatcher);

        groupEditServlet.doGet(request, response);
        verify(groupService, times(1)).findByIdDto(groupDto.getId());
        verify(request, times(1)).setAttribute("group", groupDto);
    }

    @Test
    public void whenPutAtRequestGroupWithoutId_thenCreateRecord() throws ServletException, IOException {
        GroupDto groupDto = GroupDtoModelRepository.getModel1();
        GroupDto groupDtoExpected = GroupDtoModelRepository.getModel1();
        String successMessageExpected = "Record group was created!";

        when(request.getParameter("title")).thenReturn(groupDto.getTitle());
        when(request.getParameter("year_entry")).thenReturn(String.valueOf(groupDto.getYearEntry()));
        when(groupService.create(groupDto)).thenReturn(groupDtoExpected);
        when(request.getRequestDispatcher("group.jsp")).thenReturn(mockDispatcher);

        groupEditServlet.doPost(request, response);
        verify(groupService, times(1)).create(groupDto);
        verify(request, times(1)).setAttribute("group", groupDtoExpected);
        verify(request).setAttribute("successMessage", successMessageExpected);
    }

    @Test
    public void whenPutAtRequestGroupWithId_thenUpdateRecord() throws ServletException, IOException {
        GroupDto groupDto = GroupDtoModelRepository.getModel1();
        groupDto.setId(1);
        GroupDto groupDtoExpected = GroupDtoModelRepository.getModel1();
        String successMessageExpected = "Record group was updated!";

        when(request.getParameter("id")).thenReturn(String.valueOf(groupDto.getId()));
        when(request.getParameter("title")).thenReturn(groupDto.getTitle());
        when(request.getParameter("year_entry")).thenReturn(String.valueOf(groupDto.getYearEntry()));
        when(groupService.update(groupDto)).thenReturn(groupDtoExpected);
        when(request.getRequestDispatcher("group.jsp")).thenReturn(mockDispatcher);

        groupEditServlet.doPost(request, response);
        verify(groupService, times(1)).update(groupDto);
        verify(request, times(1)).setAttribute("group", groupDtoExpected);
        verify(request).setAttribute("successMessage", successMessageExpected);
    }

}

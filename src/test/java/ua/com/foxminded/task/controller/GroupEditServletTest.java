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
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;

@RunWith(JUnitPlatform.class)
public class GroupEditServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
    GroupService groupService = mock(GroupService.class);
    GroupEditServlet groupEditServlet = new GroupEditServlet(groupService);

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

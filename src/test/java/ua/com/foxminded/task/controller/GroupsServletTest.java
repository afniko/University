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

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;

public class GroupsServletTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
    private GroupService groupService = mock(GroupService.class);
    private GroupsServlet groupsServlet = new GroupsServlet(groupService);

    @Test
    public void whenPutAtRequestGetParametrId_thenOpenGroupsViewPage() throws ServletException, IOException {
        List<GroupDto> groups = GroupDtoModelRepository.getModels();
        when(groupService.findAllDto()).thenReturn(groups);
        when(request.getRequestDispatcher("group/groups.jsp")).thenReturn(mockDispatcher);

        groupsServlet.doGet(request, response);
        verify(groupService, times(1)).findAllDto();
        verify(request, times(1)).setAttribute("groups", groups);
    }
}

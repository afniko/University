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

public class GroupServletTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
    private GroupService groupService = mock(GroupService.class);
    private GroupServlet groupServlet = new GroupServlet(groupService);
//    private static DataSourceCreater dataSourceCreater;
//
//    @BeforeAll
//    public static void setDataSource() {
//        dataSourceCreater = DataSourceCreater.getInstance();
//        dataSourceCreater.setInitialContext();
//    }
//
//    @AfterAll
//    public static void closeDataSource() {
//        dataSourceCreater.closeInitialContext();
//    }

    @Test
    public void whenPutAtRequestGetParametrId_thenOpenGroupViewPage() throws ServletException, IOException {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();

        when(request.getParameter("id")).thenReturn(String.valueOf(groupDto.getId()));
        when(groupService.findByIdDto(groupDto.getId())).thenReturn(groupDto);
        when(request.getRequestDispatcher("group/group.jsp")).thenReturn(mockDispatcher);

        groupServlet.doGet(request, response);
        verify(groupService, times(1)).findByIdDto(groupDto.getId());
        verify(request, times(1)).setAttribute("group", groupDto);
    }

}

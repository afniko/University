package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@WebServlet(urlPatterns = "/groups")
public class GroupsServlet extends HttpServlet {

    private static final long serialVersionUID = -77541974969140801L;
    private GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        List<GroupDto> groups = null;
        try {
            groups = groupService.findAllDto();
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with group goes wrong!";
        }
        req.setAttribute("groups", groups);
        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("group/groups.jsp").forward(req, resp);
    }
}

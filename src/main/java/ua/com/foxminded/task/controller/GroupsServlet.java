package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.service.GroupService;

@WebServlet(urlPatterns = "/groups")
public class GroupsServlet extends HttpServlet {

    private static final long serialVersionUID = -77541974969140801L;
    private GroupService groupService = new GroupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = null;
        List<Group> groups = null;
        try {
            groups = groupService.findAll();
        } catch (NoExecuteQueryException e) {
            text = "Something with group goes wrong!";
        }
        req.setAttribute("groups", groups);
        req.setAttribute("text", text);
        req.getRequestDispatcher("groups.jsp").forward(req, resp);
    }
}

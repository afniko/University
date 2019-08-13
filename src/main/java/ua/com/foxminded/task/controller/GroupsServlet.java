package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;

@WebServlet(urlPatterns = "/groups")
public class GroupsServlet extends HttpServlet {

    private static final long serialVersionUID = -77541974969140801L;

    private static GroupDao groupDao = new GroupDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = null;
        List<Group> groups = null;
        try {
            groups = groupDao.findAll();
        } catch (NoExecuteQueryException e) {
            text = "Something with group goes wrong!";
        } finally {
            req.setAttribute("groups", groups);
            req.setAttribute("text", text);
            req.getRequestDispatcher("groups.jsp").forward(req, resp);
        }
    }
}

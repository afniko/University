package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@WebServlet(urlPatterns = "/group_create")
public class GroupCreateServlet extends HttpServlet {

    private static final long serialVersionUID = -5656956490382779313L;
    private GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Group page");
        req.setAttribute("title_header", "Group create page");
        req.getRequestDispatcher("group_create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        String successMessage = null;
        String title = req.getParameter("title");
        String yearEntry = req.getParameter("year_entry");
        Group group = null;
        if (validateTitle(title) && validateYearEntry(yearEntry)) {
            group = new Group();
            group.setTitle(title);
            group.setYearEntry(Date.valueOf(yearEntry));
            try {
                group = groupService.create(group);
                successMessage = "Record group was created!";
            } catch (NoExecuteQueryException e) {
                errorMessage = "Record group was not created!";
            }
        } else {
            errorMessage = "You enter incorrect data";
        }
        req.setAttribute("group", group);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("successMessage", successMessage);
        req.setAttribute("title", "Group page");
        req.setAttribute("title_header", "Group create page");
        req.getRequestDispatcher("group_create.jsp").forward(req, resp);
    }

    private boolean validateTitle(String title) {
        return StringUtils.isNotBlank(title);
    }

    private boolean validateYearEntry(String yearEntry) {
        String pattern = "^20\\d\\d-\\d\\d-\\d\\d$";
        return yearEntry.matches(pattern);
    }
}

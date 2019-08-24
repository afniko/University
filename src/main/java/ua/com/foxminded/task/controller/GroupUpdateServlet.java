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
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@WebServlet(urlPatterns = "/group_update")
public class GroupUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = 7615789936547001494L;
    private GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        String successMessage = null;

        String id = req.getParameter("id");
        String title = req.getParameter("title");
        String yearEntry = req.getParameter("year_entry");
        GroupDto groupDto = null;

        if (validateTitle(title) 
                && validateYearEntry(yearEntry)) 
        {
            try {
                Group group = new Group();
                group.setId(Integer.valueOf(id));
                group.setTitle(title);
                group.setYearEntry(Date.valueOf(yearEntry));

                groupDto = groupService.update(group);
                successMessage = "Record group was updated!";
            } catch (NoExecuteQueryException e) {
                errorMessage = "Record group was not updated!";
            }
        } else {
            errorMessage = "You enter incorrect data";
        }
        req.setAttribute("group", groupDto);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("successMessage", successMessage);
        req.setAttribute("title", "Group page");
        req.setAttribute("title_header", "Group update list");
        req.getRequestDispatcher("group.jsp").forward(req, resp);
    }

    private boolean validateTitle(String title) {
        return StringUtils.isNotBlank(title);
    }

    private boolean validateYearEntry(String yearEntry) {
        String pattern = "^20\\d\\d-\\d\\d-\\d\\d$";
        return yearEntry.matches(pattern);
    }
}

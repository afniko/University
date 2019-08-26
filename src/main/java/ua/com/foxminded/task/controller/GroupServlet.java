package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@WebServlet(urlPatterns = "/group")
public class GroupServlet extends HttpServlet {

    private static final long serialVersionUID = 4603484417851175285L;
    private GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        GroupDto group = null;
        String idString = req.getParameter("id");
        int id = 0;
        try {
            id = Integer.valueOf(idString);
            if (validateId(idString)) {
                errorMessage = "You id is blank";
            } else {
                group = groupService.findByIdDto(id);
            }
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with group goes wrong!";
        } catch (NoEntityFoundException e) {
            errorMessage = "Group by id#" + id + " not found!";
        } catch (NumberFormatException e) {
            errorMessage = "Group id# must be numeric!";
        }
        req.setAttribute("group", group);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("title", "Group page");
        req.setAttribute("title_header", "Group page");
        req.getRequestDispatcher("group.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
    private boolean validateId(String idString) {
        return StringUtils.isBlank(idString);
    }

}

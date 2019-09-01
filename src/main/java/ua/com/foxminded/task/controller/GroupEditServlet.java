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
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@WebServlet(urlPatterns = "/group/edit")
public class GroupEditServlet extends HttpServlet {

    private static final long serialVersionUID = -5656956490382779313L;
    private GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (checkId(id)) {
            GroupDto group = groupService.findByIdDto(Integer.valueOf(id));
            req.setAttribute("group", group);
        }
        req.getRequestDispatcher("group_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        String successMessage = null;
        String id = req.getParameter("id");

//    TODO    if (validateTitle(title) && validateYearEntry(yearEntry)) {
        GroupDto group = retriveGroup(req);
        try {
            if (checkId(id)) {
                group.setId(Integer.valueOf(id));
                group = groupService.update(group);
                successMessage = "Record group was updated!";
            } else {
                group = groupService.create(group);
                successMessage = "Record group was created!";
            }
        } catch (NoExecuteQueryException e) {
            errorMessage = "Record group was not edited!";
        }
//    TODO        errorMessage = "You enter incorrect data";

        req.setAttribute("group", group);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("successMessage", successMessage);
        req.getRequestDispatcher("group.jsp").forward(req, resp);
    }

    private GroupDto retriveGroup(HttpServletRequest req) {
        String title = req.getParameter("title");
        String yearEntry = req.getParameter("year_entry");
        GroupDto group = null;
        group = new GroupDto();
        group.setTitle(title);
        group.setYearEntry(Date.valueOf(yearEntry));
        return group;
    }

    private boolean checkId(String id) {
        return StringUtils.isNotBlank(id);
    }

    private boolean validateTitle(String title) {
        return StringUtils.isNotBlank(title);
    }

    private boolean validateYearEntry(String yearEntry) {
        String pattern = "^20\\d\\d-\\d\\d-\\d\\d$";
        return yearEntry.matches(pattern);
    }
}

package ua.com.foxminded.task.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@WebServlet(urlPatterns = "/group")
public class GroupServlet extends HttpServlet {

    private static final long serialVersionUID = 4603484417851175285L;
    private GroupService groupService = new GroupServiceImpl();

    public GroupServlet() {
    }

    public GroupServlet(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        GroupDto group = null;
        String idString = req.getParameter("id");
        int id = 0;
        try {
            id = Integer.valueOf(idString);
            if (checkId(idString)) {
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
        req.getRequestDispatcher("group/group.jsp").forward(req, resp);
    }

    private boolean checkId(String idString) {
        return StringUtils.isBlank(idString);
    }

}

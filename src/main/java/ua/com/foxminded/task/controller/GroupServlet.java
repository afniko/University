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

@WebServlet(urlPatterns = "/group")
public class GroupServlet extends HttpServlet {

    private static final long serialVersionUID = 4603484417851175285L;
    private GroupService groupService = new GroupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = null;
        GroupDto group = null;
        String idString = req.getParameter("id");
        int id = 0;
        try {
            id = Integer.valueOf(idString);
            if (StringUtils.isBlank(idString)) {
                text = "You id is blank";
            } else {
                group = groupService.findById(id);
            }
        } catch (NoExecuteQueryException e) {
            text = "Something with group goes wrong!";
        } catch (NoEntityFoundException e) {
            text = "Group by id#" + id + " not found!";
        } catch (NumberFormatException e) {
            text = "Group id# must be numeric!";
        }
        req.setAttribute("group", group);
        req.setAttribute("text", text);
        req.getRequestDispatcher("group.jsp").forward(req, resp);
    }

}

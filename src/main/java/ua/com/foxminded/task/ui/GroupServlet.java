package ua.com.foxminded.task.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;

@WebServlet(urlPatterns = "/group")
public class GroupServlet extends HttpServlet {

    private static final long serialVersionUID = 4603484417851175285L;

    private static GroupDao groupDao = new GroupDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = "Group page";
        List<Group> groups = null;
        String idString = req.getParameter("id");
        try {
            if (StringUtils.isBlank(idString)) {
                groups = groupDao.findAll();
            } else {
                Group group = findGroupById(idString, req, resp);
                groups = new ArrayList<Group>();
                groups.add(group);
            }
        } catch (NoExecuteQueryException e) {
            text = "Something with group goes wrong!";
        } finally {
            req.setAttribute("groups", groups);
            req.setAttribute("text", text);
            req.getRequestDispatcher("group.jsp").forward(req, resp);
        }
    }

    private Group findGroupById(String idString, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Group group = null;
        String text = null;
        int id = Integer.valueOf(idString);
        try {
            group = groupDao.findById(id);
        } catch (NoEntityFoundException e) {
            text = "Group by id#" + id + " not found!";
            req.setAttribute("text", text);
            req.getRequestDispatcher("group.jsp").forward(req, resp);
        }
        return group;
    }

}

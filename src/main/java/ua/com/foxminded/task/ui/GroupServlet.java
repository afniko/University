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
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;

@WebServlet(urlPatterns = "/group")
public class GroupServlet extends HttpServlet {

    private static final long serialVersionUID = 4603484417851175285L;

    private static GroupDao groupDao = new GroupDaoImpl();
    private String text = "Group page";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Group> groups = null;
        String idString = req.getParameter("id");
        if (StringUtils.isBlank(idString)) {
            groups = groupDao.findAll();
        } else {
            int id = Integer.valueOf(idString);
            groups = new ArrayList<Group>();
            Group group = groupDao.findById(id);
            groups.add(group);
        }

        req.setAttribute("groups", groups);
        req.setAttribute("text", text);
        req.getRequestDispatcher("group.jsp").forward(req, resp);
    }

}

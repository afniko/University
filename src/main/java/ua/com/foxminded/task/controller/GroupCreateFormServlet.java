package ua.com.foxminded.task.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/group_create_form")
public class GroupCreateFormServlet extends HttpServlet {

    private static final long serialVersionUID = -4702967700989713078L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Group page");
        req.setAttribute("title_header", "Group create page");
        req.getRequestDispatcher("group_create.jsp").forward(req, resp);
    }
}

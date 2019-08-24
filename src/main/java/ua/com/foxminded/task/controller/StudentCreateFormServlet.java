package ua.com.foxminded.task.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/student_create_form")
public class StudentCreateFormServlet extends HttpServlet {

    private static final long serialVersionUID = 6105248729977952746L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("title", "Student create page");
        req.setAttribute("title_header", "Student page");
        req.getRequestDispatcher("student_create.jsp").forward(req, resp);
    }
}

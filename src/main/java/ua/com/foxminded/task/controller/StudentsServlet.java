package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Student;

@WebServlet(urlPatterns = "/students")
public class StudentsServlet extends HttpServlet {

    private static final long serialVersionUID = -3291272855773910283L;

    private static StudentDao studentDao = new StudentDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = null;
        List<Student> students = null;
        try {
            students = studentDao.findAll();
        } catch (NoExecuteQueryException e) {
            text = "Something with student goes wrong!";
        } finally {
            req.setAttribute("students", students);
            req.setAttribute("text", text);
            req.getRequestDispatcher("students.jsp").forward(req, resp);
        }
    }
}

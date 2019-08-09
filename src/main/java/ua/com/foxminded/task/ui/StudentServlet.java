package ua.com.foxminded.task.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Student;

@WebServlet(urlPatterns = "/student")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = -8107642356833737724L;

    private static StudentDao studentDao = new StudentDaoImpl();
    private String text = "student page";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students = studentDao.findAll();
        req.setAttribute("students", students);
        req.setAttribute("text", text);
        req.getRequestDispatcher("student.jsp").forward(req, resp);
    }

}

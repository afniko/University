package ua.com.foxminded.task.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Student;

@WebServlet(urlPatterns = "/student")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = -8107642356833737724L;

    private static StudentDao studentDao = new StudentDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = null;
        Student student = null;
        try {

            String idString = req.getParameter("id");
            if (StringUtils.isBlank(idString)) {
                text = "You id is blank";
            } else {
                student = findStudentById(idString, req, resp);
            }
        } catch (NoExecuteQueryException e) {
            text = "Something with student goes wrong!";
        } finally {
            req.setAttribute("student", student);
            req.setAttribute("text", text);
            req.getRequestDispatcher("student.jsp").forward(req, resp);
        }
    }

    private Student findStudentById(String idString, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = null;
        String text = null;
        int id = Integer.valueOf(idString);
        try {
            student = studentDao.findById(id);
        } catch (NoEntityFoundException e) {
            text = "Student by id#" + id + " not found!";
            req.setAttribute("text", text);
            req.getRequestDispatcher("student.jsp").forward(req, resp);
        }
        return student;
    }

}

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
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.service.StudentService;

@WebServlet(urlPatterns = "/student")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = -8107642356833737724L;
    private StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = null;
        Student student = null;
        String idString = req.getParameter("id");
        int id = Integer.valueOf(idString);
        try {

            if (StringUtils.isBlank(idString)) {
                text = "You id is blank";
            } else {
                student = studentService.findById(id);
            }
        } catch (NoExecuteQueryException e) {
            text = "Something with student goes wrong!";
        } catch (NoEntityFoundException e) {
            text = "Student by id#" + id + " not found!";
        }
        req.setAttribute("student", student);
        req.setAttribute("text", text);
        req.getRequestDispatcher("student.jsp").forward(req, resp);
    }
}

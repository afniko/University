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
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;

@WebServlet(urlPatterns = "/student")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = -8107642356833737724L;
    private StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        StudentDto student = null;
        String idString = req.getParameter("id");
        int id = 0;
        try {
            id = Integer.valueOf(idString);
            if (StringUtils.isBlank(idString)) {
                errorMessage = "You id is blank";
            } else {
                student = studentService.findById(id);
            }
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with student goes wrong!";
        } catch (NoEntityFoundException e) {
            errorMessage = "Student by id#" + id + " not found!";
        } catch (NumberFormatException e) {
            errorMessage = "Student id# must be numeric!";
        }
        req.setAttribute("student", student);
        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("student.jsp").forward(req, resp);
    }
}

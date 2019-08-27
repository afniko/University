package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.impl.StudentServiceImpl;

@WebServlet(urlPatterns = "/students")
public class StudentsServlet extends HttpServlet {

    private static final long serialVersionUID = -3291272855773910283L;
    private StudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        List<StudentDto> students = null;
        try {
            students = studentService.findAllDto();
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with student goes wrong!";
        }
        req.setAttribute("students", students);
        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("student/students.jsp").forward(req, resp);
    }
}

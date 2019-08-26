package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.impl.StudentServiceImpl;

@WebServlet(urlPatterns = "/student_create")
public class StudentCreateServlet extends HttpServlet {

    private static final long serialVersionUID = -3975386213249523426L;
    private StudentService studentService = new StudentServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("title", "Student create page");
        req.setAttribute("title_header", "Student page");
        req.getRequestDispatcher("student_create.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        String successMessage = null;
        Student student = null;

        String firstName = req.getParameter("first_name");
        String middleName = req.getParameter("middle_name");
        String lastName = req.getParameter("last_name");
        String birthday = req.getParameter("birthday");
        String idFees = req.getParameter("idFees");

        if (validateName(firstName) 
                && validateName(middleName) 
                && validateName(lastName) 
                && validateBirthday(birthday) 
                && validateIdFees(idFees)) {
            student = new Student();
            student.setFirstName(firstName);
            student.setMiddleName(middleName);
            student.setLastName(lastName);
            student.setBirthday(Date.valueOf(birthday));
            student.setIdFees(Integer.valueOf(idFees));
            try {
                student = studentService.create(student);
                successMessage = "Record student was created";
            } catch (NoExecuteQueryException e) {
                errorMessage = "Record student was not created!";
            }
        } else {
            errorMessage = "You enter incorrect data";
        }
        req.setAttribute("student", student);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("successMessage", successMessage);
        req.setAttribute("title", "Student create page");
        req.setAttribute("title_header", "Student page");
        req.getRequestDispatcher("student_create.jsp").forward(req, resp);
    }

    private boolean validateName(String name) {
        return StringUtils.isNotBlank(name);
    }

    private boolean validateBirthday(String birthday) {
        String pattern = "^(19|20)\\d\\d-\\d\\d-\\d\\d$";
        return birthday.matches(pattern);
    }

    private boolean validateIdFees(String idFees) {
        String pattern = "^\\d{9}$";
        return idFees.matches(pattern);
    }
}

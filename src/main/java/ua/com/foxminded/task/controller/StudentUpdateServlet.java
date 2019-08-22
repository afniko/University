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
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.impl.StudentServiceImpl;

@WebServlet(urlPatterns = "/student_update")
public class StudentUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = -6447127612198967964L;
    private StudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        String successMessage = null;
        StudentDto studentDto = null;
        String id = req.getParameter("id");
        String firstName = req.getParameter("first_name");
        String middleName = req.getParameter("middle_name");
        String lastName = req.getParameter("last_name");
        String birthday = req.getParameter("birthday");
        String idFees = req.getParameter("idFees");

        if (validateName(firstName) && validateName(middleName) && validateName(lastName) && validateBirthday(birthday) && validateIdFees(idFees)) {
            try {
                Student student = new Student();
                student.setId(Integer.valueOf(id));
                student.setFirstName(firstName);
                student.setMiddleName(middleName);
                student.setLastName(lastName);
                student.setBirthday(Date.valueOf(birthday));
                student.setIdFees(Integer.valueOf(idFees));
                studentDto = studentService.update(student);
                successMessage = "Record student was updated";
            } catch (NoExecuteQueryException e) {
                errorMessage = "Record student was not updated!";
            }
        } else {
            errorMessage = "You enter incorrect data";
        }
        req.setAttribute("student", studentDto);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("successMessage", successMessage);
        req.getRequestDispatcher("student.jsp").forward(req, resp);
    }

    private boolean validateName(String name) {
        return StringUtils.isNotBlank(name);
    }

    private boolean validateBirthday(String birthday) {
        String pattern = "^(19|20)\\d\\d-\\d\\d-\\d\\d$";
        return birthday.matches(pattern);
    }

    private boolean validateIdFees(String idFees) {
        String pattern = "^\\d{10}$";
        return idFees.matches(pattern);
    }
}

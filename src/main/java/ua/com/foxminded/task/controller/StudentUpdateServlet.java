package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;
import ua.com.foxminded.task.service.impl.StudentServiceImpl;

@WebServlet(urlPatterns = "/student_update")
public class StudentUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = -6447127612198967964L;
    private StudentService studentService = new StudentServiceImpl();
    private GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        String successMessage = null;
        StudentDto studentDto = null;
        List<GroupDto> groups = null;

        String id = req.getParameter("id");
        String firstName = req.getParameter("first_name");
        String middleName = req.getParameter("middle_name");
        String lastName = req.getParameter("last_name");
        String birthday = req.getParameter("birthday");
        String idFees = req.getParameter("idFees");
        String idGroup = req.getParameter("id_group");

        if (validateName(firstName) 
                && validateName(middleName) 
                && validateName(lastName) 
                && validateBirthday(birthday) 
                && validateIdFees(idFees)) 
        {
            try {
                Student student = new Student();
                student.setId(Integer.valueOf(id));
                student.setFirstName(firstName);
                student.setMiddleName(middleName);
                student.setLastName(lastName);
                student.setBirthday(Date.valueOf(birthday));
                student.setIdFees(Integer.valueOf(idFees));
                Group group = retrieveGroupById(idGroup);
                student.setGroup(group);

                studentDto = studentService.update(student);
                groups = groupService.findAllDto();
                successMessage = "Record student was updated!";
            } catch (NoExecuteQueryException e) {
                errorMessage = "Record student was not updated!";
            }
        } else {
            errorMessage = "You enter incorrect data!";
        }
        req.setAttribute("student", studentDto);
        req.setAttribute("groups", groups);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("successMessage", successMessage);
        req.getRequestDispatcher("student.jsp").forward(req, resp);
    }
    
    private Group retrieveGroupById(String idGroup) {
        Group group = null;
        if (!StringUtils.isBlank(idGroup)) {
            if (idGroup.contains("0")) {
                group = new Group();
                group.setId(0);
            } else {
                group = groupService.findById(Integer.valueOf(idGroup));
            }
        }
        return group;
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

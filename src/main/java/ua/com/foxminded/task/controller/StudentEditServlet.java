package ua.com.foxminded.task.controller;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;
import ua.com.foxminded.task.service.impl.StudentServiceImpl;

@WebServlet(urlPatterns = "/student/edit")
public class StudentEditServlet extends HttpServlet {

    private static final long serialVersionUID = -3975386213249523426L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private StudentService studentService = new StudentServiceImpl();
    private GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (checkId(id)) {
            StudentDto student = studentService.findByIdDto(Integer.valueOf(id));
            req.setAttribute("student", student);
        }
        List<GroupDto> groups = groupService.findAllDto();
        req.setAttribute("groups", groups);
        req.getRequestDispatcher("student_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String errorMessage = null;
        String successMessage = null;
        StudentDto studentDto = null;
        List<GroupDto> groups = null;

        studentDto = retriveStudentDto(req);
        Set<ConstraintViolation<StudentDto>> violations = validateStudentDto(studentDto);
        if (violations.size() == 0) {
            try {
                if (studentDto.getId() != 0) {
                    studentDto = studentService.update(studentDto);
                    successMessage = "Record student was updated!";
                } else {
                    studentDto = studentService.create(studentDto);
                    successMessage = "Record student was created";
                }

                groups = groupService.findAllDto();
            } catch (NoExecuteQueryException e) {
                errorMessage = "Record student was not edited!";
            }
        } else {
            errorMessage = "You enter incorrect data!";
            for (ConstraintViolation<StudentDto> violation : violations) {
                errorMessage += " " + violation.getMessage();
            }
        }
        req.setAttribute("student", studentDto);
        req.setAttribute("groups", groups);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("successMessage", successMessage);
        req.getRequestDispatcher("student.jsp").forward(req, resp);
    }

    private StudentDto retriveStudentDto(HttpServletRequest req) {
        LOGGER.debug("retriveStudentDto()");
        String id = req.getParameter("id");
        String firstName = req.getParameter("first_name");
        String middleName = req.getParameter("middle_name");
        String lastName = req.getParameter("last_name");
        String birthday = req.getParameter("birthday");
        String idFees = req.getParameter("idFees");
        String idGroup = req.getParameter("id_group");
        StudentDto studentDto = new StudentDto();
        if (checkId(id)) {
            studentDto.setId(Integer.valueOf(id));
        }
        studentDto.setFirstName(firstName);
        studentDto.setMiddleName(middleName);
        studentDto.setLastName(lastName);
        studentDto.setBirthday(Date.valueOf(birthday));
        studentDto.setIdFees(Integer.valueOf(idFees));
        if (checkId(idGroup)) {
            studentDto.setIdGroup(idGroup);
        } else {
            studentDto.setIdGroup(null);
        }
        return studentDto;
    }

    private boolean checkId(String id) {
        return isNotBlank(id);
    }

    private Set<ConstraintViolation<StudentDto>> validateStudentDto(StudentDto studentDto) {
        LOGGER.debug("validateStudentDto()");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<StudentDto>> violations = validator.validate(studentDto);
        return violations;
    }

}

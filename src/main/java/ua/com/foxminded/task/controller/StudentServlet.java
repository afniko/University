package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;
import ua.com.foxminded.task.service.impl.StudentServiceImpl;

@WebServlet(urlPatterns = "/student")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = -8107642356833737724L;
    private StudentService studentService = new StudentServiceImpl();
    private GroupService groupService = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        StudentDto student = null;
        List<GroupDto> groups = null;
        String idString = req.getParameter("id");
        int id = 0;
        try {
            id = Integer.valueOf(idString);
            if (checkId(idString)) {
                errorMessage = "You id is blank";
            } else {
                student = studentService.findByIdDto(id);
                groups = groupService.findAllDto();
            }
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with student goes wrong!";
        } catch (NoEntityFoundException e) {
            errorMessage = "Student by id#" + id + " not found!";
        } catch (NumberFormatException e) {
            errorMessage = "Student id# must be numeric!";
        }
        req.setAttribute("student", student);
        req.setAttribute("groups", groups);
        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("student/student.jsp").forward(req, resp);
    }
    
    private boolean checkId(String idString) {
        return StringUtils.isBlank(idString);
    }
}

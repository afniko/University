package ua.com.foxminded.task.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Date;
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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@WebServlet(urlPatterns = "/group/edit")
public class GroupEditServlet extends HttpServlet {

    private static final long serialVersionUID = -5656956490382779313L;
    private GroupService groupService = new GroupServiceImpl();
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (checkId(id)) {
            GroupDto group = groupService.findByIdDto(Integer.valueOf(id));
            req.setAttribute("group", group);
        }
        req.getRequestDispatcher("group_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = null;
        String successMessage = null;
        String id = req.getParameter("id");

//    TODO    if (validateTitle(title) && validateYearEntry(yearEntry)) {
        GroupDto group = retriveGroupDto(req);
        try {
            if (checkId(id)) {
                group.setId(Integer.valueOf(id));
                group = groupService.update(group);
                successMessage = "Record group was updated!";
            } else {
                group = groupService.create(group);
                successMessage = "Record group was created!";
            }
        } catch (NoExecuteQueryException e) {
            errorMessage = "Record group was not edited!";
        }
//    TODO        errorMessage = "You enter incorrect data";

        req.setAttribute("group", group);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("successMessage", successMessage);
        req.getRequestDispatcher("group.jsp").forward(req, resp);
    }

    private GroupDto retriveGroupDto(HttpServletRequest req) {
        LOGGER.debug("retriveGroupDto()");
        String title = req.getParameter("title");
        String yearEntry = req.getParameter("year_entry");
        LOGGER.debug("retriveGroupDto starting validate()");
        GroupDto group = new GroupDto();
        group.setTitle(title);
        group.setYearEntry(Date.valueOf(yearEntry));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<GroupDto>> violations = validator.validate(group);

        for (ConstraintViolation<GroupDto> violation : violations) {
            LOGGER.debug("retriveGroupDto() [violation:{}]", violation.getMessage());
        }
        // TODO: handle exception
        LOGGER.debug("retriveGroupDto validated()");
        return group;
    }

    private boolean checkId(String id) {
        return StringUtils.isNotBlank(id);
    }

    private boolean validateTitle(String title) {
        return StringUtils.isNotBlank(title);
    }

    private boolean validateYearEntry(String yearEntry) {
        String pattern = "^20\\d\\d-\\d\\d-\\d\\d$";
        return yearEntry.matches(pattern);
    }
}

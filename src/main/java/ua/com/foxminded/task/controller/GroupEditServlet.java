package ua.com.foxminded.task.controller;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
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
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@WebServlet(urlPatterns = "/group/edit")
public class GroupEditServlet extends HttpServlet {

    private static final long serialVersionUID = -5656956490382779313L;
    private GroupService groupService = new GroupServiceImpl();
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public GroupEditServlet() {
    }

    public GroupEditServlet(GroupService groupService) {
        this.groupService = groupService;
    }

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

        GroupDto groupDto = retriveGroupDto(req);
        Set<ConstraintViolation<GroupDto>> violations = validateGroupDto(groupDto);
        if (violations.size() == 0) {

            try {
                if (groupDto.getId() != 0) {
                    groupDto = groupService.update(groupDto);
                    successMessage = "Record group was updated!";
                } else {
                    groupDto = groupService.create(groupDto);
                    successMessage = "Record group was created!";
                }
            } catch (NoExecuteQueryException e) {
                errorMessage = "Record group was not edited!";
            }
        } else {
            errorMessage = "You enter incorrect data! ";
            for (ConstraintViolation<GroupDto> violation : violations) {
                errorMessage += " " + violation.getMessage();
            }
        }

        req.setAttribute("group", groupDto);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("successMessage", successMessage);
        req.getRequestDispatcher("group.jsp").forward(req, resp);
    }

    private GroupDto retriveGroupDto(HttpServletRequest req) {
        LOGGER.debug("retriveGroupDto()");
        String id = req.getParameter("id");
        String title = req.getParameter("title");
        String yearEntry = req.getParameter("year_entry");
        GroupDto groupDto = new GroupDto();
        if (checkId(id)) {
            groupDto.setId(Integer.valueOf(id));
        }
        groupDto.setTitle(title);
        groupDto.setYearEntry(Integer.valueOf(yearEntry));
        return groupDto;
    }

    private Set<ConstraintViolation<GroupDto>> validateGroupDto(GroupDto groupDto) {
        LOGGER.debug("validateGroupDto()");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<GroupDto>> violations = validator.validate(groupDto);
        return violations;
    }

    private boolean checkId(String id) {
        return isNotBlank(id);
    }
}

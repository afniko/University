package ua.com.foxminded.task.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.task.dao.exception.EntityAlreadyExistsException;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;

@Controller
@RequestMapping("/group")
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String PATH_HTML_GROUP = "group/group";
    private static final String PATH_HTML_GROUPS = "group/groups";
    private static final String PATH_HTML_GROUP_EDIT = "group/group_edit";
    private static final String ATTRIBUTE_HTML_TITLE = "title";
    private static final String ATTRIBUTE_HTML_GROUP = "group";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String ATTRIBUTE_HTML_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_HTML_SUCCESS_MESSAGE = "successMessage";
    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public String groups(Model model) {
        LOGGER.debug("groups()");
        String errorMessage = null;
        List<GroupDto> groups = null;
        try {
            groups = groupService.findAllDto();
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with group goes wrong!";
        } catch (NoEntityFoundException e) {
            errorMessage = "Problem with finding group";
        }

        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Groups");
        model.addAttribute(ATTRIBUTE_HTML_GROUPS, groups);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        return PATH_HTML_GROUPS;
    }

    @GetMapping("/group")
    public String group(@RequestParam("id") String idString, Model model) {
        LOGGER.debug("group()");
        String errorMessage = null;
        GroupDto group = null;

        int id = 0;
        try {
            if (checkId(idString)) {
                id = Integer.valueOf(idString);
                group = groupService.findByIdDto(id);
            } else {
                errorMessage = "You id is blank";
            }
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with group goes wrong!";
        } catch (NoEntityFoundException e) {
            errorMessage = "Group by id#" + id + " not found!";
        } catch (NumberFormatException e) {
            errorMessage = "Group id# must be numeric!";
        }

        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Group");
        model.addAttribute(ATTRIBUTE_HTML_GROUP, group);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        return PATH_HTML_GROUP;
    }

    @GetMapping("/edit")
    public String editGet(@RequestParam(name = "id", required = false) String id, Model model) {
        LOGGER.debug("editGet(), id: {}", id);
        String errorMessage = null;
        GroupDto group = new GroupDto();
        try {
            if (checkId(id)) {
                group = groupService.findByIdDto(Integer.valueOf(id));
            }
        } catch (NoEntityFoundException e) {
            errorMessage = "Problem with finding group";
        }
        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Group edit");
        model.addAttribute(ATTRIBUTE_HTML_GROUP, group);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        return PATH_HTML_GROUP_EDIT;
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("groupDto") GroupDto groupDto, Model model) {
        LOGGER.debug("editPost()");
        StringBuilder errorMessage = null;
        String successMessage = null;
        String path = PATH_HTML_GROUP;
        String pathEdit = PATH_HTML_GROUP_EDIT;

        Set<ConstraintViolation<GroupDto>> violations = validateGroupDto(groupDto);
        if (violations.isEmpty()) {

            try {
                if (groupDto.getId() != 0) {
                    groupDto = groupService.update(groupDto);
                    successMessage = "Record group was updated!";
                } else {
                    groupDto = groupService.create(groupDto);
                    successMessage = "Record group was created!";
                }
            } catch (NoExecuteQueryException e) {
                errorMessage = new StringBuilder("Record group was not edited!");
                path = pathEdit;
            } catch (EntityAlreadyExistsException e) {
                errorMessage = new StringBuilder("Record group was not created/updated! The record already exists!");
                path = pathEdit;
            }
        } else {
            errorMessage = new StringBuilder("You enter incorrect data! ");
            for (ConstraintViolation<GroupDto> violation : violations) {
                errorMessage.append(" ");
                errorMessage.append(violation.getMessage());
            }
            path = pathEdit;
        }

        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Group edit");
        model.addAttribute(ATTRIBUTE_HTML_GROUP, groupDto);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        model.addAttribute(ATTRIBUTE_HTML_SUCCESS_MESSAGE, successMessage);
        return path;
    }

    private Set<ConstraintViolation<GroupDto>> validateGroupDto(GroupDto groupDto) {
        LOGGER.debug("validateGroupDto()");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<GroupDto>> violations = validator.validate(groupDto);
        factory.close();
        return violations;
    }

    private boolean checkId(String id) {
        return StringUtils.isNoneBlank(id);
    }

}

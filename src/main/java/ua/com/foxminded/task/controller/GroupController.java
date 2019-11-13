package ua.com.foxminded.task.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@Controller
@RequestMapping("/group")
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private GroupService groupService;

    public GroupController() {
        groupService = new GroupServiceImpl();
    }

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public String groups(Model model) {
        String errorMessage = null;
        List<GroupDto> groups = null;
        try {
            groups = groupService.findAllDto();
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with group goes wrong!";
        }

        model.addAttribute("title", "Groups");
        model.addAttribute("groups", groups);
        model.addAttribute("errorMessage", errorMessage);
        return "groups";
    }

    @GetMapping("/group")
    public String group(@RequestParam("id") String idString, Model model) {
        String errorMessage = null;
        GroupDto group = null;

        int id = 0;
        try {
            id = Integer.valueOf(idString);
            if (checkId(idString)) {
                errorMessage = "You id is blank";
            } else {
                group = groupService.findByIdDto(id);
            }
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with group goes wrong!";
        } catch (NoEntityFoundException e) {
            errorMessage = "Group by id#" + id + " not found!";
        } catch (NumberFormatException e) {
            errorMessage = "Group id# must be numeric!";
        }

        model.addAttribute("title", "Group");
        model.addAttribute("group", group);
        model.addAttribute("errorMessage", errorMessage);
        return "group";
    }

    @GetMapping("/edit")
    public String editGet(@RequestParam("id") String id, Model model) {
        if (checkId(id)) {
            GroupDto group = groupService.findByIdDto(Integer.valueOf(id));
            model.addAttribute("group", group);
        }

        model.addAttribute("title", "Group edit");
        return "group_edit";
    }

    @PostMapping("/edit")
    public String editPost(@RequestBody Map<String, String> body, Model model) {
        StringBuilder errorMessage = null;
        String successMessage = null;
        String path = "group.jsp";

        GroupDto groupDto = retriveGroupDto(body);
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
                errorMessage = new StringBuilder("Record group was not edited!");
                path = "group_edit.jsp";
            }
        } else {
            errorMessage = new StringBuilder("You enter incorrect data! ");
            for (ConstraintViolation<GroupDto> violation : violations) {
                errorMessage.append(" ");
                errorMessage.append(violation.getMessage());
            }
            path = "group_edit.jsp";
        }

        model.addAttribute("title", "Group edit");
        model.addAttribute("group", groupDto);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("successMessage", successMessage);
        return path;
    }

    private GroupDto retriveGroupDto(Map<String, String> body) {
        LOGGER.debug("retriveGroupDto()");
        String id = body.get("id");
        String title = body.get("title");
        String yearEntry = body.get("year_entry");
        GroupDto groupDto = new GroupDto();
        if (checkId(id)) {
            groupDto.setId(Integer.valueOf(id));
        }
        groupDto.setTitle(title);
        if (yearEntry.matches("^\\d+$")) {
            groupDto.setYearEntry(Integer.valueOf(yearEntry));
        } else {
            groupDto.setYearEntry(null);
        }
        return groupDto;
    }

    private Set<ConstraintViolation<GroupDto>> validateGroupDto(GroupDto groupDto) {
        LOGGER.debug("validateGroupDto()");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<GroupDto>> violations = validator.validate(groupDto);
        factory.close();
        return violations;
    }

    private boolean checkId(String idString) {
        return StringUtils.isBlank(idString);
    }

}

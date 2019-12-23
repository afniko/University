package ua.com.foxminded.task.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;

@RestController("groupRestController")
@RequestMapping("/api")
public class GroupController {

    private GroupService groupService;
    private Logger logger;

    @Autowired
    public GroupController(Logger logger, GroupService groupService) {
        this.logger = logger;
        this.groupService = groupService;
    }

    @GetMapping(path = "/groups", produces = "application/json")
    public List<GroupDto> groups() {
        logger.debug("groups()");
        List<GroupDto> groups = null;
        groups = groupService.findAllDto();
        return groups;
    }

    @GetMapping(path = "/groups/{id}", produces = "application/json")
    public GroupDto groupById(@PathVariable("id") String id) {
        logger.debug("groupById()");
        GroupDto groupDto = null;
        if (checkId(id)) {
            groupDto = groupService.findByIdDto(Integer.valueOf(id));
        }
        return groupDto;
    }

    @PostMapping(path = "/groups", produces = "application/json")
    public GroupDto editGroup(@Valid @RequestBody GroupDto groupDto) {
        logger.debug("editPost()");
        if (groupDto.getId() != 0) {
            groupDto = groupService.update(groupDto);
        } else {
            groupDto = groupService.create(groupDto);
        }
        return groupDto;
    }

    private boolean checkId(String id) {
        return StringUtils.isNoneBlank(id);
    }

}

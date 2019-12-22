package ua.com.foxminded.task.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;

@RestController
@RequestMapping("/rest")
public class GroupRestController {

    private GroupService groupService;
    private Logger logger;

    @Autowired
    public GroupRestController(Logger logger, GroupService groupService) {
        this.logger = logger;
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public List<GroupDto> groups(Model model) {
        logger.debug("groups()");
        List<GroupDto> groups = null;
        groups = groupService.findAllDto();
        return groups;
    }

    @GetMapping("/group")
    public GroupDto group(@RequestParam("id") String idString, Model model) {
        logger.debug("group()");
        GroupDto groupDto = null;

        int id = 0;
        if (checkId(idString)) {
            id = Integer.valueOf(idString);
            groupDto = groupService.findByIdDto(id);
        }
        return groupDto;
    }

    @GetMapping("/group_edit")
    public GroupDto editGet(@RequestParam(name = "id", required = false) String id, Model model) {
        logger.debug("editGet(), id: {}", id);
        GroupDto groupDto = new GroupDto();
        if (checkId(id)) {
            groupDto = groupService.findByIdDto(Integer.valueOf(id));
        }
        return groupDto;
    }

    @PostMapping("/group_edit")
    public GroupDto editPost(@Valid @RequestBody GroupDto groupDto) {
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

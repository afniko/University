package ua.com.foxminded.task.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@Controller
@RequestMapping("/group")
public class GroupController {
    
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
    public String group(Model model) {
        String errorMessage = null;
        List<GroupDto> groups = null;
        try {
            groups = groupService.findAllDto();
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with group goes wrong!";
        }
        
        model.addAttribute("title", "Group");
        model.addAttribute("groups", groups);
        model.addAttribute("errorMessage", errorMessage);
        return "groups";
    }
}

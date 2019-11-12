package ua.com.foxminded.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/group")
public class GroupController {

    @GetMapping("/")
    public String tindex(Model model) {
        model.addAttribute("title", "Group");
        model.addAttribute("name", "Thymeleaf group");
        return "tindex";
    }
}

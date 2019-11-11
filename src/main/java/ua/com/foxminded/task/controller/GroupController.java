package ua.com.foxminded.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GroupController {

    @GetMapping("/ttest")
    public String tindex(Model model) {
        model.addAttribute("name", "thymeleaf");
        return "tindex";
    }
}

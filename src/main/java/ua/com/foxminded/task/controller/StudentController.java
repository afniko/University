package ua.com.foxminded.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping
    public String tindex(Model model) {
        model.addAttribute("title", "Student");
        model.addAttribute("name", "Thymeleaf group");
        return "tindex";
    }
}

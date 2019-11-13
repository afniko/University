package ua.com.foxminded.task.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.impl.StudentServiceImpl;

@Controller
@RequestMapping("/student")
public class StudentController {

    private static StudentService studentService;

    public StudentController() {
        studentService = new StudentServiceImpl();
    }

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String tindex(Model model) {
        String errorMessage = null;
        List<StudentDto> students = null;
        try {
            students = studentService.findAllDto();
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with student goes wrong!";
        }

        model.addAttribute("title", "Students");
        model.addAttribute("students", students);
        model.addAttribute("errorMessage", errorMessage);
        return "students";
    }
}

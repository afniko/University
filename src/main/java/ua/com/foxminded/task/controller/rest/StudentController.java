package ua.com.foxminded.task.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;

@RestController("studentRestController")
@RequestMapping("/api/rest")
public class StudentController {

    private Logger logger;
    private StudentService studentService;

    @Autowired
    public StudentController(Logger logger, StudentService studentService) {
        this.logger = logger;
        this.studentService = studentService;
    }

    @GetMapping(path = "/students", produces = "application/json")
    public List<StudentDto> students() {
        logger.debug("students()");
        List<StudentDto> students = null;
        students = studentService.findAllDto();
        return students;
    }

    @GetMapping(path = "/students/{id}", produces = "application/json")
    public StudentDto studentById(@PathVariable("id") String id, Model model) {
        logger.debug("studentById()");
        StudentDto studentDto = null;
        if (checkId(id)) {
            studentDto = studentService.findByIdDto(Integer.valueOf(id));
        }
        return studentDto;
    }

    @PostMapping(path = "/students", produces = "application/json")
    public StudentDto editPost(@Valid @RequestBody StudentDto studentDto) {
        logger.debug("editPost()");
        if (studentDto.getId() != 0) {
            studentDto = studentService.update(studentDto);
        } else {
            studentDto = studentService.create(studentDto);
        }
        return studentDto;
    }

    private boolean checkId(String id) {
        return StringUtils.isNoneBlank(id);
    }

}

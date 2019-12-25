package ua.com.foxminded.task.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;

@RestController("studentRestController")
@RequestMapping("/api")
@Api(description = "Student management System", produces = "application/json", consumes = "application/json")
public class StudentController {

    private Logger logger;
    private StudentService studentService;

    @Autowired
    public StudentController(Logger logger, StudentService studentService) {
        this.logger = logger;
        this.studentService = studentService;
    }

    @GetMapping(path = "/students", produces = "application/json")
    @ApiOperation(value = "View a list of available students")
    public List<StudentDto> getStudents() {
        logger.debug("students()");
        return studentService.findAllDto();
    }

    @GetMapping(path = "/students/{id}", produces = "application/json")
    @ApiOperation(value = "View a student by id")
    public StudentDto getStudentById(@PathVariable("id") int id) {
        logger.debug("studentById()");
        return studentService.findByIdDto(id);
    }

    @PostMapping(path = "/students", produces = "application/json")
    @ApiOperation(value = "Create (if id=0) or update a student")
    public StudentDto saveStudent(@Valid @RequestBody StudentDto studentDto) {
        logger.debug("editPost()");
        if (studentDto.getId() != 0) {
            studentDto = studentService.update(studentDto);
        } else {
            studentDto = studentService.create(studentDto);
        }
        return studentDto;
    }

}

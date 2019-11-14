package ua.com.foxminded.task.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;
import ua.com.foxminded.task.service.impl.StudentServiceImpl;

@Controller
@RequestMapping("/student")
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static StudentService studentService;
    private static GroupService groupService;

    public StudentController() {
        studentService = new StudentServiceImpl();
        groupService = new GroupServiceImpl();
    }

    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping("/students")
    public String students(Model model) {
        LOGGER.debug("students()");
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

    @GetMapping("/student")
    public String student(@RequestParam("id") String idString, Model model) {
        LOGGER.debug("student()");
        String errorMessage = null;
        StudentDto student = null;
        List<GroupDto> groups = null;
        int id = 0;
        try {
            if (checkId(idString)) {
                id = Integer.valueOf(idString);
                student = studentService.findByIdDto(id);
                groups = groupService.findAllDto();
            } else {
                errorMessage = "You id is blank";
            }
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with student goes wrong!";
        } catch (NoEntityFoundException e) {
            errorMessage = "Student by id#" + id + " not found!";
        } catch (NumberFormatException e) {
            errorMessage = "Student id# must be numeric!";
        }
        model.addAttribute("student", student);
        model.addAttribute("groups", groups);
        model.addAttribute("errorMessage", errorMessage);
        return "student";
    }

    @GetMapping("/edit")
    public String editGet(@RequestParam(name = "id", required = false) String id, Model model) {
        LOGGER.debug("editGet(), id: {}", id);
        StudentDto student = new StudentDto();
        if (checkId(id)) {
            student = studentService.findByIdDto(Integer.valueOf(id));
            model.addAttribute("student", student);
        }
        List<GroupDto> groups = groupService.findAllDto();
        model.addAttribute("groups", groups);
        return "student_edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("studentDto") StudentDto studentDto, Model model) {
        LOGGER.debug("editPost()");
        StringBuilder errorMessage = null;
        String successMessage = null;
        List<GroupDto> groups = null;
        String path = "student.jsp";

        Set<ConstraintViolation<StudentDto>> violations = validateStudentDto(studentDto);
        if (violations.isEmpty()) {
            try {
                if (studentDto.getId() != 0) {
                    studentDto = studentService.update(studentDto);
                    successMessage = "Record student was updated!";
                } else {
                    studentDto = studentService.create(studentDto);
                    successMessage = "Record student was created";
                }
            } catch (NoExecuteQueryException e) {
                errorMessage = new StringBuilder("Record student was not edited!");
                path = "student_edit.jsp";
            }
        } else {
            errorMessage = new StringBuilder("You enter incorrect data!");
            for (ConstraintViolation<StudentDto> violation : violations) {
                errorMessage.append(" ");
                errorMessage.append(violation.getMessage());
            }
            path = "student_edit.jsp";
        }
        groups = groupService.findAllDto();

        model.addAttribute("student", studentDto);
        model.addAttribute("groups", groups);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("successMessage", successMessage);
        return path;
    }

    private Set<ConstraintViolation<StudentDto>> validateStudentDto(StudentDto studentDto) {
        LOGGER.debug("validateStudentDto()");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<StudentDto>> violations = validator.validate(studentDto);
        factory.close();
        return violations;
    }

    private boolean checkId(String id) {
        return StringUtils.isNoneBlank(id);
    }

}

package ua.com.foxminded.task.controller;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.task.dao.exception.EntityAlreadyExistsException;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;

@Controller
public class StudentController {

    @Autowired
    private Logger logger;
    private static final String PATH_HTML_STUDENT = "student/student";
    private static final String PATH_HTML_STUDENTS = "student/students";
    private static final String PATH_HTML_STUDENT_EDIT = "student/student_edit";
    private static final String ATTRIBUTE_HTML_TITLE = "title";
    private static final String ATTRIBUTE_HTML_STUDENT = "student";
    private static final String ATTRIBUTE_HTML_STUDENTS = "students";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String ATTRIBUTE_HTML_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_HTML_SUCCESS_MESSAGE = "successMessage";
    private StudentService studentService;
    private GroupService groupService;

    @Autowired
    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping("/students")
    public String students(Model model) {
        logger.debug("students()");
        String errorMessage = null;
        List<StudentDto> students = null;
        try {
            students = studentService.findAllDto();
        } catch (NoExecuteQueryException e) {
            errorMessage = "Something with student goes wrong!";
        }

        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Students");
        model.addAttribute(ATTRIBUTE_HTML_STUDENTS, students);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        return PATH_HTML_STUDENTS;
    }

    @GetMapping("/student")
    public String student(@RequestParam("id") String idString, Model model) {
        logger.debug("student()");
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
        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Student");
        model.addAttribute(ATTRIBUTE_HTML_STUDENT, student);
        model.addAttribute(ATTRIBUTE_HTML_GROUPS, groups);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        return PATH_HTML_STUDENT;
    }

    @GetMapping("/student_edit")
    public String editGet(@RequestParam(name = "id", required = false) String id, Model model) {
        logger.debug("editGet(), id: {}", id);
        String errorMessage = null;
        StudentDto student = new StudentDto();
        try {
            if (checkId(id)) {
                student = studentService.findByIdDto(Integer.valueOf(id));
            }
        } catch (NoEntityFoundException e) {
            errorMessage = "Problem with finding group";
        }
        List<GroupDto> groups = groupService.findAllDto();

        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Student edit");
        model.addAttribute(ATTRIBUTE_HTML_STUDENT, student);
        model.addAttribute(ATTRIBUTE_HTML_GROUPS, groups);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        return PATH_HTML_STUDENT_EDIT;
    }

    @PostMapping("/student_edit")
    public String editPost(@ModelAttribute("studentDto") StudentDto studentDto, BindingResult bindingResult, Model model) {
        logger.debug("editPost()");
        StringBuilder errorMessage = null;
        String successMessage = null;
        List<GroupDto> groups = null;
        String path = PATH_HTML_STUDENT;
        String pathEdit = PATH_HTML_STUDENT_EDIT;
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
                path = pathEdit;
                groups = groupService.findAllDto();
            } catch (EntityAlreadyExistsException e) {
                errorMessage = new StringBuilder("Record sudent was not created/updated! The record already exists!");
                path = pathEdit;
            }
        } else {
            errorMessage = new StringBuilder("You enter incorrect data!");
            for (ConstraintViolation<StudentDto> violation : violations) {
                errorMessage.append(" ");
                errorMessage.append(violation.getMessage());
            }
            path = pathEdit;
            groups = groupService.findAllDto();
        }

        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Student edit");
        model.addAttribute(ATTRIBUTE_HTML_STUDENT, studentDto);
        model.addAttribute(ATTRIBUTE_HTML_GROUPS, groups);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        model.addAttribute(ATTRIBUTE_HTML_SUCCESS_MESSAGE, successMessage);
        return path;
    }

    private Set<ConstraintViolation<StudentDto>> validateStudentDto(StudentDto studentDto) {
        logger.debug("validateStudentDto()");
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

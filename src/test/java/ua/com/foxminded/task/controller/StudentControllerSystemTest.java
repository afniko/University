package ua.com.foxminded.task.controller;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.foxminded.task.dao.GroupRepository;
import ua.com.foxminded.task.dao.StudentRepository;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.StudentModelRepository;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;

@SpringBootTest
public class StudentControllerSystemTest {

    @Autowired
    private Flyway flyway;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private WebApplicationContext context;

    private static final String PATH_HTML_STUDENT = "student/student";
    private static final String PATH_HTML_STUDENTS = "student/students";
    private static final String PATH_HTML_STUDENT_EDIT = "student/student_edit";
    private static final String ATTRIBUTE_HTML_STUDENT = "studentDto";
    private static final String ATTRIBUTE_HTML_STUDENTS = "students";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String EXPECTED_ERROR_MESSAGE = "You enter incorrect data!";
    private static final Group GROUP1 = GroupModelRepository.getModel1();
    private static final Group GROUP2 = GroupModelRepository.getModel2();
    private static final Group GROUP3 = GroupModelRepository.getModel3();
    private static final Group GROUP4 = GroupModelRepository.getModel4();

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        flyway.migrate();
        groupRepository.save(GROUP1);
        groupRepository.save(GROUP2);
        groupRepository.save(GROUP3);
        groupRepository.saveAndFlush(GROUP4);
        studentRepository.saveAll(StudentModelRepository.getModels());
    }

    @Test
    void whenRetriveAllStudent_thenExpectListOfStudent() throws Exception {
        String expectedTitle = "Students";
        List<StudentDto> students = StudentDtoModelRepository.getModels();
        String httpRequest = "/students";
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENTS))
                .andExpect(content().string(allOf(containsString("<title>" + expectedTitle + "</title>"))))
                .andDo(print())
                .andReturn();
        List<StudentDto> actuallyStudents = (List<StudentDto>) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENTS);
        assertTrue(actuallyStudents.containsAll(students));
    }

    @Test
    void whenRetriveTheStudent_thenExpectStudentById() throws Exception {
        String expectedTitle = "Student";
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        int id = 1;
        String httpRequest = "/student?id=" + id;
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andExpect(content().string(allOf(containsString("<title>" + expectedTitle + "</title>"))))
                .andDo(print())
                .andReturn();
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertEquals(studentDto, actuallyStudent);
    }

    @Test
    void whenRetriveEditExistsStudent_thenExpectFormWithStudentField() throws Exception {
        String expectedTitle = "Student edit";
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        List<GroupDto> groups = GroupDtoModelRepository.getModels();
        int id = 1;
        String httpRequest = "/student_edit?id=" + id;
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andExpect(content().string(allOf(containsString("<title>" + expectedTitle + "</title>"))))
                .andDo(print())
                .andReturn();
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertEquals(studentDto, actuallyStudent);
        List<GroupDto> actuallyGroups = (List<GroupDto>) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUPS);
        assertEquals(groups, actuallyGroups);
    }

    @Test
    void whenSubmitEditFormStudentWithId_thenUpdateStudent() throws Exception {
        String expectedTitle = "Student edit";
        String expectedSuccessMessage = "Record student was updated!";
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        studentDto.setId(1);
        String httpRequest = "/student_edit";
        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andExpect(content().string(allOf(
                        containsString("<title>" + expectedTitle + "</title>"), 
                        containsString("<div class=\"alert alert-success\">" + expectedSuccessMessage + "</div>")
                        )))
                .andDo(print())
                .andReturn();
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertEquals(studentDto, actuallyStudent);
    }

    @Test
    void whenSubmitEditFormStudentWithoutId_thenCreateStudent() throws Exception {
        String expectedTitle = "Student edit";
        String expectedSuccessMessage = "Record student was created";
        StudentDto studentDto = StudentDtoModelRepository.getModel7();
        String httpRequest = "/student_edit";
        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andExpect(content().string(allOf(
                        containsString("<title>" + expectedTitle + "</title>"), 
                        containsString("<div class=\"alert alert-success\">" + expectedSuccessMessage + "</div>")
                        )))
                .andDo(print())
                .andReturn();
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertEquals(studentDto, actuallyStudent);
    }

    @Test
    void whenUpdateStudentWithNotCorrectValues_thenExpectError() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel7();
        studentDto.setFirstName("");
        studentDto.setIdFees(1212121212);
        String httpRequest = "/student_edit";
        this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andExpect(content().string(allOf(containsString("<div>" + EXPECTED_ERROR_MESSAGE + "</div>"))))
                .andExpect(model().attributeHasFieldErrorCode("studentDto", "firstName", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("studentDto", "idFees", "Max"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void whenUpdateStudentWithNotCorrectValues_thenExpectError2() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel7();
        studentDto.setFirstName("qwertyuiopasdfghjklzxcvbnm");
        studentDto.setMiddleName("qwertyuiopasdfghjklzxcvbnm");
        studentDto.setLastName("qwertyuiopasdfghjklzxcvbnm");
        studentDto.setGroupTitle("qwertyuiopasdfghjklzxcvbnm");
        studentDto.setIdFees(12121212);
        String httpRequest = "/student_edit";
        this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andExpect(content().string(allOf(containsString("<div>" + EXPECTED_ERROR_MESSAGE + "</div>"))))
                .andExpect(model().attributeHasFieldErrorCode("studentDto", "firstName", "Length"))
                .andExpect(model().attributeHasFieldErrorCode("studentDto", "middleName", "Length"))
                .andExpect(model().attributeHasFieldErrorCode("studentDto", "lastName", "Length"))
                .andExpect(model().attributeHasFieldErrorCode("studentDto", "groupTitle", "Length"))
                .andExpect(model().attributeHasFieldErrorCode("studentDto", "idFees", "Min"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void whenUpdateStudentWithNotCorrectValues_thenExpectError3() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel7();
        studentDto.setId(7);
        int idFeesExist = StudentDtoModelRepository.getModel6().getIdFees();
        studentDto.setIdFees(idFeesExist);
        studentDto.setIdGroup(2);
        String httpRequest = "/student_edit";
        this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andExpect(content().string(allOf(containsString("<div>" + EXPECTED_ERROR_MESSAGE + "</div>"))))
                .andExpect(model().attributeHasFieldErrorCode("studentDto", "idFees", "StudentIdFeesUnique"))
                .andExpect(model().attributeHasFieldErrorCode("studentDto", "idGroup", "MaxStudentsInGroupLimit"))
                .andDo(print())
                .andReturn();
    }

    @AfterEach
    public void removeCreatedTables() {
        flyway.clean();
    }
}

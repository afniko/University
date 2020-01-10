package ua.com.foxminded.task.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;

@DBRider
@SpringBootTest
public class StudentControllerSystemTest {

    @Autowired
    private WebApplicationContext context;

    private static final String PATH_HTML_STUDENT = "student/student";
    private static final String PATH_HTML_STUDENTS = "student/students";
    private static final String PATH_HTML_STUDENT_EDIT = "student/student_edit";
    private static final String ATTRIBUTE_HTML_STUDENT = "studentDto";
    private static final String ATTRIBUTE_HTML_STUDENTS = "students";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String EXPECTED_ERROR_MESSAGE = "You enter incorrect data!";

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DataSet(value = "student/studentsWithGroups.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
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
        assertThat(actuallyStudents).containsAll(students);
    }

    @Test
    @DataSet(value = "student/studentsWithGroups.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = "student/studentsWithGroups.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    void whenRetriveEditExistsStudent_thenExpectFormWithStudentField() throws Exception {
        String expectedTitle = "Student edit";
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        List<GroupDto> groups = GroupDtoModelRepository.getModelDtos();
        int id = 1;
        String httpRequest = "/student_edit?id=" + id;
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andExpect(content().string(allOf(containsString("<title>" + expectedTitle + "</title>"))))
                .andDo(print())
                .andReturn();
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertThat(studentDto).isEqualTo(actuallyStudent);
        List<GroupDto> actuallyGroups = (List<GroupDto>) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUPS);
        assertThat(actuallyGroups).containsAll(groups);
    }

    @Test
    @DataSet(value = "student/studentsWithGroups.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history",
             transactional = false)
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
        assertThat(actuallyStudent).isEqualTo(studentDto);
    }

    @Test
    @DataSet(value = "student/studentsWithGroups.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
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
        assertThat(actuallyStudent).isEqualTo(studentDto);
    }

    @Test
    @DataSet(value = "student/studentsWithGroups.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = "student/studentsWithGroups.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
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
    @DataSet(value = "student/studentsWithGroups.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
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

}

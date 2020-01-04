package ua.com.foxminded.task.controller;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import ua.com.foxminded.task.config.TestMvcConfig;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;

@WebMvcTest(StudentController.class)
@Import(TestMvcConfig.class)
public class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @MockBean
    private StudentService studentService;

    private static final String PATH_HTML_STUDENT = "student/student";
    private static final String PATH_HTML_STUDENTS = "student/students";
    private static final String PATH_HTML_STUDENT_EDIT = "student/student_edit";
    private static final String ATTRIBUTE_HTML_STUDENT = "studentDto";
    private static final String ATTRIBUTE_HTML_STUDENTS = "students";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String EXPECTED_ERROR_MESSAGE = "You enter incorrect data!";

    @Test
    void whenRetriveAllStudent_thenExpectListOfStudent() throws Exception {
        List<StudentDto> studentDtos = StudentDtoModelRepository.getModels();
        String httpRequest = "/students";
        String expectedTitle = "Students";

        when(studentService.findAllDto()).thenReturn(studentDtos);

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENTS))
                .andExpect(content().string(allOf(containsString("<title>" + expectedTitle + "</title>"))))
                .andDo(print())
                .andReturn();
        List<StudentDto> actuallyStudents = (List<StudentDto>) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENTS);
        assertTrue(actuallyStudents.containsAll(studentDtos));
    }

    @Test
    void whenRetriveTheStudent_thenExpectStudentById() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        int id = 1;
        String httpRequest = "/student?id=" + id;
        String expectedTitle = "Student";

        when(studentService.findByIdDto(id)).thenReturn(studentDto);

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
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        List<GroupDto> groupDtos = GroupDtoModelRepository.getModels();
        int id = 1;
        String httpRequest = "/student_edit?id=" + id;
        String expectedTitle = "Student edit";

        when(studentService.findByIdDto(id)).thenReturn(studentDto);
        when(groupService.findAllDto()).thenReturn(groupDtos);

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andExpect(content().string(allOf(containsString("<title>" + expectedTitle + "</title>"))))
                .andDo(print())
                .andReturn();
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertEquals(studentDto, actuallyStudent);
        List<GroupDto> actuallyGroups = (List<GroupDto>) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUPS);
        assertEquals(groupDtos, actuallyGroups);
    }

    @Test
    void whenSubmitEditFormStudentWithId_thenUpdateStudent() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        studentDto.setId(1);
        String httpRequest = "/student_edit";
        String expectedTitle = "Student edit";
        String expectedSuccessMessage = "Record student was updated!";

        when(studentService.update(studentDto)).thenReturn(studentDto);

        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andExpect(content().string(allOf(
                        containsString("<title>" + expectedTitle + "</title>"), 
                        containsString("<div class=\"alert alert-success\">" + expectedSuccessMessage + "</div>"))))
                .andDo(print())
                .andReturn();
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertEquals(studentDto, actuallyStudent);
    }

    @Test
    void whenSubmitEditFormStudentWithoutId_thenCreateStudent() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel7();
        String httpRequest = "/student_edit";
        String expectedTitle = "Student edit";
        String expectedSuccessMessage = "Record student was created";

        when(studentService.create(studentDto)).thenReturn(studentDto);

        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andExpect(content().string(allOf(
                        containsString("<title>" + expectedTitle + "</title>"), 
                        containsString("<div class=\"alert alert-success\">" + expectedSuccessMessage + "</div>"))))
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
                .andDo(print()).andReturn();
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
}

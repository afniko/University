package ua.com.foxminded.task.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;
import ua.com.foxminded.task.service.impl.StudentServiceImpl;

@WebMvcTest(controllers=StudentController.class)
public class StudentControllerTest {

    private MockMvc mockMvc;

    private static final String PATH_HTML_STUDENT = "student/student";
    private static final String PATH_HTML_STUDENTS = "student/students";
    private static final String PATH_HTML_STUDENT_EDIT = "student/student_edit";
    private static final String ATTRIBUTE_HTML_TITLE = "title";
    private static final String ATTRIBUTE_HTML_STUDENT = "student";
    private static final String ATTRIBUTE_HTML_STUDENTS = "students";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String ATTRIBUTE_HTML_SUCCESS_MESSAGE = "successMessage";

    private Logger logger = LoggerFactory.getLogger(StudentController.class);
    private StudentService studentService = mock(StudentServiceImpl.class);
    private GroupService groupService = mock(GroupServiceImpl.class);
    private StudentController studentController = new StudentController(logger, studentService, groupService);

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void whenRetriveHttpGetRequestStudents_thenExpectViewNameStudentsWithAttribute() throws Exception {
        List<StudentDto> students = StudentDtoModelRepository.getModels();
        when(studentService.findAllDto()).thenReturn(students);
        String httpRequest = "/students";
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENTS))
                .andDo(print())
                .andReturn();
        String expectedTitle = "Students";
        String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
        assertEquals(actuallyTitle, expectedTitle);
        List<StudentDto> actuallyStudents = (List<StudentDto>) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENTS);
        assertEquals(students, actuallyStudents);
    }
    
    @Test
    void whenRetriveHttpGetRequestStudent_thenExpectViewNameStudentWithAttribute() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        List<GroupDto> groups = GroupDtoModelRepository.getModels();
        int id = studentDto.getId();
        String httpRequest = "/student?id=" + id;
        when(studentService.findByIdDto(id)).thenReturn(studentDto);
        when(groupService.findAllDto()).thenReturn(groups);
        
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andDo(print())
                .andReturn();
        String expectedTitle = "Student";
        String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
        assertEquals(actuallyTitle, expectedTitle);
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertEquals(studentDto, actuallyStudent);
    }
    
    @Test
    void whenRetriveHttpGetRequestStudentEdit_thenExpectViewNameStudenteditWithAttribute() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        List<GroupDto> groups = GroupDtoModelRepository.getModels();
        int id = studentDto.getId();
        String httpRequest = "/student_edit?id=" + id;
        when(studentService.findByIdDto(id)).thenReturn(studentDto);
        when(groupService.findAllDto()).thenReturn(groups);

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andDo(print())
                .andReturn();
        String expectedTitle = "Student edit";
        String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
        assertEquals(actuallyTitle, expectedTitle);
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertEquals(studentDto, actuallyStudent);
        List<GroupDto> actuallyGroups = (List<GroupDto>) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUPS);
        assertEquals(groups, actuallyGroups);
    }
    
    @Test
    void whenRetriveHttpPostRequestStudentEditAndStudentWithId_thenExpectViewNameStudenteditWithAttribute() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        studentDto.setId(1);
        String httpRequest = "/student_edit";
        when(studentService.update(studentDto)).thenReturn(studentDto);
        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andDo(print())
                .andReturn();
        String expectedTitle = "Student edit";
        String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
        assertEquals(actuallyTitle, expectedTitle);
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertEquals(studentDto, actuallyStudent);
        String expectedSuccessMessage = "Record student was updated!";
        String actuallySuccessMessage = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_SUCCESS_MESSAGE).toString();
        assertEquals(actuallySuccessMessage, expectedSuccessMessage);
    }
    
    @Test
    void whenRetriveHttpPostRequestStudentEditAndStudentWithoutId_thenExpectViewNameStudenteditWithAttribute() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        String httpRequest = "/student_edit";
        when(studentService.create(studentDto)).thenReturn(studentDto);

        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andDo(print())
                .andReturn();
        String expectedTitle = "Student edit";
        String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
        assertEquals(actuallyTitle, expectedTitle);
        StudentDto actuallyStudent = (StudentDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_STUDENT);
        assertEquals(studentDto, actuallyStudent);
        String expectedSuccessMessage = "Record student was created";
        String actuallySuccessMessage = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_SUCCESS_MESSAGE).toString();
        assertEquals(actuallySuccessMessage, expectedSuccessMessage);
    }
}

package ua.com.foxminded.task.controller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import ua.com.foxminded.task.dao.exception.EntityAlreadyExistsException;
import ua.com.foxminded.task.dao.exception.EntityNotValidException;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.StudentService;

@WebMvcTest(StudentController.class)
@Import(TestMvcConfig.class)
public class StudentControllerTest {

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
    private static final String ATTRIBUTE_HTML_ERROR_MESSAGE = "errorMessage";
    
    @Test
    void whenRetriveAllStudent_thenExpectListOfStudent() throws Exception {
        List<StudentDto> studentDtos = StudentDtoModelRepository.getModels();
        String expectedTitle = "Students";
        String httpRequest = "/students";

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
    void whenInvokeAllStudentsWithBadRequest_thenExpectErrorsMessage() throws Exception {
        String expectedErrorMessage = "Something with student goes wrong!";
        String httpRequest = "/students";

        doThrow(NoExecuteQueryException.class).when(studentService).findAllDto();
        
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENTS))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
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
        assertThat(studentDto).isEqualTo(actuallyStudent);
    }

    @Test
    void whenInvokeByBlankId_thenErrorMessage() throws Exception {
        String expectedErrorMessage = "You id is blank";
        String httpRequest = "/student?id=";

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenInvokeGroupWithBadRequest_thenErrorMessage() throws Exception {
        String expectedErrorMessage = "Something with student goes wrong!";
        int id = 1;
        String httpRequest = "/student?id=" + id;
        
        doThrow(NoExecuteQueryException.class).when(studentService).findByIdDto(id);

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenInvokeNoFoundGroup_thenErrorMessage() throws Exception {
        int id = 1;
        String expectedErrorMessage = "Student by id#" + id + " not found!";
        String httpRequest = "/student?id=" + id;
        
        doThrow(NoEntityFoundException.class).when(studentService).findByIdDto(id);

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenInvokeGroupWithIncorrectNumberFormatId_thenErrorMessage() throws Exception {
        int id = 1;
        String expectedErrorMessage = "Student id# must be numeric!";
        String httpRequest = "/student?id=" + id;
        
        doThrow(NumberFormatException.class).when(studentService).findByIdDto(id);

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
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
    void whenInvokeEditGroupWithNoEntityNumber_thenExpectErrorMessage() throws Exception {
        String expectedErrorMessage = "Problem with finding group";
        List<GroupDto> groupDtos = GroupDtoModelRepository.getModels();
        int id = 1;
        String httpRequest = "/student_edit?id=" + id;
        
        doThrow(NoEntityFoundException.class).when(studentService).findByIdDto(id);
        when(groupService.findAllDto()).thenReturn(groupDtos);

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
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
        assertThat(studentDto).isEqualTo(actuallyStudent);
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
        assertThat(studentDto).isEqualTo(actuallyStudent);
    }
    
    @Test
    void whenInvokeEditStudentWithNoExequteQuery_thenExpectErrorMessage() throws Exception {
        String expectedErrorMessage = "Record student was not edited!";
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        List<GroupDto> groupDtos = GroupDtoModelRepository.getModels();
        studentDto.setId(1);
        String httpRequest = "/student_edit";

        doThrow(NoExecuteQueryException.class).when(studentService).update(studentDto);
        when(groupService.findAllDto()).thenReturn(groupDtos);
        
        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenInvokeCreateExistsStudent_thenExpectErrorMessage() throws Exception {
        String expectedErrorMessage = "Record sudent was not created! The record already exists!";
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        String httpRequest = "/student_edit";

        doThrow(EntityAlreadyExistsException.class).when(studentService).create(studentDto);
        
        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenInvokeEditNotFoundEntity_thenExpectErrorMessage() throws Exception {
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        studentDto.setId(1);
        String expectedErrorMessage = "Student " + studentDto + " not found!";
        String httpRequest = "/student_edit";

        doThrow(NoEntityFoundException.class).when(studentService).update(studentDto);
        
        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenInvokeEditNotValidEntity_thenExpectErrorMessage() throws Exception {
        String expectedErrorMessage = "Record sudent was not updated/created! The data is not valid!";
        StudentDto studentDto = StudentDtoModelRepository.getModel1();
        studentDto.setId(1);
        String httpRequest = "/student_edit";

        doThrow(EntityNotValidException.class).when(studentService).update(studentDto);
        
        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
}

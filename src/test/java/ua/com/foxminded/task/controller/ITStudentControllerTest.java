package ua.com.foxminded.task.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
public class ITStudentControllerTest {

    @Autowired
    private Flyway flyway;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;

    private static final String PATH_HTML_STUDENT = "student/student";
    private static final String PATH_HTML_STUDENTS = "student/students";
    private static final String PATH_HTML_STUDENT_EDIT = "student/student_edit";
    private static final String ATTRIBUTE_HTML_TITLE = "title";
    private static final String ATTRIBUTE_HTML_STUDENT = "studentDto";
    private static final String ATTRIBUTE_HTML_STUDENTS = "students";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String ATTRIBUTE_HTML_SUCCESS_MESSAGE = "successMessage";
    private static final Group GROUP1 = GroupModelRepository.getModel1();
    private static final Group GROUP2 = GroupModelRepository.getModel2();
    private static final Group GROUP3 = GroupModelRepository.getModel3();
    private static final Group GROUP4 = GroupModelRepository.getModel4();
//    private static final Student STUDENT7 = StudentDtoModelRepository.getModel7();

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        flyway.migrate();
        groupRepository.save(GROUP1);
        groupRepository.save(GROUP2);
        groupRepository.save(GROUP3);
        groupRepository.saveAndFlush(GROUP4);
        studentRepository.saveAll(StudentModelRepository.getModels());
    }

  @Test
  void whenRetriveHttpGetRequestStudents_thenExpectViewNameStudentsWithAttribute() throws Exception {
      List<StudentDto> students = StudentDtoModelRepository.getModels();
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
      assertTrue(actuallyStudents.containsAll(students));
  }
  
  @Test
  void whenRetriveHttpGetRequestStudent_thenExpectViewNameStudentWithAttribute() throws Exception {
      StudentDto studentDto = StudentDtoModelRepository.getModel1();
      int id = 1;
      String httpRequest = "/student?id=" + id;
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
      int id = 1;
      String httpRequest = "/student_edit?id=" + id;
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
      StudentDto studentDto = StudentDtoModelRepository.getModel7();
      String httpRequest = "/student_edit";
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
    
  @Test
  void whenRetriveHttpPostRequestStudentEditAndStudentWithBlankFirstName_thenVerifyErrorResponse() throws Exception {
      StudentDto studentDto = StudentDtoModelRepository.getModel7();
//      studentDto.setId(7);
      studentDto.setFirstName("");
      String httpRequest = "/student_edit";
      this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
              .andExpect(model().attributeHasFieldErrorCode("studentDto", "firstName", "NotBlank"))
              .andDo(print())
              .andReturn();
  }
  
  @Test
  void whenRetriveHttpPostRequestStudentEditAndStudentWithFirstNameLength_thenVerifyErrorResponse() throws Exception {
      StudentDto studentDto = StudentDtoModelRepository.getModel7();
//      studentDto.setId(7);
      studentDto.setFirstName("qwertyuiopasdfghjklzxcvbnm");
      String httpRequest = "/student_edit";
      this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
              .andExpect(model().attributeHasFieldErrorCode("studentDto", "firstName", "Length"))
              .andDo(print())
              .andReturn();
  }
  
  @Test
  void whenRetriveHttpPostRequestStudentEditAndStudentWithMiddleNameLength_thenVerifyErrorResponse() throws Exception {
      StudentDto studentDto = StudentDtoModelRepository.getModel7();
//      studentDto.setId(7);
      studentDto.setMiddleName("qwertyuiopasdfghjklzxcvbnm");
      String httpRequest = "/student_edit";
      this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
              .andExpect(model().attributeHasFieldErrorCode("studentDto", "middleName", "Length"))
              .andDo(print())
              .andReturn();
  }
  
  @Test
  void whenRetriveHttpPostRequestStudentEditAndStudentWithLastNameLength_thenVerifyErrorResponse() throws Exception {
      StudentDto studentDto = StudentDtoModelRepository.getModel7();
//      studentDto.setId(7);
      studentDto.setLastName("qwertyuiopasdfghjklzxcvbnm");
      String httpRequest = "/student_edit";
      this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
              .andExpect(model().attributeHasFieldErrorCode("studentDto", "lastName", "Length"))
              .andDo(print())
              .andReturn();
  }
  
  @Test
  void whenRetriveHttpPostRequestStudentEditAndStudentWithGroupTitleLength_thenVerifyErrorResponse() throws Exception {
      StudentDto studentDto = StudentDtoModelRepository.getModel7();
//      studentDto.setId(7);
      studentDto.setGroupTitle("qwertyuiopasdfghjklzxcvbnm");
      String httpRequest = "/student_edit";
      this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
              .andExpect(model().attributeHasFieldErrorCode("studentDto", "groupTitle", "Length"))
              .andDo(print())
              .andReturn();
  }
  
  @Test
  void whenRetriveHttpPostRequestStudentEditAndStudentWithIdFeesMax_thenVerifyErrorResponse() throws Exception {
      StudentDto studentDto = StudentDtoModelRepository.getModel7();
//      studentDto.setId(7);
      studentDto.setIdFees(1212121212);
      String httpRequest = "/student_edit";
      this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
              .andExpect(model().attributeHasFieldErrorCode("studentDto", "idFees", "Max"))
              .andDo(print())
              .andReturn();
  }
  
  @Test
  void whenRetriveHttpPostRequestStudentEditAndStudentWithIdFeesMin_thenVerifyErrorResponse() throws Exception {
      StudentDto studentDto = StudentDtoModelRepository.getModel7();
//      studentDto.setId(7);
      studentDto.setIdFees(12121212);
      String httpRequest = "/student_edit";
      this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
              .andExpect(model().attributeHasFieldErrorCode("studentDto", "idFees", "Min"))
              .andDo(print())
              .andReturn();
  }
  
  @Test
  void whenRetriveHttpPostRequestStudentEditAndStudentWithIdFeesNotUnique_thenVerifyErrorResponse() throws Exception {
      StudentDto studentDto = StudentDtoModelRepository.getModel7();
      studentDto.setId(7);
      int idFeesExist = StudentDtoModelRepository.getModel6().getIdFees();
      studentDto.setIdFees(idFeesExist);
      String httpRequest = "/student_edit";
      this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("studentDto", studentDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_STUDENT_EDIT))
              .andExpect(model().attributeHasErrors("studentDto"))
              .andDo(print())
              .andReturn();
  }
  
    @AfterEach
    public void removeCreatedTables() {
        flyway.clean();
    }
}

package ua.com.foxminded.task.controller.rest;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.foxminded.task.config.TestMvcConfig;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.repository.dto.StudentDtoModelRepository;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.validation.validator.property.unique.Command;

@WebMvcTest(StudentController.class)
@Import(TestMvcConfig.class)
public class StudentRestControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private StudentService studentService;
    @MockBean
    @Qualifier("uniqueValidationCommandMap")
    private Map<String, Command> uniqueValidationCommandMap;

    private static StudentDto STUDENT_DTO1 = StudentDtoModelRepository.getModel1();
    private static StudentDto STUDENT_DTO2 = StudentDtoModelRepository.getModel2();

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void whenPerformStudentsRequest_thenExpectListOfStudent() throws Exception {
        STUDENT_DTO1.setId(1);
        STUDENT_DTO2.setId(2);
        List<StudentDto> studentDtos = Arrays.asList(STUDENT_DTO1, STUDENT_DTO2);

        when(studentService.findAllDto()).thenReturn(studentDtos);

        this.mockMvc.perform(get("/api/students").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("firstName1")))
                .andExpect(jsonPath("$[0].middleName", is("middleName1")))
                .andExpect(jsonPath("$[0].lastName", is("lastName1")))
                .andExpect(jsonPath("$[0].birthday", is("1999-06-25")))
                .andExpect(jsonPath("$[0].idFees", is(111111111)))
                .andExpect(jsonPath("$[0].groupTitle", is("group11")))
                .andExpect(jsonPath("$[0].groupId", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("firstName2")))
                .andExpect(jsonPath("$[1].middleName", is("middleName2")))
                .andExpect(jsonPath("$[1].lastName", is("lastName2")))
                .andExpect(jsonPath("$[1].birthday", is("1998-06-25")))
                .andExpect(jsonPath("$[1].idFees", is(222211111)))
                .andExpect(jsonPath("$[1].groupTitle", is("group11")))
                .andExpect(jsonPath("$[1].groupId", is(1)));
    }

    @Test
    void whenPerformStudentsAndIdRequest_thenExpectStudentById() throws Exception {
        STUDENT_DTO2.setId(2);

        when(studentService.findByIdDto(2)).thenReturn(STUDENT_DTO2);

        this.mockMvc.perform(get("/api/students/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", aMapWithSize(8)))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("firstName2")))
                .andExpect(jsonPath("$.middleName", is("middleName2")))
                .andExpect(jsonPath("$.lastName", is("lastName2")))
                .andExpect(jsonPath("$.birthday", is("1998-06-25")))
                .andExpect(jsonPath("$.idFees", is(222211111)))
                .andExpect(jsonPath("$.groupTitle", is("group11")))
                .andExpect(jsonPath("$.groupId", is(1)));
    }

    @Test
    void whenPerformPostStudentsRequest_thenUpdateStudent() throws Exception {
        String student = "{\"id\":2,\"firstName\":\"firstName2\"," 
                       + "\"middleName\":\"middleName2\"," 
                       + "\"lastName\":\"lastName2\"," 
                       + "\"birthday\":\"1997-06-25\"," 
                       + "\"idFees\":232211111,"
                       + "\"groupTitle\":\"group1\"," 
                       + "\"groupId\":0}";
        StudentDto studentDto = new StudentDto();
        studentDto.setId(2);
        studentDto.setFirstName("firstName2");
        studentDto.setMiddleName("middleName2");
        studentDto.setLastName("lastName2");
        studentDto.setBirthday(LocalDate.parse("1997-06-25"));
        studentDto.setIdFees(232211111);
        studentDto.setGroupTitle(null);
        studentDto.setGroupId(0);

        when(studentService.update(any(StudentDto.class))).thenReturn(studentDto);

        this.mockMvc.perform(post("/api/students").content(student).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(jsonPath("$", aMapWithSize(8)))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("firstName2")))
                .andExpect(jsonPath("$.middleName", is("middleName2")))
                .andExpect(jsonPath("$.lastName", is("lastName2")))
                .andExpect(jsonPath("$.birthday", is("1997-06-25")))
                .andExpect(jsonPath("$.idFees", is(232211111)))
                .andExpect(jsonPath("$.groupTitle", is(nullValue())))
                .andExpect(jsonPath("$.groupId", is(0)));
    }

    @Test
    void whenPerformPostStudentsRequestWithIdZero_thenCreateStudent() throws Exception {
        String student = "{\"id\":0,\"firstName\":\"firstName7\"," 
                       + "\"middleName\":\"middleName7\"," 
                       + "\"lastName\":\"lastName7\"," 
                       + "\"birthday\":\"1997-06-25\"," 
                       + "\"idFees\":232211111,"
                       + "\"groupTitle\":\"group2\"," 
                       + "\"groupId\":3}";
        StudentDto studentDto = new StudentDto();
        studentDto.setId(7);
        studentDto.setFirstName("firstName7");
        studentDto.setMiddleName("middleName7");
        studentDto.setLastName("lastName7");
        studentDto.setBirthday(LocalDate.parse("1997-06-25"));
        studentDto.setIdFees(232211111);
        studentDto.setGroupTitle("group3");
        studentDto.setGroupId(3);

        when(studentService.create(any(StudentDto.class))).thenReturn(studentDto);

        this.mockMvc.perform(post("/api/students").content(student).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(jsonPath("$", aMapWithSize(8)))
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.firstName", is("firstName7")))
                .andExpect(jsonPath("$.middleName", is("middleName7")))
                .andExpect(jsonPath("$.lastName", is("lastName7")))
                .andExpect(jsonPath("$.birthday", is("1997-06-25")))
                .andExpect(jsonPath("$.idFees", is(232211111)))
                .andExpect(jsonPath("$.groupTitle", is("group3")))
                .andExpect(jsonPath("$.groupId", is(3)));
    }

    @Test
    void whenUpdateStudentWithNotCorrectValues_thenExpectError() throws Exception {
        String student = "{\"id\":0,\"firstName\":\"\"," 
                       + "\"middleName\":\"middleName7\"," 
                       + "\"lastName\":\"lastName7\"," 
                       + "\"birthday\":\"1997-06-25\"," 
                       + "\"idFees\":1212121212,"
                       + "\"groupTitle\":\"group2\"," 
                       + "\"groupId\":3}";
        this.mockMvc.perform(post("/api/students").content(student).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.errors", aMapWithSize(2)))
                .andExpect(jsonPath("$.errors.firstName", is("First name can`t be blank!")))
                .andExpect(jsonPath("$.errors.idFees", is("Value is 9 number!")));
    }

    @Test
    void whenUpdateStudentWithNotCorrectValues_thenExpectError2() throws Exception {
        String student = "{\"id\":0,\"firstName\":\"qwertyuiopasdfghjklzxcvbnm\"," 
                       + "\"middleName\":\"qwertyuiopasdfghjklzxcvbnm\"," 
                       + "\"lastName\":\"qwertyuiopasdfghjklzxcvbnm\","
                       + "\"birthday\":\"1997-06-25\"," 
                       + "\"idFees\":12121212," 
                       + "\"groupTitle\":\"qwertyuiopasdfghjklzxcvbnm\"," 
                       + "\"groupId\":3}";
        this.mockMvc.perform(post("/api/students").content(student).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(jsonPath("$.errors", aMapWithSize(5)))
                .andExpect(jsonPath("$.errors.firstName", is("Maximum length of first name is 20!")))
                .andExpect(jsonPath("$.errors.middleName", is("Maximum length of middle name is 20!")))
                .andExpect(jsonPath("$.errors.lastName", is("Maximum length of last name is 20!")))
                .andExpect(jsonPath("$.errors.idFees", is("Value is 9 number!")))
                .andExpect(jsonPath("$.errors.groupTitle", is("Maximum length of title group is 20!")));
    }
}

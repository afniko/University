package ua.com.foxminded.task.controller.rest;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.foxminded.task.dao.GroupRepository;
import ua.com.foxminded.task.dao.StudentRepository;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.StudentModelRepository;

@SpringBootTest
public class ITStudentRestControllerTest {

    @Autowired
    private Flyway flyway;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;

    private static final Group GROUP1 = GroupModelRepository.getModel1();
    private static final Group GROUP2 = GroupModelRepository.getModel2();
    private static final Group GROUP3 = GroupModelRepository.getModel3();
    private static final Group GROUP4 = GroupModelRepository.getModel4();

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
    void whenPerformStudentsRequest_thenExpectListOfStudent() throws Exception {
        this.mockMvc.perform(get("/api/students").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("firstName1")))
                .andExpect(jsonPath("$[0].middleName", is("middleName1")))
                .andExpect(jsonPath("$[0].lastName", is("lastName1")))
                .andExpect(jsonPath("$[0].birthday", is("1999-06-25")))
                .andExpect(jsonPath("$[0].idFees", is(111111111)))
                .andExpect(jsonPath("$[0].groupTitle", is("group1")))
                .andExpect(jsonPath("$[0].idGroup", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("firstName2")))
                .andExpect(jsonPath("$[1].middleName", is("middleName2")))
                .andExpect(jsonPath("$[1].lastName", is("lastName2")))
                .andExpect(jsonPath("$[1].birthday", is("1998-06-25")))
                .andExpect(jsonPath("$[1].idFees", is(222211111)))
                .andExpect(jsonPath("$[1].groupTitle", is("group1")))
                .andExpect(jsonPath("$[1].idGroup", is(1)));
    }

    @Test
    void whenPerformStudentsAndIdRequest_thenExpectStudentById() throws Exception {
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
                  .andExpect(jsonPath("$.groupTitle", is("group1")))
                  .andExpect(jsonPath("$.idGroup", is(1)));
    }

  @Test
  void whenPerformPostStudentsRequest_thenUpdateStudent() throws Exception {
      String student = "{\"id\":2,\"firstName\":\"firstName2\","
                     + "\"middleName\":\"middleName2\","
                     + "\"lastName\":\"lastName2\","
                     + "\"birthday\":\"1997-06-25\","
                     + "\"idFees\":232211111,"
                     + "\"groupTitle\":\"group1\","
                     + "\"idGroup\":0}";
      this.mockMvc.perform(post("/api/students")
                .content(student)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", aMapWithSize(8)))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("firstName2")))
                .andExpect(jsonPath("$.middleName", is("middleName2")))
                .andExpect(jsonPath("$.lastName", is("lastName2")))
                .andExpect(jsonPath("$.birthday", is("1997-06-25")))
                .andExpect(jsonPath("$.idFees", is(232211111)))
                .andExpect(jsonPath("$.groupTitle", is(nullValue())))
                .andExpect(jsonPath("$.idGroup", is(0)));
  }

  @Test
  void whenPerformPostStudentsRequestWithIdZero_thenCreateStudent() throws Exception {
      String student = "{\"id\":0,\"firstName\":\"firstName7\","
                     + "\"middleName\":\"middleName7\","
                     + "\"lastName\":\"lastName7\","
                     + "\"birthday\":\"1997-06-25\","
                     + "\"idFees\":232211111,"
                     + "\"groupTitle\":\"group2\","
                     + "\"idGroup\":3}";
      this.mockMvc.perform(post("/api/students")
                .content(student)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", aMapWithSize(8)))
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.firstName", is("firstName7")))
                .andExpect(jsonPath("$.middleName", is("middleName7")))
                .andExpect(jsonPath("$.lastName", is("lastName7")))
                .andExpect(jsonPath("$.birthday", is("1997-06-25")))
                .andExpect(jsonPath("$.idFees", is(232211111)))
                .andExpect(jsonPath("$.groupTitle", is("group3")))
                .andExpect(jsonPath("$.idGroup", is(3)));
  }

  @Test
  void whenUpdateStudentWithNotCorrectValues_thenExpectError() throws Exception {
      String student = "{\"id\":0,\"firstName\":\"\","
                     + "\"middleName\":\"middleName7\","
                     + "\"lastName\":\"lastName7\","
                     + "\"birthday\":\"1997-06-25\","
                     + "\"idFees\":1212121212,"
                     + "\"groupTitle\":\"group2\","
                     + "\"idGroup\":3}";
      this.mockMvc.perform(post("/api/students")
                .content(student)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
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
                     + "\"idGroup\":3}";
      this.mockMvc.perform(post("/api/students")
                .content(student)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.errors", aMapWithSize(5)))
                .andExpect(jsonPath("$.errors.firstName", is("Maximum length of first name is 20!")))
                .andExpect(jsonPath("$.errors.middleName", is("Maximum length of middle name is 20!")))
                .andExpect(jsonPath("$.errors.lastName", is("Maximum length of last name is 20!")))
                .andExpect(jsonPath("$.errors.idFees", is("Value is 9 number!")))
                .andExpect(jsonPath("$.errors.groupTitle", is("Maximum length of title group is 20!")));
  }

  @Test
  void whenUpdateStudentWithNotCorrectValues_thenExpectError3() throws Exception {
      String student = "{\"id\":6,\"firstName\":\"firstName2\","
                     + "\"middleName\":\"middleName7\","
                     + "\"lastName\":\"lastName7\","
                     + "\"birthday\":\"1997-06-25\","
                     + "\"idFees\":111111111,"
                     + "\"groupTitle\":\"group2\","
                     + "\"idGroup\":2}";
      this.mockMvc.perform(post("/api/students")
                .content(student)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.errors", aMapWithSize(2)))
                .andExpect(jsonPath("$.errors.idFees", is("Id fees is already exists!")))
                .andExpect(jsonPath("$.errors.idGroup", is("Max participant in group!")));
  }
  
    @AfterEach
    public void removeCreatedTables() {
        flyway.clean();
    }
}

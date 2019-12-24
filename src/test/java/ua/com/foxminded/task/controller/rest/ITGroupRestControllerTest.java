package ua.com.foxminded.task.controller.rest;
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
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;

@SpringBootTest
public class ITGroupRestControllerTest {

    @Autowired
    private Flyway flyway;
    @Autowired
    private GroupRepository groupRepository;

    private static final Group GROUP1 = GroupModelRepository.getModel1();
    private static final Group GROUP2 = GroupModelRepository.getModel2();

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        flyway.migrate();
        groupRepository.save(GROUP1);
        groupRepository.saveAndFlush(GROUP2);
    }

    @Test
    void whenPerformGroupsRequest_thenExpectListOfGroups() throws Exception {
        this.mockMvc.perform(get("/api/groups").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("group1")))
                .andExpect(jsonPath("$[0].yearEntry", is(2016)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("group2")))
                .andExpect(jsonPath("$[1].yearEntry", is(2018)));
    }

    @Test
    void whenPerformGroupAndIdRequest_thenExpectGroupById() throws Exception {
        this.mockMvc.perform(get("/api/groups/2").accept(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andDo(print())
                  .andExpect(jsonPath("$", aMapWithSize(3)))
                  .andExpect(jsonPath("$.id", is(2)))
                  .andExpect(jsonPath("$.title", is("group2")))
                  .andExpect(jsonPath("$.yearEntry", is(2018)));
    }

  @Test
  void whenPerformPostGroupsRequest_thenUpdateGroup() throws Exception {
      String group = "{\"id\":2,"
                   + "\"title\":\"group2\","
                   + "\"yearEntry\":2001}";
      this.mockMvc.perform(post("/api/groups")
                .content(group)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("group2")))
                .andExpect(jsonPath("$.yearEntry", is(2001)));
  }

  @Test
  void whenPerformPostGroupsRequestWithIdZero_thenCreateGroup() throws Exception {
      String group = "{\"id\":0,"
                   + "\"title\":\"group3\","
                   + "\"yearEntry\":2013}";
      this.mockMvc.perform(post("/api/groups")
                .content(group)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("group3")))
                .andExpect(jsonPath("$.yearEntry", is(2013)));
  }
 
  @Test
  void whenUpdateGroupWithNotCorrectValues_thenExpectError() throws Exception {
      String group = "{\"id\":2,"
                   + "\"title\":\"group1\","
                   + "\"yearEntry\":99999}";
      this.mockMvc.perform(post("/api/groups")
                .content(group)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.errors", aMapWithSize(2)))
                .andExpect(jsonPath("$.errors.title", is("Title is already exists!")))
                .andExpect(jsonPath("$.errors.yearEntry", is("Year of entry is not correct!")));
  }
  
  @Test
  void whenUpdateGroupWithNotCorrectValues_thenExpectError2() throws Exception {
      String group = "{\"id\":2,"
                   + "\"title\":\"qwertyuiopasdfghjklzxcvbnm\","
                   + "\"yearEntry\":999}";
      this.mockMvc.perform(post("/api/groups")
                .content(group)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.errors", aMapWithSize(2)))
                .andExpect(jsonPath("$.errors.title", is("Maximum length is 20!")))
                .andExpect(jsonPath("$.errors.yearEntry", is("Year of entry is not correct!")));
  }
  
  @Test
  void whenUpdateGroupWithNotCorrectTitle_thenExpectError() throws Exception {
      String group = "{\"id\":2,"
                   + "\"title\":\"\","
                   + "\"yearEntry\":2018}";
      this.mockMvc.perform(post("/api/groups")
                .content(group)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.errors", aMapWithSize(1)))
                .andExpect(jsonPath("$.errors.title", is("Title can`t be blank!")));
  }
  
    @AfterEach
    public void removeCreatedTables() {
        flyway.clean();
    }
}

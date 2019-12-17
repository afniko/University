package ua.com.foxminded.task.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
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
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;

@SpringBootTest
public class ITGroupControllerTest {

    @Autowired
    private Flyway flyway;
    @Autowired
    private GroupRepository groupRepository;

    private static final String PATH_HTML_GROUP = "group/group";
    private static final String PATH_HTML_GROUPS = "group/groups";
    private static final String PATH_HTML_GROUP_EDIT = "group/group_edit";
    private static final String ATTRIBUTE_HTML_TITLE = "title";
    private static final String ATTRIBUTE_HTML_GROUP = "groupDto";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String ATTRIBUTE_HTML_SUCCESS_MESSAGE = "successMessage";
    private static final String ATTRIBUTE_HTML_ERROR_MESSAGE = "errorMessage";
    private static final String EXPECTED_ERROR_MESSAGE = "You enter incorrect data!";
    private static final Group GROUP1 = GroupModelRepository.getModel1();
    private static final Group GROUP2 = GroupModelRepository.getModel2();
    private static final Group GROUP3 = GroupModelRepository.getModel3();
    private static final Group GROUP4 = GroupModelRepository.getModel4();
    private static final GroupDto GROUP_DTO1 = GroupDtoModelRepository.getModel1();
    private static final GroupDto GROUP_DTO2 = GroupDtoModelRepository.getModel2();
    private static final GroupDto GROUP_DTO3 = GroupDtoModelRepository.getModel3();
    private static final GroupDto GROUP_DTO4 = GroupDtoModelRepository.getModel4();

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        flyway.migrate();
        groupRepository.save(GROUP1);
        groupRepository.save(GROUP2);
        groupRepository.save(GROUP3);
        groupRepository.saveAndFlush(GROUP4);
    }

    @Test
    void whenRetriveAllGroups_thenExpectListOfGroups() throws Exception {
        List<GroupDto> groups = Arrays.asList(GROUP_DTO1, GROUP_DTO2, GROUP_DTO3);
        MvcResult mvcResult =  this.mockMvc.perform(get("/groups").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUPS))
                .andDo(print())
                .andReturn();
        String expectedTitle = "Groups";
        String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
        assertEquals(actuallyTitle, expectedTitle);
        List<GroupDto> actuallyGroups = (List<GroupDto>) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUPS);
        assertTrue(actuallyGroups.containsAll(groups));
    }

  @Test
  void whenRetriveTheGroup_thenExpectGroupById() throws Exception {
      GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
      int id = groupDto.getId();
      String httpRequest = "/group?id=" + id;
      MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_GROUP))
              .andDo(print())
              .andReturn();
      String expectedTitle = "Group";
      String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
      assertEquals(actuallyTitle, expectedTitle);
      GroupDto actuallyGroup = (GroupDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUP);
      assertEquals(groupDto, actuallyGroup);
  }

  @Test
  void whenRetriveEditExistsGroup_thenExpectFormWithGroupField() throws Exception {
      GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
      int id = groupDto.getId();
      String httpRequest = "/group_edit?id=" + id;
      MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_GROUP_EDIT))
              .andDo(print())
              .andReturn();
      String expectedTitle = "Group edit";
      String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
      assertEquals(actuallyTitle, expectedTitle);
      GroupDto actuallyGroup = (GroupDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUP);
      assertEquals(groupDto, actuallyGroup);
  }

  @Test
  void whenSubmitEditFormGroupWithId_thenUpdateGroup() throws Exception {
      GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
      String httpRequest = "/group_edit";
      MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_GROUP))
              .andDo(print())
              .andReturn();
      String expectedTitle = "Group edit";
      String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
      assertEquals(actuallyTitle, expectedTitle);
      GroupDto actuallyGroup = (GroupDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUP);
      assertEquals(groupDto, actuallyGroup);
      String expectedSuccessMessage = "Record group was updated!";
      String actuallySuccessMessage = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_SUCCESS_MESSAGE).toString();
      assertEquals(actuallySuccessMessage, expectedSuccessMessage);
  }

  @Test
  void whenSubmitEditFormGroupWithoutId_thenCreateGroup() throws Exception {
      GroupDto groupDto = GroupDtoModelRepository.getModel6();
      String httpRequest = "/group_edit";
      MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_GROUP))
              .andDo(print())
              .andReturn();
      String expectedTitle = "Group edit";
      String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
      assertEquals(actuallyTitle, expectedTitle);
      GroupDto actuallyGroup = (GroupDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUP);
      assertEquals(groupDto, actuallyGroup);
      String expectedSuccessMessage = "Record group was created!";
      String actuallySuccessMessage = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_SUCCESS_MESSAGE).toString();
      assertEquals(actuallySuccessMessage, expectedSuccessMessage);
  }
  
  @Test
  void whenUpdateGroupWithNotCorrectValues_thenExpectError() throws Exception {
      GroupDto groupDto = GROUP_DTO4;
      groupDto.setId(1);
      groupDto.setTitle(GROUP_DTO2.getTitle());
      groupDto.setYearEntry(99999);
      String httpRequest = "/group_edit";
      MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_GROUP_EDIT))
              .andExpect(model().attributeHasFieldErrorCode("groupDto", "title", "GroupTitleUnique"))
              .andExpect(model().attributeHasFieldErrorCode("groupDto", "yearEntry", "Max"))
              .andDo(print())
              .andReturn();
        
        String actuallyErrorMessage = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE).toString();
        assertEquals(EXPECTED_ERROR_MESSAGE, actuallyErrorMessage);
    }
  
  @Test
  void whenUpdateGroupWithNotCorrectValues_thenExpectError2() throws Exception {
      GroupDto groupDto = GROUP_DTO4;
      groupDto.setId(1);
      groupDto.setTitle("qwertyuiopasdfghjklzxcvbnm");
      groupDto.setYearEntry(999);
      String httpRequest = "/group_edit";
      MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_GROUP_EDIT))
              .andExpect(model().attributeHasFieldErrorCode("groupDto", "yearEntry", "Min"))
              .andExpect(model().attributeHasFieldErrorCode("groupDto", "title", "Length"))
              .andDo(print())
              .andReturn();
      
      String actuallyErrorMessage = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE).toString();
      assertEquals(EXPECTED_ERROR_MESSAGE, actuallyErrorMessage);
  }
  
  @Test
  void whenUpdateGroupWithNotCorrectTitle_thenExpectError() throws Exception {
      GroupDto groupDto = GROUP_DTO4;
      groupDto.setId(1);
      groupDto.setTitle("");
      String httpRequest = "/group_edit";
      MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
              .andExpect(status().isOk())
              .andExpect(view().name(PATH_HTML_GROUP_EDIT))
              .andExpect(model().attributeHasFieldErrorCode("groupDto", "title", "NotBlank"))
              .andDo(print())
              .andReturn();
      
      String actuallyErrorMessage = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE).toString();
      assertEquals(EXPECTED_ERROR_MESSAGE, actuallyErrorMessage);
  }
  
    @AfterEach
    public void removeCreatedTables() {
        flyway.clean();
    }
}

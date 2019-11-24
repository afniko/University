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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.com.foxminded.task.config.TestConfig;
import ua.com.foxminded.task.config.spring.mvc.WebConfig;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.impl.GroupServiceImpl;

@SpringJUnitWebConfig(classes = { WebConfig.class, TestConfig.class })
public class GroupControllerTest {

    private MockMvc mockMvc;

    private static final String PATH_HTML_GROUP = "group/group";
    private static final String PATH_HTML_GROUPS = "group/groups";
    private static final String PATH_HTML_GROUP_EDIT = "group/group_edit";
    private static final String ATTRIBUTE_HTML_TITLE = "title";
    private static final String ATTRIBUTE_HTML_GROUP = "group";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String ATTRIBUTE_HTML_SUCCESS_MESSAGE = "successMessage";

    private GroupService groupService = mock(GroupServiceImpl.class);
    private GroupController groupController = new GroupController(groupService);

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    void whenRetriveHttpGetRequestGroups_thenExpectViewNameGroupsWithAttribute() throws Exception {
        List<GroupDto> groups = GroupDtoModelRepository.getModels();
        String httpRequest = "/groups";
        when(groupService.findAllDto()).thenReturn(groups);
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUPS))
                .andDo(print())
                .andReturn();
        String expectedTitle = "Groups";
        String actuallyTitle = mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_TITLE).toString();
        assertEquals(actuallyTitle, expectedTitle);
        List<GroupDto> actuallyGroups = (List<GroupDto>) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUPS);
        assertEquals(groups, actuallyGroups);
    }
    
    @Test
    void whenRetriveHttpGetRequestGroup_thenExpectViewNameGroupWithAttribute() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        int id = groupDto.getId();
        String httpRequest = "/group?id=" + id;
        when(groupService.findByIdDto(groupDto.getId())).thenReturn(groupDto);
        
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
    void whenRetriveHttpGetRequestGroupEdit_thenExpectViewNameGroupeditWithAttribute() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        int id = groupDto.getId();
        String httpRequest = "/group_edit?id=" + id;
        when(groupService.findByIdDto(groupDto.getId())).thenReturn(groupDto);
        
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
    void whenRetriveHttpPostRequestGroupEditAndGroupWithId_thenExpectViewNameGroupeditWithAttribute() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        String httpRequest = "/group_edit";
        when(groupService.update(groupDto)).thenReturn(groupDto);
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
    void whenRetriveHttpPostRequestGroupEditAndGroupWithoutId_thenExpectViewNameGroupeditWithAttribute() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModel1();
        String httpRequest = "/group_edit";
        when(groupService.create(groupDto)).thenReturn(groupDto);
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
}

package ua.com.foxminded.task.controller;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.validation.validator.GroupTitleUniqueValidator;

@ExtendWith(SpringExtension.class)
public class GroupControllerTest {
    
    private MockMvc mockMvc;
    private GroupController groupController;
@MockBean
private GroupTitleUniqueValidator validator;
    @MockBean
    private GroupService groupService;
    
    @MockBean
    private Logger logger;
    
    private static final String PATH_HTML_GROUP = "group/group";
    private static final String PATH_HTML_GROUPS = "group/groups";
    private static final String PATH_HTML_GROUP_EDIT = "group/group_edit";
    private static final String ATTRIBUTE_HTML_GROUP = "groupDto";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String ATTRIBUTE_HTML_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_HTML_TITLE = "title";
    
    @BeforeEach
    public void InitBinder() {
        groupController = new GroupController(logger, groupService);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }
    
    @Test
    void whenRetriveAllGroups_thenExpectListOfGroups() throws Exception {
        String expectedTitle = "Groups";
        List<GroupDto> groupDtos = GroupDtoModelRepository.getModels();
        
        when(groupService.findAllDto()).thenReturn(groupDtos);
        
        this.mockMvc.perform(get("/groups").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_HTML_TITLE, equalTo(expectedTitle)))
                .andExpect(model().attribute(ATTRIBUTE_HTML_GROUPS, equalTo(groupDtos)))
                .andExpect(forwardedUrl(PATH_HTML_GROUPS))
                .andDo(print());
    }
    
    @Test
    void whenInvokeAllGroupsWithNoEntity_thenExpectErrorsMessage() throws Exception {
        String expectedErrorMessage = "Problem with finding group";
        doThrow(EntityNotFoundException.class).when(groupService).findAllDto();
        
        this.mockMvc.perform(get("/groups").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_HTML_ERROR_MESSAGE, equalTo(expectedErrorMessage)))
                .andExpect(forwardedUrl(PATH_HTML_GROUPS))
                .andDo(print());
    }
    
    @Test
    void whenRetriveTheGroup_thenExpectGroupById() throws Exception {
        String expectedTitle = "Group";
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        int id = groupDto.getId();
        String httpRequest = "/group?id=" + id;

        when(groupService.findByIdDto(id)).thenReturn(groupDto);

        this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_HTML_TITLE, equalTo(expectedTitle)))
                .andExpect(model().attribute(ATTRIBUTE_HTML_GROUP, equalTo(groupDto)))
                .andExpect(forwardedUrl(PATH_HTML_GROUP))
                .andDo(print());
    }
    
    @Test
    void whenInvokeByBlankId_thenErrorMessage() throws Exception {
        String expectedErrorMessage = "You id is blank";
        String httpRequest = "/group?id=";

        this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_HTML_ERROR_MESSAGE, equalTo(expectedErrorMessage)))
                .andExpect(forwardedUrl(PATH_HTML_GROUP))
                .andDo(print());
    }
    
    @Test
    void whenInvokeGroupWithNoEntity_thenErrorMessage() throws Exception {
        int id = 1;
        String expectedErrorMessage = "Group by id#" + id + " not found!";
        String httpRequest = "/group?id=" + id;
        
        doThrow(EntityNotFoundException.class).when(groupService).findByIdDto(id);
        
        this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_HTML_ERROR_MESSAGE, equalTo(expectedErrorMessage)))
                .andExpect(forwardedUrl(PATH_HTML_GROUP))
                .andDo(print());
    }
    
    @Test
    void whenInvokeGroupWithIncorrectNumberFormat_thenErrorMessage() throws Exception {
        int id = 1;
        String expectedErrorMessage = "Group id# must be numeric!";
        String httpRequest = "/group?id=" + id;
        
        doThrow(NumberFormatException.class).when(groupService).findByIdDto(id);
        
        this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_HTML_ERROR_MESSAGE, equalTo(expectedErrorMessage)))
                .andExpect(forwardedUrl(PATH_HTML_GROUP))
                .andDo(print());
    }
    
    @Test
    void whenRetriveEditExistsGroup_thenExpectFormWithGroupField() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        int id = groupDto.getId();
        String expectedTitle = "Group edit";
        String httpRequest = "/group_edit?id=" + id;

        when(groupService.findByIdDto(id)).thenReturn(groupDto);

        this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_HTML_TITLE, equalTo(expectedTitle)))
                .andExpect(model().attribute(ATTRIBUTE_HTML_GROUP, equalTo(groupDto)))
                .andExpect(forwardedUrl(PATH_HTML_GROUP_EDIT))
                .andDo(print());
    }
    
    @Test
    void whenInvokeEditGroupWithNoEntityNumber_thenExpectErrorMessage() throws Exception {
        int id = 1;
        String expectedErrorMessage = "Problem with finding group";
        String httpRequest = "/group_edit?id=" + id;
        
        doThrow(EntityNotFoundException.class).when(groupService).findByIdDto(id);
        
        this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_HTML_ERROR_MESSAGE, equalTo(expectedErrorMessage)))
                .andExpect(forwardedUrl(PATH_HTML_GROUP_EDIT))
                .andDo(print());
    }
}

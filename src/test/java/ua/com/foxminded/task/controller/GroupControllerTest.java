package ua.com.foxminded.task.controller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
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

import javax.persistence.EntityNotFoundException;

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
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;

@WebMvcTest(GroupController.class)
@Import(TestMvcConfig.class)
public class GroupControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;
    
    private static final String PATH_HTML_GROUP = "group/group";
    private static final String PATH_HTML_GROUPS = "group/groups";
    private static final String PATH_HTML_GROUP_EDIT = "group/group_edit";
    private static final String ATTRIBUTE_HTML_GROUP = "groupDto";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String ATTRIBUTE_HTML_ERROR_MESSAGE = "errorMessage";
    
    @Test
    void whenRetriveAllGroups_thenExpectListOfGroups() throws Exception {
        String expectedTitle = "Groups";
        List<GroupDto> groupDtos = GroupDtoModelRepository.getModels();
        
        when(groupService.findAllDto()).thenReturn(groupDtos);
        
        MvcResult mvcResult = this.mockMvc.perform(get("/groups").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUPS))
                .andExpect(content().string(allOf(containsString("<title>" + expectedTitle + "</title>"))))
                .andDo(print())
                .andReturn();
        List<GroupDto> actuallyGroups = (List<GroupDto>) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUPS);
        assertTrue(actuallyGroups.containsAll(groupDtos));
    }
    
    @Test
    void whenInvokeAllGroupsWithNoEntity_thenExpectErrorsMessage() throws Exception {
        String expectedErrorMessage = "Problem with finding group";
        doThrow(EntityNotFoundException.class).when(groupService).findAllDto();
        
        MvcResult mvcResult = this.mockMvc.perform(get("/groups").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUPS))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenRetriveTheGroup_thenExpectGroupById() throws Exception {
        String expectedTitle = "Group";
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        int id = groupDto.getId();
        String httpRequest = "/group?id=" + id;

        when(groupService.findByIdDto(id)).thenReturn(groupDto);

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP))
                .andExpect(content().string(allOf(containsString("<title>" + expectedTitle + "</title>"))))
                .andDo(print())
                .andReturn();
        GroupDto actuallyGroup = (GroupDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUP);
        assertThat(groupDto).isEqualTo( actuallyGroup);
    }
    
    @Test
    void whenInvokeByBlankId_thenErrorMessage() throws Exception {
        String expectedErrorMessage = "You id is blank";
        String httpRequest = "/group?id=";

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String)  mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenInvokeGroupWithNoEntity_thenErrorMessage() throws Exception {
        int id = 1;
        String expectedErrorMessage = "Group by id#" + id + " not found!";
        String httpRequest = "/group?id=" + id;
        
        doThrow(EntityNotFoundException.class).when(groupService).findByIdDto(id);
        
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String)  mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenInvokeGroupWithIncorrectNumberFormat_thenErrorMessage() throws Exception {
        int id = 1;
        String expectedErrorMessage = "Group id# must be numeric!";
        String httpRequest = "/group?id=" + id;
        
        doThrow(NumberFormatException.class).when(groupService).findByIdDto(id);
        
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String)  mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenRetriveEditExistsGroup_thenExpectFormWithGroupField() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        int id = groupDto.getId();
        String expectedTitle = "Group edit";
        String httpRequest = "/group_edit?id=" + id;

        when(groupService.findByIdDto(id)).thenReturn(groupDto);

        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP_EDIT))
                .andExpect(content().string(allOf(containsString("<title>" + expectedTitle + "</title>"))))
                .andDo(print())
                .andReturn();
        GroupDto actuallyGroup = (GroupDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUP);
        assertThat(groupDto).isEqualTo(actuallyGroup);
    }
    
    @Test
    void whenInvokeEditGroupWithNoEntityNumber_thenExpectErrorMessage() throws Exception {
        int id = 1;
        String expectedErrorMessage = "Problem with finding group";
        String httpRequest = "/group_edit?id=" + id;
        
        doThrow(EntityNotFoundException.class).when(groupService).findByIdDto(id);
        
        MvcResult mvcResult = this.mockMvc.perform(get(httpRequest).accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP_EDIT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String)  mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenSubmitEditFormGroupWithId_thenUpdateGroup() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        String httpRequest = "/group_edit";
        String expectedTitle = "Group edit";
        String expectedSuccessMessage = "Record group was updated!";

        when(groupService.update(groupDto)).thenReturn(groupDto);

        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP))
                .andExpect(content().string(allOf(
                        containsString("<title>" + expectedTitle + "</title>"), 
                        containsString("<div class=\"alert alert-success\">" + expectedSuccessMessage + "</div>")
                        )))
                .andDo(print())
                .andReturn();
        GroupDto actuallyGroup = (GroupDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUP);
        assertThat(groupDto).isEqualTo(actuallyGroup);
    }
    
    @Test
    void whenSubmitEditFormGroupWithoutId_thenCreateGroup() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModel1();
        String httpRequest = "/group_edit";
        String expectedTitle = "Group edit";
        String expectedSuccessMessage = "Record group was created!";

        when(groupService.create(groupDto)).thenReturn(groupDto);

        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP))
                .andExpect(content().string(allOf(
                        containsString("<title>" + expectedTitle + "</title>"), 
                        containsString("<div class=\"alert alert-success\">" + expectedSuccessMessage + "</div>")
                        )))
                .andDo(print())
                .andReturn();
        GroupDto actuallyGroup = (GroupDto) mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_GROUP);
        assertThat(groupDto).isEqualTo(actuallyGroup);
    }
    
    @Test
    void whenCreateExistsGroup_thenExpectErrorMessage() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModel1();
        String httpRequest = "/group_edit";
        String expectedErrorMessage = "Record group was not created! The record already exists!";
        
        doThrow(EntityAlreadyExistsException.class).when(groupService).create(groupDto);
        
        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP_EDIT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String)  mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenUpdateNotFountEntity_thenExpectErrorMessage() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        String httpRequest = "/group_edit";
        String expectedErrorMessage = "Group " + groupDto + " not found!";
        
        doThrow(EntityNotFoundException.class).when(groupService).update(groupDto);
        
        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP_EDIT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String)  mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
    @Test
    void whenCreateNotValidEntity_thenExpectErrorMessage() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModel1();
        String httpRequest = "/group_edit";
        String expectedErrorMessage = "Record group was not updated/created! The data is not valid!";
        
        doThrow(EntityNotValidException.class).when(groupService).create(groupDto);
        
        MvcResult mvcResult = this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP_EDIT))
                .andDo(print())
                .andReturn();
        String actuallyErrorMessage = (String)  mvcResult.getRequest().getAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE);
        assertThat(expectedErrorMessage).isEqualTo(actuallyErrorMessage);
    }
    
}

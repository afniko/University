package ua.com.foxminded.task.controller;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
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
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;
import ua.com.foxminded.task.service.GroupService;

@WebMvcTest(GroupController.class)
@Import(TestMvcConfig.class)
public class GroupControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    private static final String PATH_HTML_GROUP = "group/group";
    private static final String PATH_HTML_GROUPS = "group/groups";
    private static final String PATH_HTML_GROUP_EDIT = "group/group_edit";
    private static final String ATTRIBUTE_HTML_GROUP = "groupDto";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String EXPECTED_ERROR_MESSAGE = "You enter incorrect data!";
    private static final GroupDto GROUP_DTO1 = GroupDtoModelRepository.getModel1();
    private static final GroupDto GROUP_DTO2 = GroupDtoModelRepository.getModel2();
    private static final GroupDto GROUP_DTO3 = GroupDtoModelRepository.getModel3();
    private static final GroupDto GROUP_DTO4 = GroupDtoModelRepository.getModel4();

    @Test
    void whenRetriveAllGroups_thenExpectListOfGroups() throws Exception {
        String expectedTitle = "Groups";
        List<GroupDto> groupDtos = Arrays.asList(GROUP_DTO1, GROUP_DTO2, GROUP_DTO3);
        
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
        assertEquals(groupDto, actuallyGroup);
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
        assertEquals(groupDto, actuallyGroup);
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
        assertEquals(groupDto, actuallyGroup);
    }

    @Test
    void whenSubmitEditFormGroupWithoutId_thenCreateGroup() throws Exception {
        GroupDto groupDto = GroupDtoModelRepository.getModel6();
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
        assertEquals(groupDto, actuallyGroup);
    }

    @Test
    void whenUpdateGroupWithNotCorrectValues_thenExpectError() throws Exception {
        GroupDto groupDto = GROUP_DTO4;
        groupDto.setId(1);
        groupDto.setYearEntry(99999);
        String httpRequest = "/group_edit";
        this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP_EDIT))
                .andExpect(content().string(allOf(containsString("<div>" + EXPECTED_ERROR_MESSAGE + "</div>"))))
                .andExpect(model().attributeHasFieldErrorCode("groupDto", "yearEntry", "Max"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void whenUpdateGroupWithNotCorrectValues_thenExpectError2() throws Exception {
        GroupDto groupDto = GROUP_DTO4;
        groupDto.setId(1);
        groupDto.setTitle("qwertyuiopasdfghjklzxcvbnm");
        groupDto.setYearEntry(999);
        String httpRequest = "/group_edit";
        this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP_EDIT))
                .andExpect(content().string(allOf(containsString("<div>" + EXPECTED_ERROR_MESSAGE + "</div>"))))
                .andExpect(model().attributeHasFieldErrorCode("groupDto", "yearEntry", "Min"))
                .andExpect(model().attributeHasFieldErrorCode("groupDto", "title", "Length"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void whenUpdateGroupWithNotCorrectTitle_thenExpectError() throws Exception {
        GroupDto groupDto = GROUP_DTO4;
        groupDto.setId(1);
        groupDto.setTitle("");
        String httpRequest = "/group_edit";
        this.mockMvc.perform(post(httpRequest).accept(MediaType.TEXT_HTML_VALUE).flashAttr("groupDto", groupDto))
                .andExpect(status().isOk())
                .andExpect(view().name(PATH_HTML_GROUP_EDIT))
                .andExpect(content().string(allOf(containsString("<div>" + EXPECTED_ERROR_MESSAGE + "</div>"))))
                .andExpect(model().attributeHasFieldErrorCode("groupDto", "title", "NotBlank"))
                .andDo(print())
                .andReturn();
    }
}

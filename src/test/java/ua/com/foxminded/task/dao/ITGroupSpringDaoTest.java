package ua.com.foxminded.task.dao;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;

@SpringBootTest
@FlywayTest
public class ITGroupSpringDaoTest {
    
    
    private static final String PATH_HTML_GROUPS = "group/groups";
    private static final String ATTRIBUTE_HTML_TITLE = "title";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final Group GROUP1 = GroupModelRepository.getModel1();
    private static final Group GROUP2 = GroupModelRepository.getModel2();
    private static final Group GROUP3 = GroupModelRepository.getModel3();
    private static final Group GROUP4 = GroupModelRepository.getModel4();
    private static final GroupDto GROUP_DTO1 = GroupDtoModelRepository.getModel1();
    private static final GroupDto GROUP_DTO2 = GroupDtoModelRepository.getModel2();
    private static final GroupDto GROUP_DTO3 = GroupDtoModelRepository.getModel3();
    private static final GroupDto GROUP_DTO4 = GroupDtoModelRepository.getModel4();

    private MockMvc mockMvc;

//    @BeforeEach
//    public void createRecords(@Autowired GroupDao groupDao) {
//        groupDao.create(GROUP1);
//        groupDao.create(GROUP2);
//        groupDao.create(GROUP3);
//        groupDao.create(GROUP4);
//    }

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void whenRetriveHttpGetRequestGroups_thenExpectViewNameGroupsAndAllGroupsFromDataBase() throws Exception {
        
        List<GroupDto> groups = Arrays.asList(GROUP_DTO1, GROUP_DTO2, GROUP_DTO3, GROUP_DTO4);
        MvcResult mvcResult =  this.mockMvc.perform(get("/groups").accept(MediaType.APPLICATION_JSON))
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

//    @AfterEach
//    public void removeCreatedTables(@Autowired Flyway flyway) {
//        flyway.clean();
//    }
}

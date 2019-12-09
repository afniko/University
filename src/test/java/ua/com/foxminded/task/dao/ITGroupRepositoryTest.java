package ua.com.foxminded.task.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;

@SpringBootTest
public class ITGroupRepositoryTest {

    @Autowired private GroupRepository groupRepository;
    @Autowired private Flyway flyway;

    private static final Group GROUP11 = GroupModelRepository.getModel11();
    private static final Group GROUP12 = GroupModelRepository.getModel12();
    private static final Group GROUP13 = GroupModelRepository.getModel13();

    @BeforeEach
    public void setup() {
        flyway.migrate();
        groupRepository.save(GROUP11);
        groupRepository.save(GROUP12);
        groupRepository.saveAndFlush(GROUP13);
    }

    @Test
    public void WhenPutAtTableDbGroupObjects_thenGetThisObjectsFindById() {
        int id = GROUP12.getId();
        assertTrue(groupRepository.findById(id).get().equals(GROUP12));
    }

    @Test
    public void WhenPutAtTableDbGroupObjects_thenGetThisObjects() {
        assertTrue(groupRepository.findAll().containsAll(Arrays.asList(GROUP11, GROUP12, GROUP13)));
    }

    @Test
    public void WhenUpdateAtTableDbGroupObject_thenGetNewObject() {
        String titleExpected = "test_title_text";
        Group group = groupRepository.findById(1).get();
        group.setTitle(titleExpected);
        Group groupActually = groupRepository.save(group);
        String titleActually = groupActually.getTitle();
        assertEquals(titleExpected, titleActually);
    }

    @AfterEach
    public void cleanDB() {
        flyway.clean();
    }
}

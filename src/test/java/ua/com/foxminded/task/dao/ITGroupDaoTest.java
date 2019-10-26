package ua.com.foxminded.task.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.task.dao.impl.jdbc.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;

public class ITGroupDaoTest {

    private static GroupDao groupDao;
    private static final Group GROUP11 = GroupModelRepository.getModel11();
    private static final Group GROUP12 = GroupModelRepository.getModel12();
    private static final Group GROUP13 = GroupModelRepository.getModel13();

    private static FlywayConnection flywayConnection = new FlywayConnection();
    private static InitialContextBinder initialContextBinder = InitialContextBinder.getInstance();

    @BeforeAll
    public static void createRecords() {
        initialContextBinder.setInitialContext();
        flywayConnection.createTables();
        groupDao = new GroupDaoImpl();
        groupDao.create(GROUP11);
        groupDao.create(GROUP12);
        groupDao.create(GROUP13);
    }

    @Test
    public void WhenPutAtTableDbGroupObjects_thenGetThisObjectsFindById() {
        int id = GROUP12.getId();
        assertTrue(groupDao.findById(id).equals(GROUP12));
    }

    @Test
    public void WhenPutAtTableDbGroupObjects_thenGetThisObjects() {
        assertTrue(groupDao.findAll().containsAll(Arrays.asList(GROUP12, GROUP13)));
    }

    @Test
    public void WhenUpdateAtTableDbGroupObject_thenGetNewObject() {
        String titleExpected = "test_title_text";
        Group group = groupDao.findById(1);
        group.setTitle(titleExpected);
        Group groupActually = groupDao.update(group);
        String titleActually = groupActually.getTitle();
        assertEquals(titleExpected, titleActually);
    }

    @AfterAll
    public static void removeCreatedTables() {
        flywayConnection.removeTables();
        initialContextBinder.closeInitialContext();
    }
}

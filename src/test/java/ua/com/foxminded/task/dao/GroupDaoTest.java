package ua.com.foxminded.task.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;

@RunWith(JUnitPlatform.class)
public class GroupDaoTest {

    private static GroupDao groupDao;
    private static final Group GROUP1 = GroupModelRepository.getModel1();
    private static final Group GROUP2 = GroupModelRepository.getModel2();
    private static final Group GROUP3 = GroupModelRepository.getModel3();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        groupDao = new GroupDaoImpl();
        groupDao.create(GROUP1);
        groupDao.create(GROUP2);
        groupDao.create(GROUP3);
    }

    @Test
    public void WhenPutAtTableDbGroupObjects_thenGetThisObjectsFindById() {
        int id = GROUP2.getId();
        assertTrue(groupDao.findById(id).equals(GROUP2));
    }

    @Test
    public void WhenPutAtTableDbGroupObjects_thenGetThisObjects() {
        assertTrue(groupDao.findAll().containsAll(Arrays.asList(GROUP1, GROUP2, GROUP3)));
    }

    @Test
    public void WhenPutAtTableDbGroupObjects_thenGetThisObjectsFindByTitle() {
        String title = GROUP3.getTitle();
        assertTrue(groupDao.findByTitle(title).equals(GROUP3));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

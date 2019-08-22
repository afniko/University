package ua.com.foxminded.task.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.StudentModelRepository;

@RunWith(JUnitPlatform.class)
public class ITGroupDaoTest {

    private static GroupDao groupDao;
    private static StudentDao studentDao;
    private static final Student STUDENT1 = StudentModelRepository.getModel1();
    private static final Student STUDENT2 = StudentModelRepository.getModel2();
    private static final Student STUDENT3 = StudentModelRepository.getModel3();
    private static final Student STUDENT4 = StudentModelRepository.getModel4();
    private static final Student STUDENT5 = StudentModelRepository.getModel5();
    private static final Student STUDENT6 = StudentModelRepository.getModel6();
    private static final Group GROUP11 = GroupModelRepository.getModel11();
    private static final Group GROUP12 = GroupModelRepository.getModel12();
    private static final Group GROUP13 = GroupModelRepository.getModel13();

    private static FlywayConnection flywayConnection = new FlywayConnection();

    @BeforeAll
    public static void createRecords() {
        flywayConnection.createTables();
        groupDao = new GroupDaoImpl();
        studentDao = new StudentDaoImpl();
        groupDao.create(GROUP11);
        groupDao.create(GROUP12);
        groupDao.create(GROUP13);
        studentDao.create(STUDENT1);
        studentDao.create(STUDENT2);
        studentDao.create(STUDENT3);
        studentDao.create(STUDENT4);
        studentDao.create(STUDENT5);
        studentDao.create(STUDENT6);
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
    }
}

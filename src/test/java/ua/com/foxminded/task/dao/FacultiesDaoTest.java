package ua.com.foxminded.task.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.FacultyDaoImpl;
import ua.com.foxminded.task.domain.Faculty;
import ua.com.foxminded.task.domain.repository.FacultyModelRepository;

@RunWith(JUnitPlatform.class)
public class FacultiesDaoTest {

    private static FacultyDao facultyDao;
    private static final Faculty FACULTY1 = FacultyModelRepository.getModel1();
    private static final Faculty FACULTY2 = FacultyModelRepository.getModel2();
    private static final Faculty FACULTY3 = FacultyModelRepository.getModel3();
    private static final Faculty FACULTY4 = FacultyModelRepository.getModel4();
    private static final Faculty FACULTY5 = FacultyModelRepository.getModel5();
    private static final Faculty FACULTY6 = FacultyModelRepository.getModel6();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        facultyDao = new FacultyDaoImpl();
        facultyDao.create(FACULTY1);
        facultyDao.create(FACULTY2);
        facultyDao.create(FACULTY3);
        facultyDao.create(FACULTY4);
        facultyDao.create(FACULTY5);
        facultyDao.create(FACULTY6);
    }

    @Test
    public void test() {
        System.out.println(facultyDao.findById(1));
    }
    
    @Test
    public void WhenPutAtTableDbFacultyObjects_thenGetThisObjects() {
        assertTrue(facultyDao.findAll().containsAll(Arrays.asList(FACULTY1, FACULTY2, FACULTY3)));
    }

    @Test
    public void WhenPutAtTableDbFacultyObjects_thenGetThisObjectsFindByTitle() {
        String title = FACULTY2.getTitle();
        assertTrue(facultyDao.findByTitle(title).equals(FACULTY2));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

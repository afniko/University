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

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        facultyDao = new FacultyDaoImpl();
        facultyDao.create(FACULTY1);
        facultyDao.create(FACULTY2);
        facultyDao.create(FACULTY3);
    }

    @Test
    public void test() {
        Faculty faculty = new Faculty();
        faculty.setId(1);
        faculty = facultyDao.findById(faculty);
        System.out.println("faculty Dao , find by id!!!");
        System.out.println("faculty : " + faculty);
        System.out.println("departments : " + faculty.getDepartments());
        System.out.println("groups of first department : " + faculty.getDepartments().get(0).getGroups());
        System.out.println("student : " + faculty.getDepartments().get(0).getGroups().get(0).getStudents());
        System.out.println("teacher : " + faculty.getDepartments().get(0).getTeachers());
    }

    @Test
    public void WhenPutAtTableDbFacultyObjects_thenGetThisObjects() {
        assertTrue(facultyDao.findAll().containsAll(Arrays.asList(FACULTY1, FACULTY2, FACULTY3)));
    }

    @Test
    public void WhenPutAtTableDbFacultyObjects_thenGetThisObjectsFindByTitle() {
        assertTrue(facultyDao.findByTitle(FACULTY2).equals(FACULTY2));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

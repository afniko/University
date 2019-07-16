package ua.com.foxminded.task.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.repository.StudentModelRepository;

@RunWith(JUnitPlatform.class)
public class StudentDaoTest {

    private static StudentDao studentDao;
    private static final Student GROUP1 = StudentModelRepository.getModel1();
    private static final Student GROUP2 = StudentModelRepository.getModel2();
    private static final Student GROUP3 = StudentModelRepository.getModel3();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        studentDao = new StudentDaoImpl();
        studentDao.create(GROUP1);
        studentDao.create(GROUP2);
        studentDao.create(GROUP3);
    }

    @Test
    public void WhenPutAtTableDbStudentObjects_thenGetThisObjectsFindById() {
        Student student = new Student();
        student.setId(2);
        assertTrue(studentDao.findById(student).equals(GROUP2));
    }

    @Test
    public void WhenPutAtTableDbStudentObjects_thenGetThisObjects() {
        assertTrue(studentDao.findAll().containsAll(Arrays.asList(GROUP1, GROUP2, GROUP3)));
    }

    @Test
    public void WhenPutAtTableDbStudentObjects_thenGetThisObjectsFindByTitle() {
        Student student = new Student();
        student.setIdFees(GROUP3.getIdFees());
        assertTrue(studentDao.findByIdFees(student).equals(GROUP3));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

package ua.com.foxminded.task.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.TeacherDaoImpl;
import ua.com.foxminded.task.domain.Teacher;
import ua.com.foxminded.task.domain.repository.TeacherModelRepository;

//@RunWith(JUnitPlatform.class)
public class TeacherDaoTest {

    private static TeacherDao teacherDao;
    private static final Teacher TEACHER1 = TeacherModelRepository.getModel1();
    private static final Teacher TEACHER2 = TeacherModelRepository.getModel2();
    private static final Teacher TEACHER3 = TeacherModelRepository.getModel3();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        teacherDao = new TeacherDaoImpl();
        teacherDao.create(TEACHER1);
        teacherDao.create(TEACHER2);
        teacherDao.create(TEACHER3);
    }

    @Test
    public void WhenPutAtTableDbTeacherObjects_thenGetThisObjectsFindById() {
        Teacher teacher = new Teacher();
        teacher.setId(2);
        assertTrue(teacherDao.findById(teacher).equals(TEACHER2));
    }

    @Test
    public void WhenPutAtTableDbTeacherObjects_thenGetThisObjects() {
        assertTrue(teacherDao.findAll().containsAll(Arrays.asList(TEACHER1, TEACHER2, TEACHER3)));
    }

    @Test
    public void WhenPutAtTableDbTeacherObjects_thenGetThisObjectsFindByTitle() {
        Teacher teacher = new Teacher();
        teacher.setIdFees(TEACHER3.getIdFees());
        assertTrue(teacherDao.findByIdFees(teacher).equals(TEACHER3));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

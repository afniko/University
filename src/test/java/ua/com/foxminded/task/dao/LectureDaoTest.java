package ua.com.foxminded.task.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.LectureDaoImpl;
import ua.com.foxminded.task.domain.Lecture;
import ua.com.foxminded.task.domain.repository.LectureModelRepository;

//@RunWith(JUnitPlatform.class)
public class LectureDaoTest {

    private static LectureDao lectureDao;
    private static final Lecture LECTURE1 = LectureModelRepository.getModel1();
    private static final Lecture LECTURE2 = LectureModelRepository.getModel2();
    private static final Lecture LECTURE3 = LectureModelRepository.getModel3();
    private static final Lecture LECTURE4 = LectureModelRepository.getModel4();
    private static final Lecture LECTURE5 = LectureModelRepository.getModel5();
    private static final Lecture LECTURE6 = LectureModelRepository.getModel6();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        lectureDao = new LectureDaoImpl();
        lectureDao.create(LECTURE1);
        lectureDao.create(LECTURE2);
        lectureDao.create(LECTURE3);
        lectureDao.create(LECTURE4);
        lectureDao.create(LECTURE5);
        lectureDao.create(LECTURE6);
    }

    @Test
    public void WhenPutAtTableDbLectureObjects_thenGetThisObjects() {
        assertTrue(lectureDao.findAll().containsAll(Arrays.asList(LECTURE1, LECTURE2, LECTURE3)));
    }

    @Test
    public void WhenPutAtTableDbLectureObjects_thenGetThisObjectsFindByNumber() {
        String number = LECTURE2.getNumber();
        assertTrue(lectureDao.findByNumber(number).equals(LECTURE2));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

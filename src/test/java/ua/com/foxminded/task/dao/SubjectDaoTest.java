package ua.com.foxminded.task.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.SubjectDao;
import ua.com.foxminded.task.dao.impl.SubjectDaoImpl;
import ua.com.foxminded.task.domain.Subject;
import ua.com.foxminded.task.domain.repository.SubjectModelRepository;

@RunWith(JUnitPlatform.class)
public class SubjectDaoTest {

    private static SubjectDao subjectDao;
    private static final Subject SUBJECT1 = SubjectModelRepository.getModel1();
    private static final Subject SUBJECT2 = SubjectModelRepository.getModel2();
    private static final Subject SUBJECT3 = SubjectModelRepository.getModel3();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        subjectDao = new SubjectDaoImpl();
        subjectDao.create(SUBJECT1);
        subjectDao.create(SUBJECT2);
        subjectDao.create(SUBJECT3);
    }

    @Test
    public void WhenPutAtTableDbSubjectObjects_thenGetThisObjectsFindById() {
        Subject subject = new Subject();
        subject.setId(2);
        assertTrue(subjectDao.findById(subject).equals(SUBJECT2));
    }

    @Test
    public void WhenPutAtTableDbSubjectObjects_thenGetThisObjects() {
        assertTrue(subjectDao.findAll().containsAll(Arrays.asList(SUBJECT1, SUBJECT2, SUBJECT3)));
    }

    @Test
    public void WhenPutAtTableDbSubjectObjects_thenGetThisObjectsFindByTitle() {
        Subject subject = new Subject();
        subject.setTitle(SUBJECT3.getTitle());
        assertTrue(subjectDao.findByTitle(subject).equals(SUBJECT3));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

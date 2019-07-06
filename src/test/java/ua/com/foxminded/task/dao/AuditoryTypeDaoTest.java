package ua.com.foxminded.task.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.AuditoryTypeDaoImpl;
import ua.com.foxminded.task.domain.AuditoryType;

@RunWith(JUnitPlatform.class)
public class AuditoryTypeDaoTest {

    private static final String testAuditoryType1 = "test auditory type1";
    private static final String testAuditoryType2 = "test auditory type2";
    private static AuditoryTypeDao auditoryTypeDao;
    private static AuditoryType auditoryType1;
    private static AuditoryType auditoryType2;

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();

        auditoryTypeDao = new AuditoryTypeDaoImpl();
        auditoryType1 = new AuditoryType();
        auditoryType1.setType(testAuditoryType1);
        auditoryType2 = new AuditoryType();
        auditoryType2.setType(testAuditoryType2);
    }

    @Test
    public void WhenPutAtTableDbAuditoryTypeObjects_thenGetThisObjects() {

        auditoryTypeDao.create(auditoryType1);
        auditoryTypeDao.create(auditoryType2);

        assertTrue(auditoryTypeDao.findAll().containsAll(Arrays.asList(auditoryType1, auditoryType2)));
        assertTrue(auditoryTypeDao.findByType(testAuditoryType2).equals(auditoryType2));

    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

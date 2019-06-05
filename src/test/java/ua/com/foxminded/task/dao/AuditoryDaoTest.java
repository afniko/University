package ua.com.foxminded.task.dao;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.AuditoryDaoImpl;
import ua.com.foxminded.task.dao.impl.AuditoryTypeDaoImpl;
import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.AuditoryType;

@RunWith(JUnitPlatform.class)
public class AuditoryDaoTest {

    private static final String testAuditoryType1 = "test auditory 1";
    private static final String testAuditoryType2 = "test auditory 2";
    private static final String testAuditoryNumber1 = "101a";
    private static final String testAuditoryNumber2 = "301b";

    private static Auditory auditory1 = null;
    private static Auditory auditory2 = null;

    @BeforeAll
    public static void createRecords() {
        AuditoryTypeDao auditoryTypeDao = new AuditoryTypeDaoImpl();
        AuditoryDao auditoryDao = new AuditoryDaoImpl();

        AuditoryType auditoryType1 = new AuditoryType();
        auditoryType1.setType(testAuditoryType1);
        auditoryTypeDao.create(auditoryType1);
        AuditoryType actuallyAuditoryType1 = auditoryTypeDao.findByType(testAuditoryType1);

        AuditoryType auditoryType2 = new AuditoryType();
        auditoryType2.setType(testAuditoryType2);
        auditoryTypeDao.create(auditoryType2);
        AuditoryType actuallyAuditoryType2 = auditoryTypeDao.findByType(testAuditoryType2);

        auditory1 = new Auditory();
        auditory1.setAuditoryNumber(testAuditoryNumber1);
        auditory1.setType(actuallyAuditoryType1);
        auditory1.setMaxCapacity(100);
        auditory1.setDescription("description 1");
        auditoryDao.create(auditory1);

        auditory2 = new Auditory();
        auditory2.setAuditoryNumber(testAuditoryNumber2);
        auditory2.setType(actuallyAuditoryType2);
        auditory2.setMaxCapacity(25);
        auditory2.setDescription("description 1");
        auditoryDao.create(auditory2);
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryByFindAll() {
        AuditoryDao auditoryDao = new AuditoryDaoImpl();
        assertTrue(auditoryDao.findAll().containsAll(Arrays.asList(auditory1, auditory2)));
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryByFindById() {
        AuditoryDao auditoryDao = new AuditoryDaoImpl();
        assertTrue(auditoryDao.findById(1).equals(auditory1));
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryByFindByNumber() {
        AuditoryDao auditoryDao = new AuditoryDaoImpl();
        assertTrue(auditoryDao.findByNumber(testAuditoryNumber2).equals(auditory2));
    }
}

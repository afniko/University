package ua.com.foxminded.task.dao;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.AuditoryDaoImpl;
import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.AuditoryType;
import ua.com.foxminded.task.domain.repository.AuditoryModelRepository;

@RunWith(JUnitPlatform.class)
public class AuditoryDaoTest {

    private static AuditoryDao auditoryDao;
    private static final Auditory auditory1 = AuditoryModelRepository.getModel1();
    private static final Auditory auditory2 = AuditoryModelRepository.getModel2();
    private static final Auditory auditory3 = AuditoryModelRepository.getModel3();
    private static final Auditory auditory4 = AuditoryModelRepository.getModel4();
    private static final Auditory auditory5 = AuditoryModelRepository.getModel5();
    private static final Auditory auditory6 = AuditoryModelRepository.getModel6();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        auditoryDao = new AuditoryDaoImpl();
        auditoryDao.create(auditory1);
        auditoryDao.create(auditory2);
        auditoryDao.create(auditory3);
        auditoryDao.create(auditory4);
        auditoryDao.create(auditory5);
        auditoryDao.create(auditory6);
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryByFindAll() {
        assertTrue(auditoryDao.findAll().containsAll(Arrays.asList(auditory1, auditory2, auditory6)));
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryByFindById() {
        assertTrue(auditoryDao.findById(1).equals(auditory1));
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryTypeByFindById() {
        AuditoryType auditoryType = auditory1.getType();
        assertTrue(auditoryDao.findById(1).getType().equals(auditoryType));
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryByFindByNumber() {
        String testAuditoryNumber2 = auditory2.getAuditoryNumber();
        assertTrue(auditoryDao.findByNumber(testAuditoryNumber2).equals(auditory2));
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryTypeByFindByNumber() {
        String testAuditoryNumber2 = auditory2.getAuditoryNumber();
        AuditoryType auditoryType = auditory2.getType();
        assertTrue(auditoryDao.findByNumber(testAuditoryNumber2).getType().equals(auditoryType));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

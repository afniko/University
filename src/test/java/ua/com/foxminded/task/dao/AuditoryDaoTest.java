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

    private static AuditoryDao auditoryDao = new AuditoryDaoImpl();
    private static final Auditory AUDITORY1 = AuditoryModelRepository.getModel1();
    private static final Auditory AUDITORY2 = AuditoryModelRepository.getModel2();
    private static final Auditory AUDITORY3 = AuditoryModelRepository.getModel3();
    private static final Auditory AUDITORY4 = AuditoryModelRepository.getModel4();
    private static final Auditory AUDITORY5 = AuditoryModelRepository.getModel5();
    private static final Auditory AUDITORY6 = AuditoryModelRepository.getModel6();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        auditoryDao.create(AUDITORY1);
        auditoryDao.create(AUDITORY2);
        auditoryDao.create(AUDITORY3);
        auditoryDao.create(AUDITORY4);
        auditoryDao.create(AUDITORY5);
        auditoryDao.create(AUDITORY6);
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryByFindAll() {
        assertTrue(auditoryDao.findAll().containsAll(Arrays.asList(AUDITORY1, AUDITORY2, AUDITORY6)));
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryByFindById() {
        Auditory auditory = new Auditory();
        auditory.setId(1);
        assertTrue(auditoryDao.findById(auditory).equals(AUDITORY1));
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryTypeByFindById() {
        AuditoryType auditoryType = AUDITORY1.getType();
        Auditory auditory = new Auditory();
        auditory.setType(auditoryType);
        assertTrue(auditoryDao.findById(auditory).getType().equals(auditoryType));
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryByFindByNumber() {
        String testAuditoryNumber2 = AUDITORY2.getAuditoryNumber();
        Auditory auditory = new Auditory();
        auditory.setAuditoryNumber(testAuditoryNumber2);
        assertTrue(auditoryDao.findByNumber(auditory).equals(AUDITORY2));
    }

    @Test
    public void whenPutAtInputAuditory_thenGetAuditoryTypeByFindByNumber() {
        String testAuditoryNumber2 = AUDITORY2.getAuditoryNumber();
        AuditoryType auditoryType = AUDITORY2.getType();
        Auditory auditory = new Auditory();
        auditory.setAuditoryNumber(testAuditoryNumber2);
        assertTrue(auditoryDao.findByNumber(auditory).getType().equals(auditoryType));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

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
import ua.com.foxminded.task.domain.repository.AuditoryTypeModelRepository;

//@RunWith(JUnitPlatform.class)
public class AuditoryTypeDaoTest {

    private static AuditoryTypeDao auditoryTypeDao;
    private static final AuditoryType auditoryType1 = AuditoryTypeModelRepository.getModel1();
    private static final AuditoryType auditoryType2 = AuditoryTypeModelRepository.getModel2();
    private static final AuditoryType auditoryType3 = AuditoryTypeModelRepository.getModel3();
    private static final AuditoryType auditoryType4 = AuditoryTypeModelRepository.getModel4();
    private static final AuditoryType auditoryType5 = AuditoryTypeModelRepository.getModel5();
    private static final AuditoryType auditoryType6 = AuditoryTypeModelRepository.getModel6();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        auditoryTypeDao = new AuditoryTypeDaoImpl();
        auditoryTypeDao.create(auditoryType1);
        auditoryTypeDao.create(auditoryType2);
        auditoryTypeDao.create(auditoryType3);
        auditoryTypeDao.create(auditoryType4);
        auditoryTypeDao.create(auditoryType5);
        auditoryTypeDao.create(auditoryType6);
    }

    @Test
    public void WhenPutAtTableDbAuditoryTypeObjects_thenGetThisObjects() {
        System.out.println("find all type " + auditoryTypeDao.findAll());
        assertTrue(auditoryTypeDao.findAll().containsAll(Arrays.asList(auditoryType1, auditoryType2)));
    }

    @Test
    public void WhenPutAtTableDbAuditoryTypeObjects_thenGetThisObjectsFindByAuditoryType() {
        String testAuditoryType2 = auditoryType2.getType();
        assertTrue(auditoryTypeDao.findByType(testAuditoryType2).equals(auditoryType2));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

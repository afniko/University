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

@RunWith(JUnitPlatform.class)
public class AuditoryTypeDaoTest {

    private static AuditoryTypeDao auditoryTypeDao;
    private static final AuditoryType AUDITORY_TYPE1 = AuditoryTypeModelRepository.getModel1();
    private static final AuditoryType AUDITORY_TYPE2 = AuditoryTypeModelRepository.getModel2();
    private static final AuditoryType AUDITORY_TYPE3 = AuditoryTypeModelRepository.getModel3();
    private static final AuditoryType AUDITORY_TYPE4 = AuditoryTypeModelRepository.getModel4();
    private static final AuditoryType AUDITORY_TYPE5 = AuditoryTypeModelRepository.getModel5();
    private static final AuditoryType AUDITORY_TYPE6 = AuditoryTypeModelRepository.getModel6();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        auditoryTypeDao = new AuditoryTypeDaoImpl();
        auditoryTypeDao.create(AUDITORY_TYPE1);
        auditoryTypeDao.create(AUDITORY_TYPE2);
        auditoryTypeDao.create(AUDITORY_TYPE3);
        auditoryTypeDao.create(AUDITORY_TYPE4);
        auditoryTypeDao.create(AUDITORY_TYPE5);
        auditoryTypeDao.create(AUDITORY_TYPE6);
    }

    @Test
    public void WhenPutAtTableDbAuditoryTypeObjects_thenGetThisObjects() {
        assertTrue(auditoryTypeDao.findAll().containsAll(Arrays.asList(AUDITORY_TYPE1, AUDITORY_TYPE2)));
    }

    @Test
    public void WhenPutAtTableDbAuditoryTypeObjects_thenGetThisObjectsFindByAuditoryType() {
        assertTrue(auditoryTypeDao.findByType(AUDITORY_TYPE2).equals(AUDITORY_TYPE2));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }
}

package ua.com.foxminded.task.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.AuditoryTypeDaoImpl;
import ua.com.foxminded.task.domain.AuditoryType;

@RunWith(JUnitPlatform.class)
public class AuditoryTypeDaoTest {

    @Test
    public void WhenPutAtTableDbAuditoryTypeObjects_thenGetThisObjects() {

        String testAuditoryType1 = "test auditory type1";
        String testAuditoryType2 = "test auditory type2";
        AuditoryTypeDao auditoryTypeDao = new AuditoryTypeDaoImpl();
        AuditoryType auditoryType1 = new AuditoryType();
        auditoryType1.setType(testAuditoryType1);
        AuditoryType auditoryType2 = new AuditoryType();
        auditoryType2.setType(testAuditoryType2);
        auditoryTypeDao.create(auditoryType1);
        auditoryTypeDao.create(auditoryType2);

        assertTrue(auditoryTypeDao.findAll().containsAll(Arrays.asList(auditoryType1, auditoryType2)));

        assertTrue(auditoryTypeDao.findByType(testAuditoryType2).equals(auditoryType2));
    }
}

package ua.com.foxminded.task.dao;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.AuditoryDaoImpl;
import ua.com.foxminded.task.dao.impl.AuditoryTypeDaoImpl;
import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.AuditoryType;

@RunWith(JUnitPlatform.class)
public class AuditoryDaoTest {

    @Test
    public void whenPutAtInputAuditory_thengetAudory() {
        AuditoryTypeDao auditoryTypeDao = new AuditoryTypeDaoImpl();
        AuditoryDao auditoryDao = new AuditoryDaoImpl();
        
    String testAuditoryType1 = "test auditory 1";
    String testAuditoryType2 = "test auditory 2";

        AuditoryType auditoryType1 = new AuditoryType();
        auditoryType1.setType(testAuditoryType1);
        auditoryTypeDao.create(auditoryType1);
        AuditoryType actuallyAuditoryType1 = auditoryTypeDao.findByType(testAuditoryType1);

        AuditoryType auditoryType2 = new AuditoryType();
        auditoryType2.setType(testAuditoryType2);
        auditoryTypeDao.create(auditoryType2);
        AuditoryType actuallyAuditoryType2 = auditoryTypeDao.findByType(testAuditoryType2);
        
        
        String testAuditoryNumber1 = "101a";
        String testAuditoryNumber2 = "301b";
        
        Auditory auditory1 = new Auditory();
        auditory1.setAuditoryNumber(testAuditoryNumber1);
        auditory1.setType(actuallyAuditoryType1);
        auditory1.setMaxCapacity(100);
        auditory1.setDescription("description 1");
        auditoryDao.create(auditory1);
        
        Auditory auditory2 = new Auditory();
        auditory2.setAuditoryNumber(testAuditoryNumber2);
        auditory2.setType(actuallyAuditoryType2);
        auditory2.setMaxCapacity(25);
        auditory2.setDescription("description 1");
        auditoryDao.create(auditory2);
        
        System.out.println(auditoryDao.findAll());
    }
}

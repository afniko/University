package ua.com.foxminded.task;

import ua.com.foxminded.task.dao.AuditoryTypeDao;
import ua.com.foxminded.task.dao.impl.AuditoryTypeDaoImpl;
import ua.com.foxminded.task.domain.AuditoryType;

public class Main {

    public static void main(String[] args) {

        AuditoryTypeDao auditoryTypeDao = new AuditoryTypeDaoImpl();
//        AuditoryType auditoryType = new AuditoryType();
        String testAuditoryType = "test auditory type";
//        auditoryType.setType(testAuditoryType + 3);
//        auditoryTypeDao.create(auditoryType);
//        auditoryType.setType(testAuditoryType + 4);
//        auditoryTypeDao.create(auditoryType);
        System.out.println(auditoryTypeDao.findAll());

        System.out.println(auditoryTypeDao.findByType(testAuditoryType + 3));
    }

}

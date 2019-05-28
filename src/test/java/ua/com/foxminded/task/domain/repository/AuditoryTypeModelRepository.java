package ua.com.foxminded.task.domain.repository;

import ua.com.foxminded.task.domain.AuditoryType;

public class AuditoryTypeModelRepository {

    public static AuditoryType getModel1() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setId(1);
        auditoryType.setType("Lecture");
        return auditoryType;
    }

    public static AuditoryType getModel2() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setId(2);
        auditoryType.setType("Practic");
        return auditoryType;
    }

    public static AuditoryType getModel3() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setId(3);
        auditoryType.setType("Laboratory");
        return auditoryType;
    }

    public static AuditoryType getModel4() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setId(4);
        auditoryType.setType("Lecture small");
        return auditoryType;
    }

    public static AuditoryType getModel5() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setId(5);
        auditoryType.setType("Holl");
        return auditoryType;
    }

    public static AuditoryType getModel6() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setId(6);
        auditoryType.setType("Recriation");
        return auditoryType;
    }

}

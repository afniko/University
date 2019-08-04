package ua.com.foxminded.task.domain.repository;

import ua.com.foxminded.task.domain.AuditoryType;

public class AuditoryTypeModelRepository {

    private AuditoryTypeModelRepository() {
    }

    public static AuditoryType getModel1() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setType("Lecture");
        return auditoryType;
    }

    public static AuditoryType getModel2() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setType("Practic");
        return auditoryType;
    }

    public static AuditoryType getModel3() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setType("Laboratory");
        return auditoryType;
    }

    public static AuditoryType getModel4() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setType("Lecture small");
        return auditoryType;
    }

    public static AuditoryType getModel5() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setType("Holl");
        return auditoryType;
    }

    public static AuditoryType getModel6() {
        AuditoryType auditoryType = new AuditoryType();
        auditoryType.setType("Recriation");
        return auditoryType;
    }

}

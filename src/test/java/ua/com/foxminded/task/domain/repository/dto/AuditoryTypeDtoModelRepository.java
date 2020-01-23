package ua.com.foxminded.task.domain.repository.dto;

import ua.com.foxminded.task.domain.dto.AuditoryTypeDto;

public class AuditoryTypeDtoModelRepository {

    private AuditoryTypeDtoModelRepository() {
    }

    public static AuditoryTypeDto getModel1() {
        AuditoryTypeDto auditoryType = new AuditoryTypeDto();
        auditoryType.setType("Lecture");
        return auditoryType;
    }

    public static AuditoryTypeDto getModel2() {
        AuditoryTypeDto auditoryType = new AuditoryTypeDto();
        auditoryType.setType("Practic");
        return auditoryType;
    }

    public static AuditoryTypeDto getModel3() {
        AuditoryTypeDto auditoryType = new AuditoryTypeDto();
        auditoryType.setType("Laboratory");
        return auditoryType;
    }

    public static AuditoryTypeDto getModel4() {
        AuditoryTypeDto auditoryType = new AuditoryTypeDto();
        auditoryType.setType("Lecture small");
        return auditoryType;
    }

    public static AuditoryTypeDto getModel5() {
        AuditoryTypeDto auditoryType = new AuditoryTypeDto();
        auditoryType.setType("Holl");
        return auditoryType;
    }

    public static AuditoryTypeDto getModel6() {
        AuditoryTypeDto auditoryType = new AuditoryTypeDto();
        auditoryType.setType("Recriation");
        return auditoryType;
    }

}

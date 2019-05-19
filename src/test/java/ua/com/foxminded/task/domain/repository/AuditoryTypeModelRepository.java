package ua.com.foxminded.task.domain.repository;

import ua.com.foxminded.task.domain.AuditoryType;

public class AuditoryTypeModelRepository {

    private static AuditoryType auditoryType;

    public static AuditoryType getModel(TestModel testModel) {
        switch (testModel) {
        case MODEL_1:
            createModel1();
            break;
        case MODEL_2:
            createModel2();
            break;
        case MODEL_3:
            createModel3();
            break;
        case MODEL_4:
            createModel4();
            break;
        case MODEL_5:
            createModel5();
            break;
        case MODEL_6:
            createModel6();
            break;
        }
        return auditoryType;
    }

    private static void createModel1() {
        auditoryType = new AuditoryType();
        auditoryType.setId(1);
        auditoryType.setType("Lecture");
    }

    private static void createModel2() {
        auditoryType = new AuditoryType();
        auditoryType.setId(2);
        auditoryType.setType("Practic");
    }

    private static void createModel3() {
        auditoryType = new AuditoryType();
        auditoryType.setId(3);
        auditoryType.setType("Laboratory");
    }

    private static void createModel4() {
        auditoryType = new AuditoryType();
        auditoryType.setId(4);
        auditoryType.setType("Lecture small");
    }

    private static void createModel5() {
        auditoryType = new AuditoryType();
        auditoryType.setId(5);
        auditoryType.setType("Holl");
    }

    private static void createModel6() {
        auditoryType = new AuditoryType();
        auditoryType.setId(6);
        auditoryType.setType("Recriation");
    }

}

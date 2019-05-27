package ua.com.foxminded.task.domain.repository;

import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.domain.Auditory;

public class AuditoryModelRepository {

    private static Auditory auditory;
    private static List<Auditory> auditories;

    public static Auditory getModel(TestModel testModel) {
        switch (testModel) {
        case MODEL_1:
            createModel1(testModel);
            break;
        case MODEL_2:
            createModel2(testModel);
            break;
        case MODEL_3:
            createModel3(testModel);
            break;
        case MODEL_4:
            createModel4(testModel);
            break;
        case MODEL_5:
            createModel5(testModel);
            break;
        case MODEL_EMPTY:
            createModel6(testModel);
            break;
        }
        return auditory;
    }

    public static List<Auditory> getList(TestModel testModel) {
        auditories = new ArrayList<>();
        switch (testModel) {
        case MODEL_1:
            createModel1(testModel);
            auditories.add(auditory);
            createModel2(testModel);
            auditories.add(auditory);
            break;
        case MODEL_2:
            createModel2(testModel);
            auditories.add(auditory);
            createModel3(testModel);
            auditories.add(auditory);
            break;
        case MODEL_3:
            createModel1(testModel);
            auditories.add(auditory);
            createModel6(testModel);
            auditories.add(auditory);
            break;
        case MODEL_4:
            createModel4(testModel);
            auditories.add(auditory);
            createModel5(testModel);
            auditories.add(auditory);
            break;
        case MODEL_5:
            createModel3(testModel);
            auditories.add(auditory);
            createModel6(testModel);
            auditories.add(auditory);
            break;
        case MODEL_EMPTY:
            createModel3(testModel);
            auditories.add(auditory);
            createModel5(testModel);
            auditories.add(auditory);
            break;
        }
        return auditories;
    }

    private static void createModel1(TestModel testModel) {
        auditory = new Auditory();
        auditory.setId(1);
        auditory.setAuditoryNumber("101a");
        auditory.setType(AuditoryTypeModelRepository.getModel(testModel));
        auditory.setMaxCapacity(100);
        auditory.setDescription("bla bla bla 1");
    }

    private static void createModel2(TestModel testModel) {
        auditory = new Auditory();
        auditory.setId(2);
        auditory.setAuditoryNumber("102a");
        auditory.setType(AuditoryTypeModelRepository.getModel(testModel));
        auditory.setMaxCapacity(50);
        auditory.setDescription("bla bla bla 2");
    }

    private static void createModel3(TestModel testModel) {
        auditory = new Auditory();
        auditory.setId(3);
        auditory.setAuditoryNumber("201a");
        auditory.setType(AuditoryTypeModelRepository.getModel(testModel));
        auditory.setMaxCapacity(10);
        auditory.setDescription("bla bla bla 3");
    }

    private static void createModel4(TestModel testModel) {
        auditory = new Auditory();
        auditory.setId(4);
        auditory.setAuditoryNumber("103a");
        auditory.setType(AuditoryTypeModelRepository.getModel(testModel));
        auditory.setMaxCapacity(30);
        auditory.setDescription("bla bla bla 4");
    }

    private static void createModel5(TestModel testModel) {
        auditory = new Auditory();
        auditory.setId(5);
        auditory.setAuditoryNumber("405a");
        auditory.setType(AuditoryTypeModelRepository.getModel(testModel));
        auditory.setMaxCapacity(14);
        auditory.setDescription("bla bla bla 5");
    }

    private static void createModel6(TestModel testModel) {
        auditory = new Auditory();
        auditory.setId(6);
        auditory.setAuditoryNumber("161a");
        auditory.setType(AuditoryTypeModelRepository.getModel(testModel));
        auditory.setMaxCapacity(20);
        auditory.setDescription("bla bla bla 6");
    }

}

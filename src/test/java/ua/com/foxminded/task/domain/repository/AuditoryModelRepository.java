package ua.com.foxminded.task.domain.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.Auditory;

public class AuditoryModelRepository {

    private AuditoryModelRepository() {
    }

    public static List<Auditory> getModels() {
        List<Auditory> auditories = Arrays.asList(getModel1(), getModel2(), getModel3(), getModel4(), getModel5(), getModel6());
        return new ArrayList<>(auditories);
    }

    public static Auditory getModel1() {
        Auditory auditory = new Auditory();
        auditory.setAuditoryNumber("101a");
        auditory.setType(AuditoryTypeModelRepository.getModel1());
        auditory.setMaxCapacity(100);
        auditory.setDescription("bla bla bla 1");
        return auditory;
    }

    public static Auditory getModel2() {
        Auditory auditory = new Auditory();
        auditory.setAuditoryNumber("102a");
        auditory.setType(AuditoryTypeModelRepository.getModel2());
        auditory.setMaxCapacity(50);
        auditory.setDescription("bla bla bla 2");
        return auditory;
    }

    public static Auditory getModel3() {
        Auditory auditory = new Auditory();
        auditory.setAuditoryNumber("201a");
        auditory.setType(AuditoryTypeModelRepository.getModel3());
        auditory.setMaxCapacity(10);
        auditory.setDescription("bla bla bla 3");
        return auditory;
    }

    public static Auditory getModel4() {
        Auditory auditory = new Auditory();
        auditory.setAuditoryNumber("103a");
        auditory.setType(AuditoryTypeModelRepository.getModel4());
        auditory.setMaxCapacity(30);
        auditory.setDescription("bla bla bla 4");
        return auditory;
    }

    public static Auditory getModel5() {
        Auditory auditory = new Auditory();
        auditory.setAuditoryNumber("405a");
        auditory.setType(AuditoryTypeModelRepository.getModel1());
        auditory.setMaxCapacity(14);
        auditory.setDescription("bla bla bla 5");
        return auditory;
    }

    public static Auditory getModel6() {
        Auditory auditory = new Auditory();
        auditory.setAuditoryNumber("161a");
        auditory.setType(AuditoryTypeModelRepository.getModel2());
        auditory.setMaxCapacity(20);
        auditory.setDescription("bla bla bla 6");
        return auditory;
    }

}

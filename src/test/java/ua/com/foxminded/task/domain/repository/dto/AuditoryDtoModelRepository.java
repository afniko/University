package ua.com.foxminded.task.domain.repository.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.dto.AuditoryDto;
import ua.com.foxminded.task.domain.repository.AuditoryTypeModelRepository;

public class AuditoryDtoModelRepository {

    private AuditoryDtoModelRepository() {
    }

    public static List<AuditoryDto> getModels() {
        List<AuditoryDto> auditories = Arrays.asList(getModel1(), getModel2(), getModel3(), getModel4(), getModel5(), getModel6());
        return new ArrayList<>(auditories);
    }

    public static AuditoryDto getModel1() {
        AuditoryDto auditory = new AuditoryDto();
        auditory.setAuditoryNumber("101a");
        auditory.setAuditoryTypeTitle(AuditoryTypeModelRepository.getModel1().getType());
        auditory.setMaxCapacity(100);
        auditory.setDescription("bla bla bla 1");
        return auditory;
    }

    public static AuditoryDto getModel2() {
        AuditoryDto auditory = new AuditoryDto();
        auditory.setAuditoryNumber("102a");
        auditory.setAuditoryTypeTitle(AuditoryTypeModelRepository.getModel2().getType());
        auditory.setMaxCapacity(50);
        auditory.setDescription("bla bla bla 2");
        return auditory;
    }

    public static AuditoryDto getModel3() {
        AuditoryDto auditory = new AuditoryDto();
        auditory.setAuditoryNumber("201a");
        auditory.setAuditoryTypeTitle(AuditoryTypeModelRepository.getModel3().getType());
        auditory.setMaxCapacity(10);
        auditory.setDescription("bla bla bla 3");
        return auditory;
    }

    public static AuditoryDto getModel4() {
        AuditoryDto auditory = new AuditoryDto();
        auditory.setAuditoryNumber("103a");
        auditory.setAuditoryTypeTitle(AuditoryTypeModelRepository.getModel4().getType());
        auditory.setMaxCapacity(30);
        auditory.setDescription("bla bla bla 4");
        return auditory;
    }

    public static AuditoryDto getModel5() {
        AuditoryDto auditory = new AuditoryDto();
        auditory.setAuditoryNumber("405a");
        auditory.setAuditoryTypeTitle(AuditoryTypeModelRepository.getModel1().getType());
        auditory.setMaxCapacity(14);
        auditory.setDescription("bla bla bla 5");
        return auditory;
    }

    public static AuditoryDto getModel6() {
        AuditoryDto auditory = new AuditoryDto();
        auditory.setAuditoryNumber("161a");
        auditory.setAuditoryTypeTitle(AuditoryTypeModelRepository.getModel2().getType());
        auditory.setMaxCapacity(20);
        auditory.setDescription("bla bla bla 6");
        return auditory;
    }

}

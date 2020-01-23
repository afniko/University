package ua.com.foxminded.task.domain.repository.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.dto.SubjectDto;

public class SubjectDtoModelRepository {

    private SubjectDtoModelRepository() {
    }

    public static List<SubjectDto> getModels() {
        List<SubjectDto> subjects = Arrays.asList(getModel2(), getModel3(), getModel4());
        return new ArrayList<>(subjects);
    }

    public static SubjectDto getModel1() {
        SubjectDto subject = new SubjectDto();
        subject.setTitle("Programming");
        return subject;
    }

    public static SubjectDto getModel2() {
        SubjectDto subject = new SubjectDto();
        subject.setTitle("Phisics");
        return subject;
    }

    public static SubjectDto getModel3() {
        SubjectDto subject = new SubjectDto();
        subject.setTitle("Mathmatics");
        return subject;
    }

    public static SubjectDto getModel4() {
        SubjectDto subject = new SubjectDto();
        subject.setTitle("Biologic");
        return subject;
    }

}

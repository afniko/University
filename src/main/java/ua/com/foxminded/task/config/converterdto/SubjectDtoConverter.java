package ua.com.foxminded.task.config.converterdto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.dto.SubjectDto;

@Component
public class SubjectDtoConverter implements Converter<String, SubjectDto> {

    @Override
    public SubjectDto convert(String subject) {
        if (!subject.matches("SubjectDto \\[id=\\d+, title=\\w+\\]$")) {
            return null;
        }
        return parseSubjectDto(subject);
    }

    private SubjectDto parseSubjectDto(String subject) {
        String removingSequince1 = "SubjectDto \\[id=";
        String removingSequince2 = " title=";
        String removingSequince3 = "\\]";

        String[] subjectArray = subject.replaceAll(removingSequince1, "")
                                       .replaceAll(removingSequince2, "")
                                       .replaceAll(removingSequince3, "")
                                       .split(",");
        int id = Integer.parseInt(subjectArray[0]);
        String title = subjectArray[1];
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(id);
        subjectDto.setTitle(title);
        return subjectDto;
    }
}

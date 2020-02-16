package ua.com.foxminded.task.controller.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.dto.SubjectDto;

@Component
public class SubjectFormatter implements Formatter<SubjectDto> {

    @Override
    public SubjectDto parse(String subject, Locale locale) throws ParseException {
        return parseSubjectDto(subject);
    }

    @Override
    public String print(SubjectDto subject, Locale locale) {
        return subject.toString();
    }
    
    private SubjectDto parseSubjectDto(String subject) {
        String removingSequince1 = "SubjectDto \\[id=";
        String removingSequince2 = " title=";
        String removingSequince3 = "\\]";

        String[] subjectArray = subject.replaceAll(removingSequince1, "")
                                       .replaceAll(removingSequince2, "")
                                       .replaceAll(removingSequince3, "")
                                       .split(",");
        SubjectDto subjectDto = new SubjectDto();
        if (subjectArray[0].matches("^\\d+$")) {
            int id = Integer.parseInt(subjectArray[0]);
            subjectDto.setId(id);
        }
        if (subjectArray.length==3) {
            String title = subjectArray[1];
            subjectDto.setTitle(title);
        }
        return subjectDto;
    }
}

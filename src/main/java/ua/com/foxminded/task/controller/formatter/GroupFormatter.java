package ua.com.foxminded.task.controller.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.dto.GroupDto;

@Component
public class GroupFormatter implements Formatter<GroupDto> {

    @Override
    public GroupDto parse(String group, Locale locale) throws ParseException {
        return parseGroupDto(group);
    }

    @Override
    public String print(GroupDto group, Locale locale) {
        return group.toString();
    }
    
    private GroupDto parseGroupDto(String group) {
        String removingSequince1 = "GroupDto \\[id=";
        String removingSequince2 = " title=";
        String removingSequince3 = " yearEntry=";
        String removingSequince4 = "\\]";

        String[] groupArray = group.replaceAll(removingSequince1, "")
                                   .replaceAll(removingSequince2, "")
                                   .replaceAll(removingSequince3, "")
                                   .replaceAll(removingSequince4, "")
                                   .split(",");
        GroupDto groupDto = new GroupDto();
        if (groupArray[0].matches("^\\d+$")) {
            int id = Integer.parseInt(groupArray[0]);
            groupDto.setId(id);
        }
        if (groupArray.length==3) {
            String title = groupArray[1];
            int yearEntry = Integer.parseInt(groupArray[2]);
            groupDto.setTitle(title);
            groupDto.setYearEntry(yearEntry);
        }
        return groupDto;
    }
}

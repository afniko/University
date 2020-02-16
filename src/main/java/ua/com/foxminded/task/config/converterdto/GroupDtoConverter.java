package ua.com.foxminded.task.config.converterdto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ua.com.foxminded.task.domain.dto.GroupDto;

@Component
public class GroupDtoConverter implements Converter<String, GroupDto> {

    @Override
    public GroupDto convert(String group) {
        if (!group.matches("GroupDto \\[id=\\d+, title=\\w+, yearEntry=\\d+\\]$")) {
            return null;
        }
        return parseGroupDto(group);
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
        int id = Integer.parseInt(groupArray[0]);
        String title = groupArray[1];
        int yearEntry = Integer.parseInt(groupArray[2]);
        GroupDto groupDto = new GroupDto();
        groupDto.setId(id);
        groupDto.setTitle(title);
        groupDto.setYearEntry(yearEntry);
        return groupDto;
    }
}

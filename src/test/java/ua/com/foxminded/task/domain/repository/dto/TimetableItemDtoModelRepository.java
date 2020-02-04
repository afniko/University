package ua.com.foxminded.task.domain.repository.dto;

import static java.util.Arrays.asList;

import java.time.LocalDate;
import java.util.List;

import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.TimetableItemDto;
import ua.com.foxminded.task.domain.repository.AuditoryModelRepository;
import ua.com.foxminded.task.domain.repository.LectureModelRepository;
import ua.com.foxminded.task.domain.repository.SubjectModelRepository;
import ua.com.foxminded.task.domain.repository.TeacherModelRepository;

public class TimetableItemDtoModelRepository {

    private TimetableItemDtoModelRepository() {
    }

    public static TimetableItemDto getModel1() {
        TimetableItemDto timetableItemDto = new TimetableItemDto();
        timetableItemDto.setSubjectTitle(SubjectModelRepository.getModel1().getTitle());
        timetableItemDto.setSubjectId(SubjectModelRepository.getModel1().getId());
        timetableItemDto.setAuditoryTitle(AuditoryModelRepository.getModel1().getAuditoryNumber());
        timetableItemDto.setAuditoryId(AuditoryModelRepository.getModel1().getId());
        List<GroupDto> groupDtos = asList(GroupDtoModelRepository.getModel1(), GroupDtoModelRepository.getModel2());
        timetableItemDto.setGroups(groupDtos);
        timetableItemDto.setLectureTitle(LectureModelRepository.getModel1().getNumber());
        timetableItemDto.setLectureId(LectureModelRepository.getModel1().getId());
        timetableItemDto.setDate(LocalDate.of(2020, 06, 25));
        timetableItemDto.setTeacherTitle(TeacherModelRepository.getModel1().getFirstName());
        timetableItemDto.setTeacherId(TeacherModelRepository.getModel1().getId());
        return timetableItemDto;
    }
}

package ua.com.foxminded.task.service;

import java.time.LocalDate;

import ua.com.foxminded.task.domain.dto.TimetableItemDto;

public interface TimetableItemService extends ModelService<TimetableItemDto> {

    public boolean existsByAuditoryIdAndLectureIdAndDate(Integer auditoryId, Integer lectureId, LocalDate date);

    public boolean existsByTeacherIdAndLectureIdAndDate(Integer teacherId, Integer lectureId, LocalDate date);
}

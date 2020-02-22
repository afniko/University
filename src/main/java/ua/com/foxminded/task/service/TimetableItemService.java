package ua.com.foxminded.task.service;

import java.time.LocalDate;
import java.util.List;

import ua.com.foxminded.task.dao.filter.TimetableItemSpecification;
import ua.com.foxminded.task.domain.TimetableItem;
import ua.com.foxminded.task.domain.dto.FiltersDto;
import ua.com.foxminded.task.domain.dto.TimetableItemDto;

public interface TimetableItemService extends ModelService<TimetableItemDto> {

    public TimetableItem findByAuditoryIdAndLectureIdAndDate(Integer auditoryId, Integer lectureId, LocalDate date);

    public TimetableItem findByTeacherIdAndLectureIdAndDate(Integer teacherId, Integer lectureId, LocalDate date);
    
    public List<TimetableItemDto> findByDateBetweenAndTeacherId(LocalDate startDate, LocalDate endDate, Integer teacherId);
    
    public List<TimetableItemDto> findByDateBetweenAndStudentId(LocalDate startDate, LocalDate endDate, Integer studentId);
    
    public List<TimetableItemDto> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    public List<TimetableItemDto> findByDateBetweenAndTeacherIdAndStudentId(LocalDate startDate, LocalDate endDate, Integer teacherId, Integer studentId);
    
    public List<TimetableItemDto> findByTimetableItemSpecification(FiltersDto filters);
}

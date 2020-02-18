package ua.com.foxminded.task.dao;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.task.domain.TimetableItem;

@Repository
@Transactional
public interface TimetableItemRepository extends JpaRepository<TimetableItem, Integer> {

    public boolean existsById(Integer id);
    
    public boolean existsByAuditoryIdAndLectureIdAndDate(Integer auditoryId, Integer lectureId, LocalDate date );
    
    public boolean existsByTeacherIdAndLectureIdAndDate(Integer teacherId, Integer lectureId, LocalDate date );
}

package ua.com.foxminded.task.dao;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.task.domain.TimetableItem;

@Repository
@Transactional
public interface TimetableItemRepository extends JpaRepository<TimetableItem, Integer> {

    public boolean existsById(Integer id);

    public TimetableItem findByAuditoryIdAndLectureIdAndDate(Integer auditoryId, Integer lectureId, LocalDate date);

    public TimetableItem findByTeacherIdAndLectureIdAndDate(Integer teacherId, Integer lectureId, LocalDate date);

    public List<TimetableItem> findByDateBetweenAndTeacherId(LocalDate startDate, LocalDate endDate, Integer teacherId);
    
    @Query("select t from TimetableItem t join t.groups g where g in (select s.group from Student s where s.id=?3) and t.date between ?1 and ?2")
    public List<TimetableItem> findByDateBetweenAndStudentId(LocalDate startDate, LocalDate endDate, Integer studentId);
    
    public List<TimetableItem> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("select t from TimetableItem t join t.groups g where g in (select s.group from Student s where s.id=?4) and t.teacher.id=?3 and t.date between ?1 and ?2")
    public List<TimetableItem> findByDateBetweenAndTeacherIdAndStudentId(LocalDate startDate, LocalDate endDate, Integer teacherId, Integer studentId);
}

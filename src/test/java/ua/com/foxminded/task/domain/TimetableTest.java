package ua.com.foxminded.task.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import ua.com.foxminded.task.domain.repository.StudentModelRepository;
import ua.com.foxminded.task.domain.repository.TeacherModelRepository;
import ua.com.foxminded.task.domain.repository.TimetableItemModelRepository;
import ua.com.foxminded.task.domain.repository.TimetableModelRepository;

public class TimetableTest {

    @Test
    public void whenAddTimetableItemToTimetable_thenTimetableContainsTimetableItem() {
        Timetable timetable = TimetableModelRepository.getEmptyModel();
        TimetableItem timetableItem = TimetableItemModelRepository.getModel1();
        timetable.addTimetableItem(timetableItem);
        assertTrue(timetable.getTimetableItems().contains(timetableItem));
    }

    @Test
    public void whenRemoveTimetableItemFromTimetable_thenTimetableNonContainsTimetableItem() {
        Timetable timetable = TimetableModelRepository.getModel();
        TimetableItem timetableItem = TimetableItemModelRepository.getModel1();
        timetable.removeTimetableItem(timetableItem);
        assertFalse(timetable.getTimetableItems().contains(timetableItem));
    }

    @Test
    public void whenSetPeriodForStudent_thenGetFindSchedule() {
        Timetable timetable = TimetableModelRepository.getModel();
        Student student = StudentModelRepository.getModel1();
        Date startPeriod = Date.valueOf("2019-01-25");
        Date endPeriod = Date.valueOf("2019-03-25");
        List<TimetableItem> timetableItemsExpected = TimetableItemModelRepository.getTimetableItemsStudentsExpected();
        List<TimetableItem> timetableItemsUnexpected = TimetableItemModelRepository.getTimetableItemsStudentUnexpected();
        Timetable timetableActual = timetable.findSchedule(student, startPeriod, endPeriod);
        System.out.println("actually: "+timetableActual.getTimetableItems());
        System.out.println("expected: "+timetableItemsExpected);
        assertTrue(timetableActual.getTimetableItems().containsAll(timetableItemsExpected));
        assertFalse(timetableActual.getTimetableItems().containsAll(timetableItemsUnexpected));
    }

    @Test
    public void whenSetPeriodForTeacher_thenGetFindSchedule() {
        Timetable timetable = TimetableModelRepository.getModel();
        Teacher teacher = TeacherModelRepository.getModel1();
        Date startPeriod = Date.valueOf("2019-01-22");
        Date endPeriod = Date.valueOf("2019-03-25");
        List<TimetableItem> timetableItemsExpected = TimetableItemModelRepository.getTimetableItemsTeacherExpected();
        List<TimetableItem> timetableItemsUnexpected = TimetableItemModelRepository.getTimetableItemsTeacherUnexpected();
        Timetable timetableActual = timetable.findSchedule(teacher, startPeriod, endPeriod);
        assertTrue(timetableActual.getTimetableItems().containsAll(timetableItemsExpected));
        assertFalse(timetableActual.getTimetableItems().containsAll(timetableItemsUnexpected));
    }
}

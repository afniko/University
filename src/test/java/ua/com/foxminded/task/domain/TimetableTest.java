package ua.com.foxminded.task.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.StudentModelRepository;
import ua.com.foxminded.task.domain.repository.TeacherModelRepository;
import ua.com.foxminded.task.domain.repository.TimetableItemModelRepository;
import ua.com.foxminded.task.domain.repository.TimetableModelRepository;

@RunWith(JUnitPlatform.class)
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
        List<TimetableItem> timetableItemsExpected = Arrays.asList(
                TimetableItemModelRepository.getModel3(), 
                TimetableItemModelRepository.getModel4(), 
                TimetableItemModelRepository.getModel5());
        List<TimetableItem> timetableItemsUnexpected = Arrays.asList(
                TimetableItemModelRepository.getModel1(), 
                TimetableItemModelRepository.getModel2(), 
                TimetableItemModelRepository.getModel6());
        Timetable timetableActual = timetable.findSchedule(student, startPeriod, endPeriod);
        assertTrue(timetableActual.getTimetableItems().containsAll(timetableItemsExpected));
        assertFalse(timetableActual.getTimetableItems().containsAll(timetableItemsUnexpected));
    }
    
    @Test
    public void whenSetPeriodForTeacher_thenGetFindSchedule() {
        Timetable timetable = TimetableModelRepository.getModel();
       Teacher teacher= TeacherModelRepository.getModel1();
        Date startPeriod = Date.valueOf("2019-01-22");
        Date endPeriod = Date.valueOf("2019-03-25");
        List<TimetableItem> timetableItemsExpected = Arrays.asList(
                TimetableItemModelRepository.getModel2(), 
                TimetableItemModelRepository.getModel4());
        List<TimetableItem> timetableItemsUnexpected = Arrays.asList(
                TimetableItemModelRepository.getModel1(), 
                TimetableItemModelRepository.getModel3(), 
                TimetableItemModelRepository.getModel5(), 
                TimetableItemModelRepository.getModel6());
        Timetable timetableActual = timetable.findSchedule(teacher, startPeriod, endPeriod);
        assertTrue(timetableActual.getTimetableItems().containsAll(timetableItemsExpected));
        assertFalse(timetableActual.getTimetableItems().containsAll(timetableItemsUnexpected));
    }
}

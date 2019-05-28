package ua.com.foxminded.task.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.StudentModelRepository;
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
        
    }
}

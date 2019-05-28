package ua.com.foxminded.task.domain.repository;

import ua.com.foxminded.task.domain.Timetable;

public class TimetableModelRepository {

    private static Timetable timetable;

    public static Timetable getModel() {
        timetable = new Timetable();
        timetable.setTimetableItems(TimetableItemModelRepository.getModels());
        return timetable;
    }

}

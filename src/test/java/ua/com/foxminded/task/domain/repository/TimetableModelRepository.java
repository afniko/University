package ua.com.foxminded.task.domain.repository;

import ua.com.foxminded.task.domain.Timetable;

public class TimetableModelRepository {

    private static Timetable timetable;

    public static Timetable getModel(TestModel testModel) {
        timetable = new Timetable();
        timetable.setTimetableItems(TimetableItemModelRepository.getList(testModel));
        return timetable;
    }

}

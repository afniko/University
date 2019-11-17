package ua.com.foxminded.task.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Timetable {

    private List<TimetableItem> timetableItems = new ArrayList<>();

    public Timetable() {
    }

    public Timetable(List<TimetableItem> timetableItems) {
        this.timetableItems = timetableItems;
    }

    public Timetable findSchedule(Teacher teacher, Date startPeriod, Date endPeriod) {
        List<TimetableItem> timetableItemsFiltered = timetableItems.stream()
                .filter(t -> t.getTeacher().equals(teacher))
                .filter(d -> (d.getDate().compareTo(startPeriod) >= 0 && d.getDate().compareTo(endPeriod) <= 0))
                .collect(Collectors.toList());
        return new Timetable(timetableItemsFiltered);
    }

    public Timetable findSchedule(Student student, Date startPeriod, Date endPeriod) {
        List<TimetableItem> timetableItemsFiltered = timetableItems.stream()
                .filter(tti -> tti.getGroups().contains(student.getGroup()))
                .filter(date -> (date.getDate().compareTo(startPeriod) >= 0 && date.getDate().compareTo(endPeriod) <= 0))
                .collect(Collectors.toList());
        return new Timetable(timetableItemsFiltered);
    }

    public void addTimetableItem(TimetableItem timetableItem) {
        timetableItems.add(timetableItem);
    }

    public void removeTimetableItem(TimetableItem timetableItem) {
        timetableItems.remove(timetableItem);
    }

    public List<TimetableItem> getTimetableItems() {
        return timetableItems;
    }

    public void setTimetableItems(List<TimetableItem> timetableItems) {
        this.timetableItems = timetableItems;
    }

}

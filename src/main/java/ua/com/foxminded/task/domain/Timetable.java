package ua.com.foxminded.task.domain;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Timetable {

    private List<TimetableItem> timetableItems;

    public Timetable() {
    }

    public Timetable(List<TimetableItem> timetableItems) {
        this.timetableItems = timetableItems;
    }

    public Timetable findPeriod(Student student, Date startPeriod, Date endPeriod) {
        Group group = student.getGroup();
        List<TimetableItem> timetableItemsFiltered = timetableItems.stream()
                .filter(g -> g.getGroups().contains(group))
                .filter(d -> (d.getDate().compareTo(startPeriod) > 0 && d.getDate().compareTo(endPeriod) < 0))
                .collect(Collectors.toList());
        return new Timetable(timetableItemsFiltered);
    }

    public Timetable findPeriod(Teacher teacher, Date startPeriod, Date endPeriod) {
        List<TimetableItem> timetableItemsFiltered = timetableItems.stream()
                .filter(t -> t.getTeacher().equals(teacher))
                .filter(d -> (d.getDate().compareTo(startPeriod) > 0 && d.getDate().compareTo(endPeriod) < 0))
                .collect(Collectors.toList());
        return new Timetable(timetableItemsFiltered);
    }

    public void addTimetableItem(TimetableItem timetableItem) {
        timetableItems.add(timetableItem);
    }

    public void retrieveTimetableItem(int id) {
        timetableItems.get(id);
    }

    public void updateTimetableItem(int id, TimetableItem timetableItem) {
        timetableItems.set(id, timetableItem);
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

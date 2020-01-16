package ua.com.foxminded.task.domain;

import java.time.LocalDate;
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

    public Timetable findSchedule(Teacher teacher, LocalDate startPeriod, LocalDate endPeriod) {
        List<TimetableItem> timetableItemsFiltered = timetableItems.stream()
                .filter(t -> t.getTeacher().equals(teacher))
                .filter(d -> (d.getDate().compareTo(startPeriod) >= 0 && d.getDate().compareTo(endPeriod) <= 0))
                .collect(Collectors.toList());
        return new Timetable(timetableItemsFiltered);
    }

    public Timetable findSchedule(Student student, LocalDate startPeriod, LocalDate endPeriod) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((timetableItems == null) ? 0 : timetableItems.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Timetable other = (Timetable) obj;
        if (timetableItems == null) {
            if (other.timetableItems != null)
                return false;
        } else if (!timetableItems.equals(other.timetableItems))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Timetable [timetableItems=" + timetableItems + "]";
    }

    
}

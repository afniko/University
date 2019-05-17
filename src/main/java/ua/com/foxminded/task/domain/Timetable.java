package ua.com.foxminded.task.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Timetable {

    private List<TimetableItem> timetableItems;

    public Timetable findPeriod(Student student, Date startPeriod, Date endPeriod) {
        return null;
    }

    public Timetable findPeriod(Teacher teacher, Date startPeriod, Date endPeriod) {
        return null;
    }
}

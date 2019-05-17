package ua.com.foxminded.task.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TimetableItem {

    private int id;
    private Subject subject;
    private Auditory auditory;
    private List<Group> groups;
    private Lecture lecture;
    private Date date;
    private Teacher teacher;

}

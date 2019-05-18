package ua.com.foxminded.task.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "subject", "auditory", "lecture", "date" })
@ToString(of = { "id", "subject", "auditory", "lecture", "date" })
public class TimetableItem {

    private int id;
    private Subject subject;
    private Auditory auditory;
    private List<Group> groups = new ArrayList<>();
    private Lecture lecture;
    private Date date;
    private Teacher teacher;

}

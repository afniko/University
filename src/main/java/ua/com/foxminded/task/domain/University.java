package ua.com.foxminded.task.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class University {

    private String title;
    private List<Faculty> faculties;
    private List<Auditory> auditories;
    private Timetable timetable;

}

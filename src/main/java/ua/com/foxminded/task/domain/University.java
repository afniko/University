package ua.com.foxminded.task.domain;

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
@EqualsAndHashCode(of = { "title" })
@ToString(of = { "title" })
public class University {

    private String title;
    private List<Faculty> faculties = new ArrayList<>();
    private List<Auditory> auditories = new ArrayList<>();
    private Timetable timetable;

    public void addFaculty(Faculty faculty) {
        faculties.add(faculty);
    }

    public void addAuditory(Auditory auditory) {
        auditories.add(auditory);
    }

    
    
    
}

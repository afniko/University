package ua.com.foxminded.task.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    public void removeFaculty(Faculty faculty) {
        faculties.remove(faculty);
    }

    public void addAuditory(Auditory auditory) {
        auditories.add(auditory);
    }

    public void removeAuditory(Auditory auditory) {
        auditories.remove(auditory);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    public List<Auditory> getAuditories() {
        return auditories;
    }

    public void setAuditories(List<Auditory> auditories) {
        this.auditories = auditories;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

}

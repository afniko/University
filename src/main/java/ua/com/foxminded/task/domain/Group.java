package ua.com.foxminded.task.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(of = { "id", "title", "department" })
@ToString(of = { "id", "title", "department" })
public class Group {

    private int id;
    private String title;
    private Department department;
    private Date yearEntry;
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Date getYearEntry() {
        return yearEntry;
    }

    public void setYearEntry(Date yearEntry) {
        this.yearEntry = yearEntry;
    }

}

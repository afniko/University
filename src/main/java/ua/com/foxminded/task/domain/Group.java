package ua.com.foxminded.task.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.domain.dto.GroupDto;

public class Group {

    private int id;
    private String title;
    private Department department;
    private Date yearEntry;
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
        student.setGroup(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setGroup(null);
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

    public GroupDto convertToDto() {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(this.id);
        groupDto.setTitle(this.title);
        groupDto.setYearEntry(this.yearEntry);
        return groupDto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((yearEntry == null) ? 0 : yearEntry.hashCode());
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
        Group other = (Group) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (yearEntry == null) {
            if (other.yearEntry != null)
                return false;
        } else if (!yearEntry.equals(other.yearEntry))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Group [id=" + id + ", title=" + title + ", department=" + department + ", yearEntry=" + yearEntry + "]";
    }

}

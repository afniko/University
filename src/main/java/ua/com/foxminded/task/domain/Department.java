package ua.com.foxminded.task.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(of = { "id", "title" })
@ToString(of = { "id", "title" })
public class Department {

    private int id;
    private String title;
    private String description;
    private List<Group> groups = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void retrieveGroup(int id) {
        groups.get(id);
    }

    public void updateGroup(int id, Group group) {
        groups.set(id, group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void retrieveTeacher(int id) {
        teachers.get(id);
    }

    public void updateTeacher(int id, Teacher teacher) {
        teachers.set(id, teacher);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

}

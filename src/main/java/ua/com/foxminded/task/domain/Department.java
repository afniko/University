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
    
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }
    
}

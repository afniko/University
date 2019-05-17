package ua.com.foxminded.task.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Department {

    private int id;
    private String title;
    private String description;
    private List<Group> groups;
    private List<Teacher> teachers;
    
}

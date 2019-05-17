package ua.com.foxminded.task.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Group {

    private int id;
    private String title;
    private Department department;
    private Date yearEntry;
    private List<Student> students;
    
    
}

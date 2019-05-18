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
@EqualsAndHashCode(of = { "id", "title", "department" })
@ToString(of = { "id", "title", "department" })
public class Group {

    private int id;
    private String title;
    private Department department;
    private Date yearEntry;
    private List<Student> students = new ArrayList<>();

}

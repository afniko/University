package ua.com.foxminded.task.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Faculty {

    private int id;
    private String title;
    private List<Department> departments;

}

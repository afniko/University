package ua.com.foxminded.task.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Teacher extends Person {

    private List<Subject> subjects;
    private Department department;

}

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
@EqualsAndHashCode(of = { "department" })
@ToString(of = { "department" })
public class Teacher extends Person {

    private List<Subject> subjects = new ArrayList<>();
    private Department department;

}

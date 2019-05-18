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
@EqualsAndHashCode(callSuper = true, of = { "department" })
@ToString(callSuper = true, of = { "id", "firstName", "idFees", "department" })
public class Teacher extends Person {

    private List<Subject> subjects = new ArrayList<>();
    private Department department;

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

}

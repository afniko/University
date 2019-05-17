package ua.com.foxminded.task.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Student extends Person {

    private Group group;

}

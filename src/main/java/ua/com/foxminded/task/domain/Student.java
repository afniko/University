package ua.com.foxminded.task.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = { "firstName", "lastName", "idFees" })
@ToString(callSuper = true, of = { "id", "firstName", "idFees" })
public class Student extends Person {

    private Group group;

}

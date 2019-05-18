package ua.com.foxminded.task.domain;

import java.sql.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "firstName", "lastName", "idFees" })
@ToString(of = { "id", "firstName", "idFees" })
public abstract class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthday;
    private int idFees;

}

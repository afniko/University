package ua.com.foxminded.task.domain;

import java.sql.Date;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(of = { "firstName", "lastName", "idFees" })
@ToString(of = { "id", "firstName", "idFees" })
public abstract class Person {

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String middleName;
    protected Date birthday;
    protected int idFees;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getIdFees() {
        return idFees;
    }

    public void setIdFees(int idFees) {
        this.idFees = idFees;
    }

}

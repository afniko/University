package ua.com.foxminded.task.domain.dto;

import java.sql.Date;

import ua.com.foxminded.task.domain.Group;

public class StudentDto {

    protected int id;
    protected String firstName;
    protected String middleName;
    protected Date birthday;
    protected int idFees;
    private Group group;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setIdFees(int idFees) {
        this.idFees = idFees;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getIdFees() {
        return idFees;
    }

    public Group getGroup() {
        return group;
    }

}

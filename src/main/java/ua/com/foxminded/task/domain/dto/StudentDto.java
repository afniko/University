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

}

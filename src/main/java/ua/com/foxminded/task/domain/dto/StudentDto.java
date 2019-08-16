package ua.com.foxminded.task.domain.dto;

import java.sql.Date;

public class StudentDto {

    protected int id;
    protected String firstName;
    protected String middleName;
    protected Date birthday;
    protected int idFees;
    private String groupTitle;

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

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

}

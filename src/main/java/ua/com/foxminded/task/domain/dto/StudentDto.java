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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((groupTitle == null) ? 0 : groupTitle.hashCode());
        result = prime * result + idFees;
        result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentDto other = (StudentDto) obj;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (groupTitle == null) {
            if (other.groupTitle != null)
                return false;
        } else if (!groupTitle.equals(other.groupTitle))
            return false;
        if (idFees != other.idFees)
            return false;
        if (middleName == null) {
            if (other.middleName != null)
                return false;
        } else if (!middleName.equals(other.middleName))
            return false;
        return true;
    }

}

package ua.com.foxminded.task.domain.dto;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ua.com.foxminded.task.validation.annotation.MaxStudentsInGroupLimit;
import ua.com.foxminded.task.validation.annotation.PropertyValueUnique;

@MaxStudentsInGroupLimit
@PropertyValueUnique(message = "Id fees is already exists!", 
                     nameProperty = "idFees", 
                     fieldError = "idFees")
@ApiModel(description = "Object with a student and group information")
public class StudentDto {

    @Min(value = 0, message = "Id must be more than zero!")
    @ApiModelProperty(notes = "The database generated student id")
    private int id;

    @NotBlank(message = "First name can`t be blank!")
    @Length(max = 20, message = "Maximum length of first name is 20!")
    @ApiModelProperty(notes = "The student first name. Not blank. Max length is 20 character")
    private String firstName;

    @Length(max = 20, message = "Maximum length of middle name is 20!")
    @ApiModelProperty(notes = "The student middle name. Max length is 20 character")
    private String middleName;

    @Length(max = 20, message = "Maximum length of last name is 20!")
    @ApiModelProperty(notes = "The student last name. Max length is 20 character")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birthday date must be in past!")
    @ApiModelProperty(notes = "The student birthday date. Date must be in past")
    private LocalDate birthday;

    @Min(value = 100000000, message = "Value is 9 number!")
    @Max(value = 999999999, message = "Value is 9 number!")
    @ApiModelProperty(notes = "The student identification number in an fees authority. Value is 9 number")
    private int idFees;

    @Length(max = 20, message = "Maximum length of title group is 20!")
    @ApiModelProperty(notes = "The students name of group. Max length is 20 character")
    private String groupTitle;

    @ApiModelProperty(notes = "The unique number of students group")
    private int idGroup;

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getIdFees() {
        return idFees;
    }

    public void setIdFees(int idFees) {
        this.idFees = idFees;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + idFees;
        result = prime * result + idGroup;
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
        if (idFees != other.idFees)
            return false;
        if (idGroup != other.idGroup)
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (middleName == null) {
            if (other.middleName != null)
                return false;
        } else if (!middleName.equals(other.middleName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "StudentDto [id=" + id 
             + ", firstName=" + firstName 
             + ", middleName=" + middleName 
             + ", lastName=" + lastName 
             + ", birthday=" + birthday 
             + ", idFees=" + idFees 
             + ", groupTitle=" + groupTitle 
             + ", idGroup=" + idGroup 
             + "]";
    }

}

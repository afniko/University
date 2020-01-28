package ua.com.foxminded.task.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ua.com.foxminded.task.validation.annotation.PropertyValueUnique;

@PropertyValueUnique(message = "Department title is already exists!", 
                     nameProperty = "title",
                     fieldError = "title")
@ApiModel(description = "Object with a department information")
public class DepartmentDto {

    @Min(value = 0, message = "Id must be more than zero!")
    @ApiModelProperty(notes = "The database generated department id")
    private int id;
    
    @NotBlank(message = "Title can`t be blank!")
    @Length(max = 20, message = "Maximum length is 20!")
    @ApiModelProperty(notes = "The unique name of department. Max length is 20 character")
    private String title;

    @Length(max = 200, message = "Maximum length of department description is 200!")
    @ApiModelProperty(notes = "The description of department. Max length is 200 character")
    private String description;

    @Length(max = 20, message = "Maximum length of faculty title is 20!")
    @ApiModelProperty(notes = "The faculty title. Max length is 20 character")
    private String facultyTitle;

    @ApiModelProperty(notes = "The unique number of faculty")
    private int facultyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacultyTitle() {
        return facultyTitle;
    }

    public void setFacultyTitle(String facultyTitle) {
        this.facultyTitle = facultyTitle;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((facultyTitle == null) ? 0 : facultyTitle.hashCode());
        result = prime * result + facultyId;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        DepartmentDto other = (DepartmentDto) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (facultyTitle == null) {
            if (other.facultyTitle != null)
                return false;
        } else if (!facultyTitle.equals(other.facultyTitle))
            return false;
        if (facultyId != other.facultyId)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DepartmentDto [id=" + id 
             + ", title=" + title 
             + ", description=" + description 
             + ", facultyTitle=" + facultyTitle 
             + ", facultyId=" + facultyId 
             + "]";
    }

}

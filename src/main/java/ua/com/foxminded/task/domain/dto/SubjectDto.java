package ua.com.foxminded.task.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ua.com.foxminded.task.validation.annotation.PropertyValueUnique;

@PropertyValueUnique(message = "Subject title is already exists!", 
                     nameProperty = "title",
                     fieldError = "title")
@ApiModel(description = "Object with a subject information")
public class SubjectDto {

    @Min(value = 0, message = "Id must be more than zero!")
    @ApiModelProperty(notes = "The database generated subject id")
    private int id;

    @NotBlank(message = "Title can`t be blank!")
    @Length(max = 20, message = "Maximum length is 20!")
    @ApiModelProperty(notes = "The unique name of subject. Max length is 20 character")
    private String title;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        SubjectDto other = (SubjectDto) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SubjectDto [id=" + id 
             + ", title=" + title 
             + "]";
    }

}

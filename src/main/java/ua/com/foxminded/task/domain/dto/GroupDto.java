package ua.com.foxminded.task.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import ua.com.foxminded.task.validator.GroupTitleUnique;

public class GroupDto {

    private int id;

    @GroupTitleUnique(message = "Title is already exists!")
    @NotBlank(message = "Title can`t be blank!")
    @Length(max = 20, message = "Maximum length is 20!")
    private String title;

    @NotNull(message = "Year of entry can`t be blank or not numeric!")
    @Max(value = 2100, message = "Year of entry is not correct!")
    @Min(value = 2000, message = "Year of entry is not correct!")
    private Integer yearEntry;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYearEntry(Integer yearEntry) {
        this.yearEntry = yearEntry;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYearEntry() {
        return yearEntry;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((yearEntry == null) ? 0 : yearEntry.hashCode());
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
        GroupDto other = (GroupDto) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (yearEntry == null) {
            if (other.yearEntry != null)
                return false;
        } else if (!yearEntry.equals(other.yearEntry))
            return false;
        return true;
    }

   

}

package ua.com.foxminded.task.domain.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Object for finding period timetableItems")
public class SearchPeriodDto {

    @Min(value = 0, message = "Id must be more than zero!")
    @ApiModelProperty(notes = "The id of entity")
    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "The date of search period starting.")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "The date of search period be finished.")
    private LocalDate endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + id;
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
        SearchPeriodDto other = (SearchPeriodDto) obj;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (id != other.id)
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SearchPeriodDto [id=" + id 
             + ", startDate=" + startDate 
             + ", endDate=" + endDate 
             + "]";
    }

}

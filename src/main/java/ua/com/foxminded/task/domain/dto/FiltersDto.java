package ua.com.foxminded.task.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Object for finding period timetableItems")
public class FiltersDto implements Serializable {

    private static final long serialVersionUID = 5672207011636676194L;

    @Min(value = 0, message = "Id must be more than zero!")
    @ApiModelProperty(notes = "The teacher id of entity")
    private int teacherId;

    @Min(value = 0, message = "Id must be more than zero!")
    @ApiModelProperty(notes = "The student id of entity")
    private int studentId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "The date of search period starting.")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "The date of search period be finished.")
    private LocalDate endDate;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + studentId;
        result = prime * result + teacherId;
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
        FiltersDto other = (FiltersDto) obj;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (studentId != other.studentId)
            return false;
        if (teacherId != other.teacherId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FiltersDto [teacherId=" + teacherId 
             + ", studentId=" + studentId 
             + ", startDate=" + startDate 
             + ", endDate=" + endDate 
             + "]";
    }

}

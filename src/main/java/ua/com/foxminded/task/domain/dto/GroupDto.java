package ua.com.foxminded.task.domain.dto;

import java.sql.Date;

public class GroupDto {

    private int id;
    private String title;
    private Date yearEntry;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYearEntry(Date yearEntry) {
        this.yearEntry = yearEntry;
    }

}

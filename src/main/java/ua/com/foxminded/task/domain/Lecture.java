package ua.com.foxminded.task.domain;

import java.sql.Time;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Lecture {

    private int id;
    private String number;
    private Time startTime;
    private Time endTime;

}

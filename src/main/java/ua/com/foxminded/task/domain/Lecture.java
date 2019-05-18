package ua.com.foxminded.task.domain;

import java.sql.Time;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Lecture {

    private int id;
    private String number;
    private Time startTime;
    private Time endTime;

}

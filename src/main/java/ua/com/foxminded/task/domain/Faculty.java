package ua.com.foxminded.task.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "title" })
@ToString(of = { "id", "title" })
public class Faculty {

    private int id;
    private String title;
    private List<Department> departments = new ArrayList<>();

}

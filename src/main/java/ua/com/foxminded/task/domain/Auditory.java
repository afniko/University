package ua.com.foxminded.task.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "auditoryNumber" })
@ToString(of = { "auditoryNumber" })
public class Auditory {

    private int id;
    private String auditoryNumber;
    private AuditoryType type;
    private int maxCapacity;
    private String description;

}

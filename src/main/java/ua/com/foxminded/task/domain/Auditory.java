package ua.com.foxminded.task.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Auditory {

    private int id;
    private String auditoryNumber;
    private AuditoryType type;
    private int maxCapacity;
    private String description;

}

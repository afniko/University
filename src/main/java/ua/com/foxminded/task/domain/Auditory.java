package ua.com.foxminded.task.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(of = { "auditoryNumber" })
@ToString(of = { "auditoryNumber" })
public class Auditory {

    private int id;
    private String auditoryNumber;
    private AuditoryType type;
    private int maxCapacity;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuditoryNumber() {
        return auditoryNumber;
    }

    public void setAuditoryNumber(String auditoryNumber) {
        this.auditoryNumber = auditoryNumber;
    }

    public AuditoryType getType() {
        return type;
    }

    public void setType(AuditoryType type) {
        this.type = type;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

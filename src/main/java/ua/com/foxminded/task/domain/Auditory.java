package ua.com.foxminded.task.domain;

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

    public AuditoryType getAuditoryType() {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((auditoryNumber == null) ? 0 : auditoryNumber.hashCode());
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
        Auditory other = (Auditory) obj;
        if (auditoryNumber == null) {
            if (other.auditoryNumber != null)
                return false;
        } else if (!auditoryNumber.equals(other.auditoryNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Auditory [id=" + id + ", auditoryNumber=" + auditoryNumber + ", type=" + type + "]";
    }

}

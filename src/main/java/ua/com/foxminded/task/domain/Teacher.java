package ua.com.foxminded.task.domain;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

    private List<Subject> subjects = new ArrayList<>();
    private Department department;

    public Teacher() {
        super();
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((subjects == null) ? 0 : subjects.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Teacher other = (Teacher) obj;
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
            return false;
        if (subjects == null) {
            if (other.subjects != null)
                return false;
        } else if (!subjects.equals(other.subjects))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Teacher [subjects=" + subjects 
             + ", department=" + department 
             + ", id=" + id 
             + ", firstName=" + firstName 
             + ", lastName=" + lastName 
             + ", middleName=" + middleName 
             + ", birthday=" + birthday 
             + ", idFees=" + idFees 
             + "]";
    }

    

}

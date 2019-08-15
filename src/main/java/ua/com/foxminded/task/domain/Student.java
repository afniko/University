package ua.com.foxminded.task.domain;

import ua.com.foxminded.task.domain.dto.StudentDto;

public class Student extends Person {

    private Group group;

    public Student() {
        super();
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public StudentDto convertToDto() {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(this.id);
        studentDto.setFirstName(this.firstName);
        studentDto.setMiddleName(this.middleName);
        studentDto.setBirthday(this.birthday);
        studentDto.setIdFees(this.idFees);
        studentDto.setGroup(this.group);
        return studentDto;
    }

    @Override
    public String toString() {
        return "Student [group=" + group + ", id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName + ", birthday=" + birthday + ", idFees=" + idFees + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((group == null) ? 0 : group.hashCode());
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
        Student other = (Student) obj;
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        return true;
    }

}

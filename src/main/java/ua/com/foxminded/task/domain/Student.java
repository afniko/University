package ua.com.foxminded.task.domain;

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

    @Override
    public String toString() {
        return "Student [group=" + group + ", id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName + ", birthday=" + birthday + ", idFees=" + idFees + "]";
    }

}

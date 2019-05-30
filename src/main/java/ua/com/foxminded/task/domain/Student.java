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

}

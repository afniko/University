package ua.com.foxminded.task.domain.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.Student;

public class StudentModelRepository {

    public static List<Student> getModels() {
        List<Student> students = Arrays.asList(getModel1(), getModel2(), getModel3(), getModel4(), getModel5(), getModel6());
        return new ArrayList<>(students);
    }

    public static List<Student> getModels1() {
        List<Student> students = Arrays.asList(getModel1(), getModel2());
        return new ArrayList<>(students);
    }

    public static List<Student> getModels2() {
        List<Student> students = Arrays.asList(getModel3(), getModel4());
        return new ArrayList<>(students);
    }

    public static List<Student> getModels3() {
        List<Student> students = Arrays.asList(getModel5(), getModel6());
        return new ArrayList<>(students);
    }

    public static Student getModel1() {
        Student student = new Student();
        student.setFirstName("firstName1");
        student.setMiddleName("middleName1");
        student.setLastName("lastName1");
        student.setBirthday(Date.valueOf("1999-06-25"));
        student.setIdFees(1111111111);
        return student;
    }

    public static Student getModel2() {
        Student student = new Student();
        student.setFirstName("firstName2");
        student.setMiddleName("middleName2");
        student.setLastName("lastName2");
        student.setBirthday(Date.valueOf("1998-06-25"));
        student.setIdFees(1222211111);
        return student;
    }

    public static Student getModel3() {
        Student student = new Student();
        student.setFirstName("firstName3");
        student.setMiddleName("middleName3");
        student.setLastName("lastName3");
        student.setBirthday(Date.valueOf("2001-07-25"));
        student.setIdFees(1111133331);
        return student;
    }

    private static Student getModel4() {
        Student student = new Student();
        student.setFirstName("firstName4");
        student.setMiddleName("middleName4");
        student.setLastName("lastName4");
        student.setBirthday(Date.valueOf("1999-06-25"));
        student.setIdFees(1411111141);
        return student;
    }

    private static Student getModel5() {
        Student student = new Student();
        student.setFirstName("firstName5");
        student.setMiddleName("middleName5");
        student.setLastName("lastName5");
        student.setBirthday(Date.valueOf("1998-01-20"));
        student.setIdFees(1111111551);
        return student;
    }

    private static Student getModel6() {
        Student student = new Student();
        student.setFirstName("firstName6");
        student.setMiddleName("middleName6");
        student.setLastName("lastName6");
        student.setBirthday(Date.valueOf("1997-02-15"));
        student.setIdFees(1111111661);
        return student;
    }

}

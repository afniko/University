package ua.com.foxminded.task.domain.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.domain.Student;

public class StudentModelRepository {

    private static Student student;
    private static List<Student> students;

    public static Student getModel(TestModel testModel) {
        switch (testModel) {
        case MODEL_1:
            createModel1(testModel);
            break;
        case MODEL_2:
            createModel2(testModel);
            break;
        case MODEL_3:
            createModel3(testModel);
            break;
        case MODEL_4:
            createModel4(testModel);
            break;
        case MODEL_5:
            createModel5(testModel);
            break;
        case MODEL_6:
            createModel6(testModel);
            break;
        }
        return student;
    }

    public static List<Student> getList(TestModel testModel) {
        students = new ArrayList<>();
        switch (testModel) {
        case MODEL_1:
            createModel1(testModel);
            students.add(student);
            createModel2(testModel);
            students.add(student);
            break;
        case MODEL_2:
            createModel2(testModel);
            students.add(student);
            createModel3(testModel);
            students.add(student);
            break;
        case MODEL_3:
            createModel1(testModel);
            students.add(student);
            createModel6(testModel);
            students.add(student);
            break;
        case MODEL_4:
            createModel4(testModel);
            students.add(student);
            createModel5(testModel);
            students.add(student);
            break;
        case MODEL_5:
            createModel3(testModel);
            students.add(student);
            createModel6(testModel);
            students.add(student);
            break;
        case MODEL_6:
            createModel3(testModel);
            students.add(student);
            createModel5(testModel);
            students.add(student);
            break;
        }
        return students;
    }

    private static void createModel1(TestModel testModel) {
        student = new Student();
        student.setId(1);
        student.setFirstName("firstName1");
        student.setMiddleName("middleName1");
        student.setLastName("lastName1");
        student.setBirthday(Date.valueOf("1999-06-25"));
        student.setIdFees(1111111111);
    }

    private static void createModel2(TestModel testModel) {
        student = new Student();
        student.setId(2);
        student.setFirstName("firstName2");
        student.setMiddleName("middleName2");
        student.setLastName("lastName2");
        student.setBirthday(Date.valueOf("1998-06-25"));
        student.setIdFees(1222211111);
    }

    private static void createModel3(TestModel testModel) {
        student = new Student();
        student.setId(3);
        student.setFirstName("firstName3");
        student.setMiddleName("middleName3");
        student.setLastName("lastName3");
        student.setBirthday(Date.valueOf("2001-07-25"));
        student.setIdFees(1111133331);
    }

    private static void createModel4(TestModel testModel) {
        student = new Student();
        student.setId(4);
        student.setFirstName("firstName4");
        student.setMiddleName("middleName4");
        student.setLastName("lastName4");
        student.setBirthday(Date.valueOf("1999-06-25"));
        student.setIdFees(1411111141);
    }

    private static void createModel5(TestModel testModel) {
        student = new Student();
        student.setId(5);
        student.setFirstName("firstName5");
        student.setMiddleName("middleName5");
        student.setLastName("lastName5");
        student.setBirthday(Date.valueOf("1998-01-20"));
        student.setIdFees(1111111551);
    }

    private static void createModel6(TestModel testModel) {
        student = new Student();
        student.setId(6);
        student.setFirstName("firstName6");
        student.setMiddleName("middleName6");
        student.setLastName("lastName6");
        student.setBirthday(Date.valueOf("1997-02-15"));
        student.setIdFees(1111111661);
    }

}

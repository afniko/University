package ua.com.foxminded.task.domain.repository;

import java.sql.Time;

import ua.com.foxminded.task.domain.Lecture;
import ua.com.foxminded.task.domain.Student;

public class StudentModelRepository {

    private static Student student;

    public static Student getModel(TestModel testModel) {
        switch (testModel) {
        case MODEL_1:
            createModel1();
            break;
        case MODEL_2:
            createModel2();
            break;
        case MODEL_3:
            createModel3();
            break;
        case MODEL_4:
            createModel4();
            break;
        case MODEL_5:
            createModel5();
            break;
        case MODEL_6:
            createModel6();
            break;
        }
        return student;
    }

    private static void createModel1() {
        student = new Student();
        student.setId(1);

    }

    private static void createModel2() {
        lecture = new Lecture();
        lecture.setId(2);
        lecture.setNumber("2");
        lecture.setStartTime(Time.valueOf("09:30:00"));
        lecture.setEndTime(Time.valueOf("11:05:00"));
    }

    private static void createModel3() {
        lecture = new Lecture();
        lecture.setId(3);
        lecture.setNumber("3");
        lecture.setStartTime(Time.valueOf("11:15:00"));
        lecture.setEndTime(Time.valueOf("12:50:00"));
    }

    private static void createModel4() {
        lecture = new Lecture();
        lecture.setId(4);
        lecture.setNumber("4");
        lecture.setStartTime(Time.valueOf("13:10:00"));
        lecture.setEndTime(Time.valueOf("14:45:00"));
    }

    private static void createModel5() {
        lecture = new Lecture();
        lecture.setId(5);
        lecture.setNumber("5");
        lecture.setStartTime(Time.valueOf("14:55:00"));
        lecture.setEndTime(Time.valueOf("16:30:00"));
    }

    private static void createModel6() {
        lecture = new Lecture();
        lecture.setId(6);
        lecture.setNumber("6");
        lecture.setStartTime(Time.valueOf("16:40:00"));
        lecture.setEndTime(Time.valueOf("18:15:00"));
    }

}

package ua.com.foxminded.task.domain.repository;

import java.sql.Time;

import ua.com.foxminded.task.domain.Lecture;

public class LectureModelRepository {

    public static Lecture getModel1() {
        Lecture lecture = new Lecture();
        lecture.setNumber("1");
        lecture.setStartTime(Time.valueOf("07:45:00"));
        lecture.setEndTime(Time.valueOf("09:20:00"));
        return lecture;
    }

    public static Lecture getModel2() {
        Lecture lecture = new Lecture();
        lecture.setNumber("2");
        lecture.setStartTime(Time.valueOf("09:30:00"));
        lecture.setEndTime(Time.valueOf("11:05:00"));
        return lecture;
    }

    public static Lecture getModel3() {
        Lecture lecture = new Lecture();
        lecture.setNumber("3");
        lecture.setStartTime(Time.valueOf("11:15:00"));
        lecture.setEndTime(Time.valueOf("12:50:00"));
        return lecture;
    }

    public static Lecture getModel4() {
        Lecture lecture = new Lecture();
        lecture.setNumber("4");
        lecture.setStartTime(Time.valueOf("13:10:00"));
        lecture.setEndTime(Time.valueOf("14:45:00"));
        return lecture;
    }

    public static Lecture getModel5() {
        Lecture lecture = new Lecture();
        lecture.setNumber("5");
        lecture.setStartTime(Time.valueOf("14:55:00"));
        lecture.setEndTime(Time.valueOf("16:30:00"));
        return lecture;
    }

    public static Lecture getModel6() {
        Lecture lecture = new Lecture();
        lecture.setNumber("6");
        lecture.setStartTime(Time.valueOf("16:40:00"));
        lecture.setEndTime(Time.valueOf("18:15:00"));
        return lecture;
    }

}

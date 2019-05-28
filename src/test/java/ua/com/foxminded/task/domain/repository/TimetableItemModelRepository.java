package ua.com.foxminded.task.domain.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.TimetableItem;

public class TimetableItemModelRepository {

    public static List<TimetableItem> getModels() {
        List<TimetableItem> timetableItems = Arrays.asList(getModel1(), getModel2(), getModel3(), getModel4(), getModel5(), getModel6());
        return new ArrayList<>(timetableItems);
    }

    public static TimetableItem getEmptyModel() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setId(1);
        timetableItem.setDate(Date.valueOf("2019-01-21"));
        return timetableItem;
    }

    public static TimetableItem getModel1() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setId(1);
        timetableItem.setSubject(SubjectModelRepository.getModel());
        timetableItem.setAuditory(AuditoryModelRepository.getModel1());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel1());
        timetableItem.setDate(Date.valueOf("2019-01-21"));
        timetableItem.setTeacher(TeacherModelRepository.getModel());
        return timetableItem;
    }

    private static TimetableItem getModel2() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setId(2);
        timetableItem.setSubject(SubjectModelRepository.getModel());
        timetableItem.setAuditory(AuditoryModelRepository.getModel2());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel2());
        timetableItem.setDate(Date.valueOf("2019-01-21"));
        timetableItem.setTeacher(TeacherModelRepository.getModel());
        return timetableItem;
    }

    private static TimetableItem getModel3() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setId(3);
        timetableItem.setSubject(SubjectModelRepository.getModel());
        timetableItem.setAuditory(AuditoryModelRepository.getModel3());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel3());
        timetableItem.setDate(Date.valueOf("2019-01-21"));
        timetableItem.setTeacher(TeacherModelRepository.getModel());
        return timetableItem;
    }

    private static TimetableItem getModel4() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setId(4);
        timetableItem.setSubject(SubjectModelRepository.getModel());
        timetableItem.setAuditory(AuditoryModelRepository.getModel4());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel4());
        timetableItem.setDate(Date.valueOf("2019-01-21"));
        timetableItem.setTeacher(TeacherModelRepository.getModel());
        return timetableItem;
    }

    private static TimetableItem getModel5() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setId(5);
        timetableItem.setSubject(SubjectModelRepository.getModel());
        timetableItem.setAuditory(AuditoryModelRepository.getModel5());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel5());
        timetableItem.setDate(Date.valueOf("2019-01-21"));
        timetableItem.setTeacher(TeacherModelRepository.getModel());
        return timetableItem;
    }

    private static TimetableItem getModel6() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setId(6);
        timetableItem.setSubject(SubjectModelRepository.getModel());
        timetableItem.setAuditory(AuditoryModelRepository.getModel6());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel6());
        timetableItem.setDate(Date.valueOf("2019-01-21"));
        timetableItem.setTeacher(TeacherModelRepository.getModel());
        return timetableItem;
    }

}

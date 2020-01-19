package ua.com.foxminded.task.domain.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.TimetableItem;

public class TimetableItemModelRepository {
    
    private TimetableItemModelRepository() {
    }

    public static List<TimetableItem> getModels() {
        List<TimetableItem> timetableItems = Arrays.asList(
                getModel1(), 
                getModel2(), 
                getModel3(), 
                getModel4(), 
                getModel5(), 
                getModel6());
        return new ArrayList<>(timetableItems);
    }

    public static TimetableItem getEmptyModel() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setDate(LocalDate.of(2019,01,21));
        return timetableItem;
    }

    public static List<TimetableItem> getTimetableItemsStudentsExpected() {
        List<TimetableItem> timetableItemsExpected = Arrays.asList(
                TimetableItemModelRepository.getModel3(), 
                TimetableItemModelRepository.getModel4(), 
                TimetableItemModelRepository.getModel5());
        return timetableItemsExpected;
    }

    public static List<TimetableItem> getTimetableItemsStudentUnexpected() {
        List<TimetableItem> timetableItemsUnexpected = Arrays.asList(
                TimetableItemModelRepository.getModel1(), 
                TimetableItemModelRepository.getModel2(), 
                TimetableItemModelRepository.getModel6());
        return timetableItemsUnexpected;
    }

    public static List<TimetableItem> getTimetableItemsTeacherExpected() {
        List<TimetableItem> timetableItemsExpected = Arrays.asList(
                TimetableItemModelRepository.getModel2(), 
                TimetableItemModelRepository.getModel4());
        return timetableItemsExpected;
    }

    public static List<TimetableItem> getTimetableItemsTeacherUnexpected() {
        List<TimetableItem> timetableItemsUnexpected = Arrays.asList(
                TimetableItemModelRepository.getModel1(), 
                TimetableItemModelRepository.getModel3(), 
                TimetableItemModelRepository.getModel5(),
                TimetableItemModelRepository.getModel6());
        return timetableItemsUnexpected;
    }

    public static TimetableItem getModel1() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setSubject(SubjectModelRepository.getModel1());
        timetableItem.setAuditory(AuditoryModelRepository.getModel1());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel1());
        timetableItem.setDate(LocalDate.of(2019,01,21));
        timetableItem.setTeacher(TeacherModelRepository.getModel1());
        return timetableItem;
    }

    public static TimetableItem getModel2() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setSubject(SubjectModelRepository.getModel2());
        timetableItem.setAuditory(AuditoryModelRepository.getModel2());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel2());
        timetableItem.setDate(LocalDate.of(2019,01,22));
        timetableItem.setTeacher(TeacherModelRepository.getModel1());
        return timetableItem;
    }

    public static TimetableItem getModel3() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setSubject(SubjectModelRepository.getModel3());
        timetableItem.setAuditory(AuditoryModelRepository.getModel3());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel3());
        timetableItem.setDate(LocalDate.of(2019,02,01));
        timetableItem.setTeacher(TeacherModelRepository.getModel2());
        return timetableItem;
    }

    public static TimetableItem getModel4() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setSubject(SubjectModelRepository.getModel4());
        timetableItem.setAuditory(AuditoryModelRepository.getModel4());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel4());
        timetableItem.setDate(LocalDate.of(2019,02,15));
        timetableItem.setTeacher(TeacherModelRepository.getModel1());
        return timetableItem;
    }

    public static TimetableItem getModel5() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setSubject(SubjectModelRepository.getModel1());
        timetableItem.setAuditory(AuditoryModelRepository.getModel5());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel5());
        timetableItem.setDate(LocalDate.of(2019,03,05));
        timetableItem.setTeacher(TeacherModelRepository.getModel3());
        return timetableItem;
    }

    public static TimetableItem getModel6() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setSubject(SubjectModelRepository.getModel2());
        timetableItem.setAuditory(AuditoryModelRepository.getModel6());
        timetableItem.setGroups(GroupModelRepository.getModels());
        timetableItem.setLecture(LectureModelRepository.getModel6());
        timetableItem.setDate(LocalDate.of(2019,04,24));
        timetableItem.setTeacher(TeacherModelRepository.getModel1());
        return timetableItem;
    }

}

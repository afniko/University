package ua.com.foxminded.task.domain.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.domain.TimetableItem;

public class TimetableItemModelRepository {

    private static TimetableItem timetableItem;
    private static List<TimetableItem> timetableItems;

    public static TimetableItem getModel(TestModel testModel) {
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
        return timetableItem;
    }

    public static List<TimetableItem> getList(TestModel testModel) {
        timetableItems = new ArrayList<>();
        switch (testModel) {
        case MODEL_1:
            createModel1(testModel);
            timetableItems.add(timetableItem);
            createModel2(testModel);
            timetableItems.add(timetableItem);
            createModel3(testModel);
            timetableItems.add(timetableItem);
            createModel4(testModel);
            timetableItems.add(timetableItem);
            createModel5(testModel);
            timetableItems.add(timetableItem);
            createModel6(testModel);
            timetableItems.add(timetableItem);
            break;
        case MODEL_2:
            createModel2(testModel);
            timetableItems.add(timetableItem);
            createModel3(testModel);
            timetableItems.add(timetableItem);
            break;
        case MODEL_3:
            createModel1(testModel);
            timetableItems.add(timetableItem);
            createModel6(testModel);
            timetableItems.add(timetableItem);
            break;
        case MODEL_4:
            createModel4(testModel);
            timetableItems.add(timetableItem);
            createModel5(testModel);
            timetableItems.add(timetableItem);
            break;
        case MODEL_5:
            createModel3(testModel);
            timetableItems.add(timetableItem);
            createModel6(testModel);
            timetableItems.add(timetableItem);
            break;
        case MODEL_6:
            createModel3(testModel);
            timetableItems.add(timetableItem);
            createModel5(testModel);
            timetableItems.add(timetableItem);
            break;
        }
        return timetableItems;
    }

    private static void createModel1(TestModel testModel) {
        timetableItem = new TimetableItem();
        timetableItem.setId(1);
        timetableItem.setSubject(SubjectModelRepository.getModel(testModel));
        timetableItem.setAuditory(AuditoryModelRepository.getModel(testModel));
        timetableItem.setGroups(GroupModelRepository.getList(testModel));
        timetableItem.setLecture(LectureModelRepository.getModel(testModel));
        timetableItem.setDate(Date.valueOf("2019-01-21"));
        timetableItem.setTeacher(TeacherModelRepository.getModel(testModel));
    }

    private static void createModel2(TestModel testModel) {
        timetableItem = new TimetableItem();
        timetableItem.setId(2);
        timetableItem.setSubject(SubjectModelRepository.getModel(testModel));
        timetableItem.setAuditory(AuditoryModelRepository.getModel(testModel));
        timetableItem.setGroups(GroupModelRepository.getList(testModel));
        timetableItem.setLecture(LectureModelRepository.getModel(testModel));
        timetableItem.setDate(Date.valueOf("2019-01-21"));
        timetableItem.setTeacher(TeacherModelRepository.getModel(testModel));
    }

    private static void createModel3(TestModel testModel) {
        timetableItem = new TimetableItem();
        timetableItem.setId(3);
        timetableItem.setSubject(SubjectModelRepository.getModel(testModel));
        timetableItem.setAuditory(AuditoryModelRepository.getModel(testModel));
        timetableItem.setGroups(GroupModelRepository.getList(testModel));
        timetableItem.setLecture(LectureModelRepository.getModel(testModel));
        timetableItem.setDate(Date.valueOf("2019-01-21"));
        timetableItem.setTeacher(TeacherModelRepository.getModel(testModel));
    }

    private static void createModel4(TestModel testModel) {
        timetableItem = new TimetableItem();
        timetableItem.setId(4);
        timetableItem.setSubject(SubjectModelRepository.getModel(testModel));
        timetableItem.setAuditory(AuditoryModelRepository.getModel(testModel));
        timetableItem.setGroups(GroupModelRepository.getList(testModel));
        timetableItem.setLecture(LectureModelRepository.getModel(testModel));
        timetableItem.setDate(Date.valueOf("2019-01-22"));
        timetableItem.setTeacher(TeacherModelRepository.getModel(testModel));
    }

    private static void createModel5(TestModel testModel) {
        timetableItem = new TimetableItem();
        timetableItem.setId(5);
        timetableItem.setSubject(SubjectModelRepository.getModel(testModel));
        timetableItem.setAuditory(AuditoryModelRepository.getModel(testModel));
        timetableItem.setGroups(GroupModelRepository.getList(testModel));
        timetableItem.setLecture(LectureModelRepository.getModel(testModel));
        timetableItem.setDate(Date.valueOf("2019-02-23"));
        timetableItem.setTeacher(TeacherModelRepository.getModel(testModel));
    }

    private static void createModel6(TestModel testModel) {
        timetableItem = new TimetableItem();
        timetableItem.setId(6);
        timetableItem.setSubject(SubjectModelRepository.getModel(testModel));
        timetableItem.setAuditory(AuditoryModelRepository.getModel(testModel));
        timetableItem.setGroups(GroupModelRepository.getList(testModel));
        timetableItem.setLecture(LectureModelRepository.getModel(testModel));
        timetableItem.setDate(Date.valueOf("2019-01-01"));
        timetableItem.setTeacher(TeacherModelRepository.getModel(testModel));
    }

}

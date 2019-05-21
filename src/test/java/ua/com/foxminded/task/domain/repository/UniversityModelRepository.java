package ua.com.foxminded.task.domain.repository;

import ua.com.foxminded.task.domain.University;

public class UniversityModelRepository {

    private static University university = new University();

    public static University getModel(TestModel testModel) {
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
        return university;
    }

    private static void createModel1(TestModel testModel) {
        university = new University();
        university.setTitle("university1");
        university.setFaculties(FacultyModelRepository.getList(testModel));
        university.setAuditories(AuditoryModelRepository.getList(testModel));
        university.setTimetable(TimetableModelRepository.getModel(testModel));
    }

    private static void createModel2(TestModel testModel) {
        university = new University();
        university.setTitle("university2");
        university.setFaculties(FacultyModelRepository.getList(testModel));
        university.setAuditories(AuditoryModelRepository.getList(testModel));
        university.setTimetable(TimetableModelRepository.getModel(testModel));
    }

    private static void createModel3(TestModel testModel) {
        university = new University();
        university.setTitle("university3");
        university.setFaculties(FacultyModelRepository.getList(testModel));
        university.setAuditories(AuditoryModelRepository.getList(testModel));
        university.setTimetable(TimetableModelRepository.getModel(testModel));
    }

    private static void createModel4(TestModel testModel) {
        university = new University();
        university.setTitle("university4");
        university.setFaculties(FacultyModelRepository.getList(testModel));
        university.setAuditories(AuditoryModelRepository.getList(testModel));
        university.setTimetable(TimetableModelRepository.getModel(testModel));
    }

    private static void createModel5(TestModel testModel) {
        university = new University();
        university.setTitle("university5");
        university.setFaculties(FacultyModelRepository.getList(testModel));
        university.setAuditories(AuditoryModelRepository.getList(testModel));
        university.setTimetable(TimetableModelRepository.getModel(testModel));
    }

    private static void createModel6(TestModel testModel) {
        university = new University();
        university.setTitle("university6");
        university.setFaculties(FacultyModelRepository.getList(testModel));
        university.setAuditories(AuditoryModelRepository.getList(testModel));
        university.setTimetable(TimetableModelRepository.getModel(testModel));
    }

}

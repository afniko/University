package ua.com.foxminded.task.domain.repository;

import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.domain.Faculty;

public class FacultyModelRepository {

    private static Faculty faculty;
    private static List<Faculty> faculties;

    public static Faculty getModel(TestModel testModel) {
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
        case MODEL_EMPTY:
            createModel6(testModel);
            break;
        }
        return faculty;
    }

    public static List<Faculty> getList(TestModel testModel) {
        faculties = new ArrayList<>();
        switch (testModel) {
        case MODEL_1:
            createModel1(testModel);
            faculties.add(faculty);
            createModel2(testModel);
            faculties.add(faculty);
            break;
        case MODEL_2:
            createModel2(testModel);
            faculties.add(faculty);
            createModel3(testModel);
            faculties.add(faculty);
            break;
        case MODEL_3:
            createModel1(testModel);
            faculties.add(faculty);
            createModel6(testModel);
            faculties.add(faculty);
            break;
        case MODEL_4:
            createModel4(testModel);
            faculties.add(faculty);
            createModel5(testModel);
            faculties.add(faculty);
            break;
        case MODEL_5:
            createModel3(testModel);
            faculties.add(faculty);
            createModel6(testModel);
            faculties.add(faculty);
            break;
        case MODEL_EMPTY:
            createModel3(testModel);
            faculties.add(faculty);
            createModel5(testModel);
            faculties.add(faculty);
            break;
        }
        return faculties;
    }

    private static void createModel1(TestModel testModel) {
        faculty = new Faculty();
        faculty.setId(1);
        faculty.setTitle("faculty1");
        faculty.setDepartments(DepartmentModelRepository.getList(testModel));
    }

    private static void createModel2(TestModel testModel) {
        faculty = new Faculty();
        faculty.setId(2);
        faculty.setTitle("faculty2");
        faculty.setDepartments(DepartmentModelRepository.getList(testModel));
    }

    private static void createModel3(TestModel testModel) {
        faculty = new Faculty();
        faculty.setId(3);
        faculty.setTitle("faculty3");
        faculty.setDepartments(DepartmentModelRepository.getList(testModel));
    }

    private static void createModel4(TestModel testModel) {
        faculty = new Faculty();
        faculty.setId(4);
        faculty.setTitle("faculty4");
        faculty.setDepartments(DepartmentModelRepository.getList(testModel));
    }

    private static void createModel5(TestModel testModel) {
        faculty = new Faculty();
        faculty.setId(5);
        faculty.setTitle("faculty5");
        faculty.setDepartments(DepartmentModelRepository.getList(testModel));
    }

    private static void createModel6(TestModel testModel) {
        faculty = new Faculty();
        faculty.setId(6);
        faculty.setTitle("faculty6");
        faculty.setDepartments(DepartmentModelRepository.getList(testModel));
    }

}

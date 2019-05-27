package ua.com.foxminded.task.domain.repository;

import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.domain.Department;

public class DepartmentModelRepository {

    private static Department department;
    private static List<Department> departments;

    public static Department getModel(TestModel testModel) {
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
        return department;
    }

    public static List<Department> getList(TestModel testModel) {
        departments = new ArrayList<>();
        switch (testModel) {
        case MODEL_1:
            createModel1(testModel);
            departments.add(department);
            createModel2(testModel);
            departments.add(department);
            break;
        case MODEL_2:
            createModel2(testModel);
            departments.add(department);
            createModel3(testModel);
            departments.add(department);
            break;
        case MODEL_3:
            createModel1(testModel);
            departments.add(department);
            createModel6(testModel);
            departments.add(department);
            break;
        case MODEL_4:
            createModel4(testModel);
            departments.add(department);
            createModel5(testModel);
            departments.add(department);
            break;
        case MODEL_5:
            createModel3(testModel);
            departments.add(department);
            createModel6(testModel);
            departments.add(department);
            break;
        case MODEL_EMPTY:
            createModel3(testModel);
            departments.add(department);
            createModel5(testModel);
            departments.add(department);
            break;
        }
        return departments;
    }

    private static void createModel1(TestModel testModel) {
        department = new Department();
        department.setId(1);
        department.setTitle("department1");
        department.setDescription("bla bla bla 1");
        department.setGroups(GroupModelRepository.getList(testModel));
        department.setTeachers(TeacherModelRepository.getList(testModel));
    }

    private static void createModel2(TestModel testModel) {
        department = new Department();
        department.setId(2);
        department.setTitle("department2");
        department.setDescription("bla bla bla 2");
        department.setGroups(GroupModelRepository.getList(testModel));
        department.setTeachers(TeacherModelRepository.getList(testModel));
    }

    private static void createModel3(TestModel testModel) {
        department = new Department();
        department.setId(3);
        department.setTitle("department3");
        department.setDescription("bla bla bla 3");
        department.setGroups(GroupModelRepository.getList(testModel));
        department.setTeachers(TeacherModelRepository.getList(testModel));
    }

    private static void createModel4(TestModel testModel) {
        department = new Department();
        department.setId(4);
        department.setTitle("department4");
        department.setDescription("bla bla bla 4");
        department.setGroups(GroupModelRepository.getList(testModel));
        department.setTeachers(TeacherModelRepository.getList(testModel));
    }

    private static void createModel5(TestModel testModel) {
        department = new Department();
        department.setId(5);
        department.setTitle("department5");
        department.setDescription("bla bla bla 5");
        department.setGroups(GroupModelRepository.getList(testModel));
        department.setTeachers(TeacherModelRepository.getList(testModel));
    }

    private static void createModel6(TestModel testModel) {
        department = new Department();
        department.setId(6);
        department.setTitle("test");
    }

    public static Department getEmptyModel() {
        Department department = new Department();
        department.setId(6);
        department.setTitle("test");
        return department;
    }
}

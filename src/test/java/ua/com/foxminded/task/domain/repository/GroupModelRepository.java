package ua.com.foxminded.task.domain.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.domain.Group;

public class GroupModelRepository {

    private static Group group;
    private static List<Group> groups;

    public static Group getModel(TestModel testModel) {
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
        return group;
    }

    public static List<Group> getList(TestModel testModel) {
        groups = new ArrayList<>();
        switch (testModel) {
        case MODEL_1:
            createModel1(testModel);
            groups.add(group);
            createModel2(testModel);
            groups.add(group);
            break;
        case MODEL_2:
            createModel2(testModel);
            groups.add(group);
            createModel3(testModel);
            groups.add(group);
            break;
        case MODEL_3:
            createModel1(testModel);
            groups.add(group);
            createModel6(testModel);
            groups.add(group);
            break;
        case MODEL_4:
            createModel4(testModel);
            groups.add(group);
            createModel5(testModel);
            groups.add(group);
            break;
        case MODEL_5:
            createModel3(testModel);
            groups.add(group);
            createModel6(testModel);
            groups.add(group);
            break;
        case MODEL_6:
            createModel3(testModel);
            groups.add(group);
            createModel5(testModel);
            groups.add(group);
            break;
        }
        return groups;
    }

    private static void createModel1(TestModel testModel) {
        group = new Group();
        group.setId(1);
        group.setTitle("group1");
//        group.setDepartment(DepartmentModelRepository.getModel(testModel));
        group.setYearEntry(Date.valueOf("2018-01-01"));
        group.setStudents(StudentModelRepository.getList(testModel));
    }

    private static void createModel2(TestModel testModel) {
        group = new Group();
        group.setId(2);
        group.setTitle("group2");
//        group.setDepartment(DepartmentModelRepository.getModel(testModel));
        group.setYearEntry(Date.valueOf("2017-01-01"));
        group.setStudents(StudentModelRepository.getList(testModel));
    }

    private static void createModel3(TestModel testModel) {
        group = new Group();
        group.setId(3);
        group.setTitle("group3");
//        group.setDepartment(DepartmentModelRepository.getModel(testModel));
        group.setYearEntry(Date.valueOf("2016-01-01"));
        group.setStudents(StudentModelRepository.getList(testModel));
    }

    private static void createModel4(TestModel testModel) {
        group = new Group();
        group.setId(4);
        group.setTitle("group4");
//        group.setDepartment(DepartmentModelRepository.getModel(testModel));
        group.setYearEntry(Date.valueOf("2018-01-01"));
        group.setStudents(StudentModelRepository.getList(testModel));
    }

    private static void createModel5(TestModel testModel) {
        group = new Group();
        group.setId(5);
        group.setTitle("group5");
//        group.setDepartment(DepartmentModelRepository.getModel(testModel));
        group.setYearEntry(Date.valueOf("2017-01-01"));
        group.setStudents(StudentModelRepository.getList(testModel));
    }

    private static void createModel6(TestModel testModel) {
        group = new Group();
        group.setId(6);
        group.setTitle("group6");
//        group.setDepartment(DepartmentModelRepository.getModel(testModel));
        group.setYearEntry(Date.valueOf("2016-01-01"));
        group.setStudents(StudentModelRepository.getList(testModel));
    }

}

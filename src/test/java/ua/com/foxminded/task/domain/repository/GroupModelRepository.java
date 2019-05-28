package ua.com.foxminded.task.domain.repository;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.Group;

public class GroupModelRepository {

    public static List<Group> getModels() {
        return Arrays.asList(getModel1(), getModel2(), getModel3(), getModel4());
    }

    public static Group getEmptyModel() {
        Group group = new Group();
        group.setId(7);
        group.setTitle("group empty");
        group.setYearEntry(Date.valueOf("2016-01-01"));
        return group;
    }

    public static Group getModel1() {
        Group group = new Group();
        group.setId(4);
        group.setTitle("group6");
        group.setYearEntry(Date.valueOf("2016-01-01"));
        group.setStudents(StudentModelRepository.getModels());
        return group;
    }

    private static Group getModel2() {
        Group group = new Group();
        group.setId(1);
        group.setTitle("group1");
        group.setYearEntry(Date.valueOf("2018-01-01"));
        group.setStudents(StudentModelRepository.getModels());
        return group;
    }

    private static Group getModel3() {
        Group group = new Group();
        group.setId(2);
        group.setTitle("group2");
        group.setYearEntry(Date.valueOf("2017-01-01"));
        group.setStudents(StudentModelRepository.getModels());
        return group;
    }

    private static Group getModel4() {
        Group group = new Group();
        group.setId(3);
        group.setTitle("group3");
        group.setYearEntry(Date.valueOf("2016-01-01"));
        group.setStudents(StudentModelRepository.getModels());
        return group;
    }

}

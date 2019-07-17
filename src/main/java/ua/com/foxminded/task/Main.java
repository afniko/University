package ua.com.foxminded.task;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.dao.DepartmentDao;
import ua.com.foxminded.task.dao.impl.DepartmentDaoImpl;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Group;

public class Main {

    public static Department getModelD1() {
        Department department = new Department();
        department.setTitle("department3");
        department.setDescription("bla bla bla 1");
        department.setGroups(getModels1());
//        department.setTeachers(TeacherModelRepository.getModels1());
        return department;
    }

    public static List<Group> getModels1() {
        List<Group> groups = Arrays.asList(getModelG1(), getModelG2());
        return new ArrayList<>(groups);
    }

    public static Group getModelG1() {
        Group group = new Group();
        group.setTitle("group1");
        group.setYearEntry(Date.valueOf("2016-01-01"));
//        group.setStudents(StudentModelRepository.getModels1());
        return group;
    }

    public static Group getModelG2() {
        Group group = new Group();
        group.setTitle("group2");
        group.setYearEntry(Date.valueOf("2018-01-01"));
//        group.setStudents(StudentModelRepository.getModels2());
        return group;
    }

    public static void main(String[] args) {
        DepartmentDao departmentDao = new DepartmentDaoImpl();
//        departmentDao.create(getModelD1());
        System.out.println(departmentDao.findAll());
        System.out.println("test: " + departmentDao.findByTitle(getModelD1()));
    }

}

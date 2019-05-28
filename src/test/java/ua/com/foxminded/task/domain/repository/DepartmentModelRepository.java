package ua.com.foxminded.task.domain.repository;

import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.Department;

public class DepartmentModelRepository {

    public static Department getEmptyModel() {
        Department department = new Department();
        department.setId(6);
        department.setTitle("test");
        return department;
    }

    public static List<Department> getModels() {
        return Arrays.asList(getModel1(), getModel2(), getModel3());
    }

    public static Department getModel1() {
        Department department = new Department();
        department.setId(1);
        department.setTitle("department1");
        department.setDescription("bla bla bla 1");
        department.setGroups(GroupModelRepository.getModels());
        department.setTeachers(TeacherModelRepository.getModels());
        return department;
    }

    public static Department getModel2() {
        Department department = new Department();
        department.setId(2);
        department.setTitle("department2");
        department.setDescription("bla bla bla 2");
        department.setGroups(GroupModelRepository.getModels());
        department.setTeachers(TeacherModelRepository.getModels());
        return department;
    }

    public static Department getModel3() {
        Department department = new Department();
        department.setId(3);
        department.setTitle("department3");
        department.setDescription("bla bla bla 3");
        department.setGroups(GroupModelRepository.getModels());
        department.setTeachers(TeacherModelRepository.getModels());
        return department;
    }

}

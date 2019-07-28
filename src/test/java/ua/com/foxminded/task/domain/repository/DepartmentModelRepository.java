package ua.com.foxminded.task.domain.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.Department;

public class DepartmentModelRepository {

    public static Department getEmptyModel() {
        Department department = new Department();
        department.setTitle("test");
        return department;
    }

    public static List<Department> getModels() {
        List<Department> departments = Arrays.asList(getModel1(), getModel2(), getModel3());
        return new ArrayList<>(departments);
    }

    public static List<Department> getModels1() {
        List<Department> departments = Arrays.asList(getModel1(), getModel2());
        return new ArrayList<>(departments);
    }

    public static List<Department> getModels2() {
        List<Department> departments = Arrays.asList(getModel3(), getModel4());
        return new ArrayList<>(departments);
    }

    public static List<Department> getModels3() {
        List<Department> departments = Arrays.asList(getModel5());
        return new ArrayList<>(departments);
    }

    public static Department getModel1() {
        Department department = new Department();
        department.setTitle("department1");
        department.setDescription("bla bla bla 1");
        department.setGroups(GroupModelRepository.getModels1());
        department.setTeachers(TeacherModelRepository.getModels1());
        return department;
    }

    public static Department getModel2() {
        Department department = new Department();
        department.setTitle("department2");
        department.setDescription("bla bla bla 2");
        department.setGroups(GroupModelRepository.getModels2());
        department.setTeachers(TeacherModelRepository.getModels2());
        return department;
    }

    public static Department getModel3() {
        Department department = new Department();
        department.setTitle("department3");
        department.setDescription("bla bla bla 3");
        department.setGroups(GroupModelRepository.getModels3());
        department.setTeachers(TeacherModelRepository.getModels3());
        return department;
    }

    public static Department getModel4() {
        Department department = new Department();
        department.setTitle("department4");
        department.setDescription("bla bla bla 4");
        department.setGroups(GroupModelRepository.getModels4());
        department.setTeachers(TeacherModelRepository.getModels4());
        return department;
    }

    public static Department getModel5() {
        Department department = new Department();
        department.setTitle("department5");
        department.setDescription("bla bla bla 5");
        department.setGroups(GroupModelRepository.getModels5());
        department.setTeachers(TeacherModelRepository.getModels5());
        return department;
    }
}

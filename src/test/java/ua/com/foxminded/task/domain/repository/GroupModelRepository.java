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
            createModel1();
            break;
        case MODEL_2:
            createModel2();
            break;
        case MODEL_3:
            createModel3();
            break;
        case MODEL_4:
            createModel4();
            break;
        case MODEL_5:
            createModel5();
            break;
        case MODEL_6:
            createModel6();
            break;
        }
        return group;
    }

    public static List<Group> getList(TestModel testModel) {
        groups = new ArrayList<>();
        switch (testModel) {
        case MODEL_1:
            createModel1();
            groups.add(group);
            createModel2();
            groups.add(group);
            break;
        case MODEL_2:
            createModel2();
            groups.add(group);
            createModel3();
            groups.add(group);
            break;
        case MODEL_3:
            createModel1();
            groups.add(group);
            createModel6();
            groups.add(group);
            break;
        case MODEL_4:
            createModel4();
            groups.add(group);
            createModel5();
            groups.add(group);
            break;
        case MODEL_5:
            createModel3();
            groups.add(group);
            createModel6();
            groups.add(group);
            break;
        case MODEL_6:
            createModel3();
            groups.add(group);
            createModel5();
            groups.add(group);
            break;
        }
        return groups;
    }

    private static void createModel1() {
        group = new Group();
        group.setId(1);
        group.setTitle("group1");
        group.setDepartment(null);
        group.setYearEntry(Date.valueOf("2018-01-01"));
        group.setStudents(StudentModelRepository.getList(TestModel.MODEL_1));
//       TODO 
    }

    private static void createModel2() {

    }

    private static void createModel3() {

    }

    private static void createModel4() {

    }

    private static void createModel5() {

    }

    private static void createModel6() {

    }

}

package ua.com.foxminded.task;

import java.sql.Date;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;

public class Main {

    private static GroupDao groupDao;
    private static final Group GROUP1 = getModel1();
    private static final Group GROUP2 = getModel2();
    private static final Group GROUP3 = getModel3();

    public static void main(String[] args) {
        DaoFactory.getInstance().createTables();
        groupDao = new GroupDaoImpl();
        groupDao.create(GROUP1);
        groupDao.create(GROUP2);
        groupDao.create(GROUP3);
    }

    public static Group getModel1() {
        Group group = new Group();
        group.setTitle("group1");
        group.setYearEntry(Date.valueOf("2016-01-01"));
        return group;
    }

    public static Group getModel2() {
        Group group = new Group();
        group.setTitle("group2");
        group.setYearEntry(Date.valueOf("2018-01-01"));
        return group;
    }

    public static Group getModel3() {
        Group group = new Group();
        group.setTitle("group3");
        group.setYearEntry(Date.valueOf("2017-01-01"));
        return group;
    }

}

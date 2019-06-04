package ua.com.foxminded.task;

import java.sql.Connection;
import java.sql.SQLException;

import ua.com.foxminded.task.dao.DaoFactory;

public class Main {

    public static void main(String[] args) {
        DaoFactory daoFactory = DaoFactory.getInstance();
        try {
            Connection connection = daoFactory.getConnection();
            System.out.println(connection);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

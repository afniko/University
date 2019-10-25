package ua.com.foxminded.task.dao;

import java.sql.Connection;

public interface ConnectionFactory {

    public Connection getConnection();

}

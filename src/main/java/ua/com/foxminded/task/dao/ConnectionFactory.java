package ua.com.foxminded.task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public interface ConnectionFactory {

    public Connection getConnection();

    public void closeConnection(Connection connection);

    public void closeResultSet(ResultSet resultSet);

    public void closePreparedStatement(PreparedStatement preparedStatement);

    public void closeStatement(Statement statement);

}

package ua.com.foxminded.task.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.exception.NoConnectionDatabaseException;

public class DaoFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final static String NAME_CONTEXT = "java:/comp/env/jdbc/univer";
    private static DaoFactory instance;
    private static InitialContext initialContext;
    private static DataSource dataSource;

    private DaoFactory() {
    }

    private static void setInitialContext() {
        try {
            initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup(NAME_CONTEXT);
            LOGGER.debug("Get datasource: {}", dataSource);
        } catch (NamingException e) {
            LOGGER.error("DataSource connection {} not found : {}", dataSource, e);
            throw new NoConnectionDatabaseException("DaoFactory() was not get data source.", e);
        }
    }

    public synchronized static DaoFactory getInstance() {
        if (instance == null) {
            setInitialContext();
            instance = new DaoFactory();
        }
        return instance;
    }

    public Connection getConnection() {
        LOGGER.debug("getConnection() ");
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("getConnection() connection not found : {}", e);
            throw new NoConnectionDatabaseException("getConnection() was not get data source.", e);
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.debug("Close connection: {}", connection);
            } catch (SQLException e) {
                LOGGER.error("Connection {} cannot close {}", connection, e);
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
                LOGGER.debug("Close ResultSet: {}", resultSet);
            } catch (SQLException e) {
                LOGGER.error("ResultSet {} cannot close {}", resultSet, e);
            }
        }
    }

    public void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                LOGGER.debug("Close PreparedStatement: {}", preparedStatement);
            } catch (SQLException e) {
                LOGGER.error("PreparedStatement {} cannot close {}", preparedStatement, e);
            }
        }
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
                LOGGER.debug("Close Statement: {}", statement);
            } catch (SQLException e) {
                LOGGER.error("Statement {} cannot close {}", statement, e);
            }
        }
    }

}

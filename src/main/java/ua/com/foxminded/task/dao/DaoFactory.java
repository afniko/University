package ua.com.foxminded.task.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoFactory {
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static DaoFactory instance;
    private Properties properties;
    private InitialContext initialContext;
    private DataSource dataSource;

    private DaoFactory() {
        properties = getProperties(APPLICATION_PROPERTIES_FILE);

        try {
            initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/univer");
            LOGGER.debug("Get datasource: {}", dataSource);
        } catch (NamingException e) {
            LOGGER.error("DataSource connection {} not found : {}", dataSource, e);
        }

    }

    public Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        LOGGER.debug("Get connection: {}", connection);
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

    public synchronized static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    private Properties getProperties(String namePropertiesFile) {
        String rootResoursePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String propertiesFilePath = rootResoursePath + namePropertiesFile;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFilePath));
            LOGGER.debug("Properties load: {}", properties);
        } catch (FileNotFoundException e) {
            LOGGER.error("File properties {} not found. {}", propertiesFilePath, e);
        } catch (IOException e) {
            LOGGER.error("Input file properties {} had problem. {}", propertiesFilePath, e);
        }
        return properties;
    }
}

package ua.com.foxminded.task.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoFactory {
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static DaoFactory instance;
    private Properties properties;

    private DaoFactory() {
        properties = getProperties(APPLICATION_PROPERTIES_FILE);

        try {
            Class.forName(properties.getProperty("db.driver"));
            LOGGER.debug("Driver database {} registered", properties.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            LOGGER.error("Driver database {} not found : {}", properties.getProperty("db.driver"), e);
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
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

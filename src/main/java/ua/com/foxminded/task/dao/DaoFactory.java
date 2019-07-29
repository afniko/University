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

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoFactory {
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static DaoFactory instance;
    private Properties properties;
    private Flyway flyway;

    private DaoFactory() {
        properties = getProperties(APPLICATION_PROPERTIES_FILE);

        try {
            Class.forName(properties.getProperty("db.driver"));
            logger.debug("Driver database {} registered", properties.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            logger.error("Driver database {} not found : {}", properties.getProperty("db.driver"), e);
        }
        flywayInit();
        createTables();
    }

    private void flywayInit() {
        flyway = Flyway.configure().configuration(properties).load();
    }

    public void createTables() {
        flyway.migrate();
    }

    public void removeTables() {
        flyway.clean();
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
        logger.debug("Get connection: {}", connection);
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.debug("Close connection: {}", connection);
            } catch (SQLException e) {
                logger.error("Connection {} cannot close {}", connection, e);
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
                logger.debug("Close ResultSet: {}", resultSet);
            } catch (SQLException e) {
                logger.error("ResultSet {} cannot close {}", resultSet, e);
            }
        }
    }

    public void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                logger.debug("Close PreparedStatement: {}", preparedStatement);
            } catch (SQLException e) {
                logger.error("PreparedStatement {} cannot close {}", preparedStatement, e);
            }
        }
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
                logger.debug("Close Statement: {}", statement);
            } catch (SQLException e) {
                logger.error("Statement {} cannot close {}", statement, e);
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
            logger.debug("Properties load: {}", properties);
        } catch (FileNotFoundException e) {
            logger.error("File properties {} not found. {}", propertiesFilePath, e);
        } catch (IOException e) {
            logger.error("Input file properties {} had problem. {}", propertiesFilePath, e);
        }
        return properties;
    }
}

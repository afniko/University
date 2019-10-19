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

import ua.com.foxminded.task.dao.exception.NoDatabaseConnectionException;

public class ConnectionFactoryImpl implements ConnectionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    private static ConnectionFactoryImpl instance;
    private static Properties properties;
    private DataSource dataSource;

    private ConnectionFactoryImpl() {
        properties = getProperties(APPLICATION_PROPERTIES_FILE);
        retriveDataSourceFromInitialContext();
    }

    private void retriveDataSourceFromInitialContext() {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup(properties.getProperty("ds.name.context"));
            LOGGER.debug("Get datasource: {}", dataSource);
        } catch (NamingException e) {
            LOGGER.error("DataSource connection {} not found : {}", dataSource, e);
            throw new NoDatabaseConnectionException("DaoFactory() was not get data source.", e);
        }
    }

    public synchronized static ConnectionFactoryImpl getInstance() {
        if (instance == null) {
            instance = new ConnectionFactoryImpl();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        LOGGER.debug("getConnection() ");
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("getConnection() connection not found : {}", e);
            throw new NoDatabaseConnectionException("getConnection() was not get data source.", e);
        }
        return connection;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

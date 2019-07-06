package ua.com.foxminded.task.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DaoFactory {
    private static final String CONFIG_PROPERTIES_FILE = "config.properties";
    private static DaoFactory instance;
    Properties properties = getProperties(CONFIG_PROPERTIES_FILE);

    private DaoFactory() {
        registrationJdbcDriver();
        createTables();
    }

    private void registrationJdbcDriver() {
        try {
            Class.forName(properties.getProperty("db.jdbc_driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("db.url"), properties);
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public void createTables() {
        executeOueryFromFile("create_tables.sql");
    }

    public void removeTables() {
        executeOueryFromFile("remove_tables.sql");
    }

    private void executeOueryFromFile(String fileName) {
        String sqlQuery = getSqlQueryFromFile(fileName);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    private String getSqlQueryFromFile(String fileName) {
        String rootResoursePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String sqlFilePath = rootResoursePath + fileName;
        String sqlQuery = "";
        try {
            sqlQuery = new String(Files.readAllBytes(Paths.get(sqlFilePath.substring(0))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlQuery;
    }

    private Properties getProperties(String namePropertiesFile) {
        String rootResoursePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String propertiesFilePath = rootResoursePath + namePropertiesFile;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFilePath));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return properties;
    }

}

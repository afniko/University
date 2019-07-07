package ua.com.foxminded.task.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.flywaydb.core.Flyway;

public class DaoFactory {
    private static final String CONFIG_FLYWAY_PROPERTIES_FILE = "flyway.properties";
    private static DaoFactory instance;
    private Properties propertiesFlyWay;
    private Flyway flyway;

    private DaoFactory() {
        propertiesFlyWay = getProperties(CONFIG_FLYWAY_PROPERTIES_FILE);
        flyway = Flyway.configure().configuration(propertiesFlyWay).load();
        createTables();
    }

    public void createTables() {
        flyway.migrate();
    }

    public void removeTables() {
        flyway.clean();
//        executeOueryFromFile("remove_tables.sql");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(propertiesFlyWay.getProperty("flyway.url"), propertiesFlyWay.getProperty("flyway.user"), propertiesFlyWay.getProperty("flyway.password"));
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
//
//    private void executeOueryFromFile(String fileName) {
//        String sqlQuery = getSqlQueryFromFile(fileName);
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(sqlQuery);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closePreparedStatement(preparedStatement);
//            closeConnection(connection);
//        }
//    }

//    private String getSqlQueryFromFile(String fileName) {
//        String rootResoursePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        String sqlFilePath = rootResoursePath + fileName;
//        String sqlQuery = "";
//        try {
//            sqlQuery = new String(Files.readAllBytes(Paths.get(sqlFilePath.substring(0))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sqlQuery;
//    }
}

package ua.com.foxminded.task.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceCreater {

    private static DataSourceCreater instance;
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static Properties properties;
    private InitialContext initialContext = null;
    private PGSimpleDataSource dataSource;

    private DataSourceCreater() {
    };

    public void setInitialContext() {
        properties = getProperties(APPLICATION_PROPERTIES_FILE);
        dataSource = getDataSource();

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

        bindingInitialContext(dataSource);
    }

    public void closeInitialContext() {
        if (initialContext != null) {
            try {
                initialContext.unbind("java:/comp/env/jdbc/univer");
                initialContext.destroySubcontext("java:/comp/env/jdbc");
                initialContext.destroySubcontext("java:/comp/env");
                initialContext.destroySubcontext("java:/comp");
                initialContext.destroySubcontext("java:");
                initialContext.close();
            } catch (NamingException e) {
                LOGGER.error("Initial Context {} was not closed. {}", initialContext, e);
            }
        }
    }

    private void bindingInitialContext(DataSource dataSource) {
        try {
            initialContext = new InitialContext();
            initialContext.createSubcontext("java:");
            initialContext.createSubcontext("java:/comp");
            initialContext.createSubcontext("java:/comp/env");
            initialContext.createSubcontext("java:/comp/env/jdbc");
            initialContext.bind("java:/comp/env/jdbc/univer", dataSource);
        } catch (NamingException e) {
            LOGGER.error("Initial Context {} was not bound. {}", initialContext, e);
        }
    }

    public synchronized static DataSourceCreater getInstance() {
        if (instance == null) {
            instance = new DataSourceCreater();
        }
        return instance;
    }

    private PGSimpleDataSource getDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(properties.getProperty("db.servername"));
        dataSource.setDatabaseName(properties.getProperty("db.databasename"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));
        return dataSource;
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

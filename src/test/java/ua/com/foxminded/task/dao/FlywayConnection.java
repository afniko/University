package ua.com.foxminded.task.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlywayConnection {
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private Properties properties;
    private Flyway flyway;

    public void createTables() {
        LOGGER.info("createTables() [flyway:{}]", flyway);
        flywayInit();
        flyway.migrate();
    }

    public void removeTables() {
        flyway.clean();
    }

    private void flywayInit() {
        LOGGER.info("flywayInit() [flyway:{}]", flyway);
        properties = getProperties(APPLICATION_PROPERTIES_FILE);
        flyway = Flyway.configure().configuration(properties).load();
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

package ua.com.foxminded.task.dao.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.com.foxminded.task.config.ConfigurationConnection;
import ua.com.foxminded.task.dao.ConnectionFactory;
import ua.com.foxminded.task.dao.exception.NoDatabaseConnectionException;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static ConnectionFactoryImpl instance;
    private DataSource dataSource;

    private ConnectionFactoryImpl() {
        retriveDataSourceFromInitialContext();
    }

    private void retriveDataSourceFromInitialContext() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigurationConnection.class);
        dataSource = ctx.getBean(DataSource.class);
        LOGGER.debug("Get datasource: {}", dataSource);
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

}

package ua.com.foxminded.task.dao;

import java.sql.Connection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ua.com.foxminded.task.dao.impl.ConnectionFactoryImpl;

@Configuration
@ComponentScan
public class ConfigurationConnection {

    @Bean
    @Scope("singleton")
    public ConnectionFactory getConnectionInstance() {
        return ConnectionFactoryImpl.getInstance();
    }
    
    @Bean
    @Scope("prototype")
    public Connection getConnection() {
        return getConnectionInstance().getConnection();
    }
}

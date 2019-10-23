package ua.com.foxminded.task.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ua.com.foxminded.task")
public class ConfigurationConnection {

    @Bean
    public DataSource getDataSource() throws NamingException {
        InitialContext initialContext = new InitialContext();
        return (DataSource) initialContext.lookup("java:/comp/env/jdbc/univer");
    }

}

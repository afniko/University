package ua.com.foxminded.task.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ConfigurationConnection {
    
    @Bean
    public ConnectionFactory getConnectionInstance() {
        return ConnectionFactoryImpl.getInstance();
    }
}

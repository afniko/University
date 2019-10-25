package ua.com.foxminded.task.config;

import java.lang.invoke.MethodHandles;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class ConfigurationConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    @Resource
    private Environment env;

    @Bean
    public DataSource getDataSource() throws NamingException {
        LOGGER.info("ConfigurationConnection getDataSource()");
        return (DataSource) new JndiTemplate().lookup(env.getProperty("ds.name.context"));
    }

}

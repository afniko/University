package ua.com.foxminded.task.config;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = { "ua.com.foxminded.task" })
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class TestConfig {

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() throws NamingException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("ds.db.driver"));
        dataSource.setUrl(env.getProperty("ds.db.url"));
        dataSource.setUsername(env.getProperty("ds.db.user"));
        dataSource.setPassword(env.getProperty("ds.db.password"));
        return dataSource;
    }

}

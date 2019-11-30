package ua.com.foxminded.task.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "ua.com.foxminded.task" })
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class TestConfig {

    @Autowired
    private Logger logger;

    @Resource
    private Environment env;

//    @Bean
//    public DataSource dataSource() throws NamingException {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("db.driver"));
//        dataSource.setUrl(env.getProperty("db.url"));
//        dataSource.setUsername(env.getProperty("db.user"));
//        dataSource.setPassword(env.getProperty("db.password"));
//        return dataSource;
//    }

//    @Bean
//    public HikariDataSource dataSource(DataSourceProperties properties) {
//        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }

//    @Bean
//    @Scope("prototype")
//    public Logger produceLogger(InjectionPoint injectionPoint) {
//        Class<?> classOnWired = injectionPoint.getMember().getDeclaringClass();
//        return LoggerFactory.getLogger(classOnWired);
//    }

//    @Bean(initMethod = "migrate")
//    public Flyway flyway() {
//        logger.info("flyway()");
//        Flyway flyway = Flyway.configure().configuration(getFlywayProperties()).load();
//        return flyway;
//    }
//
//    private Properties getFlywayProperties() {
//        Properties properties = new Properties();
//        properties.put("flyway.driver", env.getRequiredProperty("spring.datasource.driver-class-name"));
//        properties.put("flyway.url", env.getRequiredProperty("spring.datasource.url"));
//        properties.put("flyway.user", env.getRequiredProperty("spring.datasource.username"));
//        properties.put("flyway.password", env.getRequiredProperty("spring.datasource.password"));
//        properties.put("flyway.locations", env.getRequiredProperty("flyway.locations"));
//        properties.put("flyway.sqlMigrationPrefix", env.getRequiredProperty("flyway.sqlMigrationPrefix"));
//        properties.put("flyway.sqlMigrationSeparator", env.getRequiredProperty("flyway.sqlMigrationSeparator"));
//        properties.put("flyway.sqlMigrationSuffix", env.getRequiredProperty("flyway.sqlMigrationSuffix"));
//        properties.put("flyway.validateOnMigrate", env.getRequiredProperty("flyway.validateOnMigrate"));
//
//        return properties;
//    }
}

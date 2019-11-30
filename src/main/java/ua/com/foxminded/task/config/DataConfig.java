package ua.com.foxminded.task.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan("ua.com.foxminded.task")
@PropertySource("classpath:application.properties")
public class DataConfig {

    @Autowired
    private Logger logger;

    @Resource
    private Environment env;

//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }

//    @Bean
//    @Primary
////    @ConfigurationProperties("spring.datasource")
//    public DataSourceProperties dataSourceProperties() {
//        return new DataSourceProperties();
//    }

    @Bean
    public HikariDataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

//    @Bean
//    public DataSource dataSource() throws NamingException {
//        logger.info("ConfigurationConnection getDataSource()");
//        return (DataSource) new JndiTemplate().lookup(env.getRequiredProperty("ds.name.context"));
//    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
//        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource());
//        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
//        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty("db.entitymanager.packages.to.scan"));
//        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
//
//        return entityManagerFactoryBean;
//    }

//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter() {
//        return new HibernateJpaVendorAdapter();
//    }

//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        logger.info("flyway()");
        Flyway flyway = Flyway.configure().configuration(getFlywayProperties()).load();
        return flyway;
    }


    private Properties getFlywayProperties() {
        Properties properties = new Properties();
        properties.put("flyway.driver", env.getRequiredProperty("spring.datasource.driver-class-name"));
        properties.put("flyway.url", env.getRequiredProperty("spring.datasource.url"));
        properties.put("flyway.user", env.getRequiredProperty("spring.datasource.username"));
        properties.put("flyway.password", env.getRequiredProperty("spring.datasource.password"));
        properties.put("flyway.locations", env.getRequiredProperty("flyway.locations"));
        properties.put("flyway.sqlMigrationPrefix", env.getRequiredProperty("flyway.sqlMigrationPrefix"));
        properties.put("flyway.sqlMigrationSeparator", env.getRequiredProperty("flyway.sqlMigrationSeparator"));
        properties.put("flyway.sqlMigrationSuffix", env.getRequiredProperty("flyway.sqlMigrationSuffix"));
        properties.put("flyway.validateOnMigrate", env.getRequiredProperty("flyway.validateOnMigrate"));

        return properties;
    }
}

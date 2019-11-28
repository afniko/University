package ua.com.foxminded.task.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("ua.com.foxminded.task")
@PropertySource("classpath:application.properties")
public class DataConfig {
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    @Autowired
    private Logger logger;

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() throws NamingException {
        logger.info("ConfigurationConnection getDataSource()");
        return (DataSource) new JndiTemplate().lookup(env.getRequiredProperty("ds.name.context"));
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty("db.entitymanager.packages.to.scan"));
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean(initMethod = "migrate")
    Flyway flyway() throws NamingException {
        logger.info("flyway()");
        Properties properties = getProperties(APPLICATION_PROPERTIES_FILE);
        Flyway flyway = Flyway.configure().configuration(properties).load();
//    Flyway flyway = Flyway.configure().dataSource(dataSource()).load();
//        Flyway flyway =    new Flyway();
//    flyway.setBaselineOnMigrate(true);
//    flyway.setLocations("filesystem:/path/to/migrations/");
//    flyway.setDataSource(dataSource());
        return flyway;
    }

    private Properties getProperties(String namePropertiesFile) {
        String rootResoursePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String propertiesFilePath = rootResoursePath + namePropertiesFile;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFilePath));
            logger.debug("Properties load: {}", properties);
        } catch (FileNotFoundException e) {
            logger.error("File properties {} not found. {}", propertiesFilePath, e);
        } catch (IOException e) {
            logger.error("Input file properties {} had problem. {}", propertiesFilePath, e);
        }
        return properties;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("db.hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("db.hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("db.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.connection.CharSet", env.getRequiredProperty("db.hibernate.connection.CharSet"));
        properties.put("hibernate.connection.characterEncoding", env.getRequiredProperty("db.hibernate.connection.characterEncoding"));
        properties.put("hibernate.connection.useUnicode", env.getRequiredProperty("db.hibernate.connection.useUnicode"));
        properties.put("hibernate.cache.use_second_level_cache", env.getRequiredProperty("db.hibernate.cache.use_second_level_cache"));
        properties.put("hibernate.cache.use_query_cache", env.getRequiredProperty("db.hibernate.cache.use_query_cache"));

        return properties;
    }
}

package ua.com.foxminded.task.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "ua.com.foxminded.task" })
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class TestConfig {

    @Autowired
    private Logger logger;

//    @Resource
//    private Environment env;
//
//    @Bean
//    @Primary
//    public DataSourceProperties dataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource) throws NamingException {
//        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
//        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty("db.entitymanager.packages.to.scan"));
//        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
//
//        return entityManagerFactoryBean;
//    }
//
//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter() {
//        return new HibernateJpaVendorAdapter();
//    }
//
//    private Properties getHibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", env.getRequiredProperty("db.hibernate.dialect"));
//        properties.put("hibernate.show_sql", env.getRequiredProperty("db.hibernate.show_sql"));
//        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("db.hibernate.hbm2ddl.auto"));
//        properties.put("hibernate.connection.CharSet", env.getRequiredProperty("db.hibernate.connection.CharSet"));
//        properties.put("hibernate.connection.characterEncoding", env.getRequiredProperty("db.hibernate.connection.characterEncoding"));
//        properties.put("hibernate.connection.useUnicode", env.getRequiredProperty("db.hibernate.connection.useUnicode"));
//        properties.put("hibernate.cache.use_second_level_cache", env.getRequiredProperty("db.hibernate.cache.use_second_level_cache"));
//        properties.put("hibernate.cache.use_query_cache", env.getRequiredProperty("db.hibernate.cache.use_query_cache"));
//        return properties;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }
//
//    @Bean
//    public DataSource dataSource() throws NamingException {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("db.driver"));
//        dataSource.setUrl(env.getProperty("db.url"));
//        dataSource.setUsername(env.getProperty("db.user"));
//        dataSource.setPassword(env.getProperty("db.password"));
//        return dataSource;
//    }
//
//    @Bean
//    public HikariDataSource dataSource(DataSourceProperties properties) {
//        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//
//    @Bean
//    @Scope("prototype")
//    public Logger produceLogger(InjectionPoint injectionPoint) {
//        Class<?> classOnWired = injectionPoint.getMember().getDeclaringClass();
//        return LoggerFactory.getLogger(classOnWired);
//    }
//
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

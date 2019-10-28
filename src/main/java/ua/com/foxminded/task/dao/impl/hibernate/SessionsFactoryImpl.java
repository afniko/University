package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.com.foxminded.task.config.ConfigurationConnection;
import ua.com.foxminded.task.dao.SessionsFactory;
import ua.com.foxminded.task.domain.Group;

public class SessionsFactoryImpl implements SessionsFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static SessionsFactoryImpl instance;
    private DataSource dataSource;

    private SessionsFactoryImpl() {
        retriveDataSourceFromInitialContext();
    }

    private void retriveDataSourceFromInitialContext() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigurationConnection.class);
        dataSource = ctx.getBean(DataSource.class);
        LOGGER.debug("retriveDataSourceFromInitialContext() Get datasource: {}", dataSource);
    }

    public synchronized static SessionsFactoryImpl getInstance() {
        if (instance == null) {
            instance = new SessionsFactoryImpl();
        }
        return instance;
    }

    @Override
    public SessionFactory getSessionFactory() {

        Map<String, Object> settings = new HashMap<>();
        settings.put(Environment.DATASOURCE, dataSource);
        settings.put(Environment.HBM2DDL_AUTO, "update");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL95Dialect");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();
        MetadataSources sources = new MetadataSources(registry)
                .addAnnotatedClass(Group.class);
        Metadata metadata = sources.getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        LOGGER.debug("getSessionFactory() Get SessionFactory: {}", sessionFactory);
        return sessionFactory;
    }

}

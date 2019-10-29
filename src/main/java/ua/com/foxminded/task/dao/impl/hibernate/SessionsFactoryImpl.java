package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.SessionsFactory;

public class SessionsFactoryImpl implements SessionsFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static SessionsFactoryImpl instance;

    public synchronized static SessionsFactoryImpl getInstance() {
        if (instance == null) {
            instance = new SessionsFactoryImpl();
        }
        return instance;
    }

    @Override
    public SessionFactory getSessionFactory() {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        MetadataSources sources = new MetadataSources(registry);
        Metadata metadata = sources.getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        LOGGER.debug("getSessionFactory() Get SessionFactory: {}", sessionFactory);
        return sessionFactory;
    }

}

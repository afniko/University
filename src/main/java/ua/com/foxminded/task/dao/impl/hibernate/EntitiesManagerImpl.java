package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.EntitiesManager;

public class EntitiesManagerImpl implements EntitiesManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String PERSISTENCE_UNIT_NAME = "persistenceUniversity";
    private static EntitiesManagerImpl instance;
    private EntityManagerFactory entityManagerFactory;

    public synchronized static EntitiesManagerImpl getInstance() {
        if (instance == null) {
            instance = new EntitiesManagerImpl();
        }
        return instance;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        LOGGER.debug("getEntityManagerFactory() Get EntityManagerFactory: {}", entityManagerFactory);
        return entityManagerFactory;
    }

}

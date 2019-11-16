package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.task.dao.EntitiesManagerFactory;

@Repository
public class EntitiesManagerFactoryImpl implements EntitiesManagerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String PERSISTENCE_UNIT_NAME = "persistenceUniversity";
    private EntityManagerFactory entityManagerFactory;

    public EntitiesManagerFactoryImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Override
    public EntityManager getEntityManager() {
        LOGGER.debug("getEntityManager()");
        return entityManagerFactory.createEntityManager();
    }

}

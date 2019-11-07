package ua.com.foxminded.task.dao;

import javax.persistence.EntityManager;

public interface EntitiesManagerFactory {

    public EntityManager getEntityManager();
}

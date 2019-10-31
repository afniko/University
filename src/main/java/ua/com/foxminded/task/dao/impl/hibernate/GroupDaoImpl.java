package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.EntitiesManager;
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.domain.Group;

public class GroupDaoImpl implements GroupDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private EntityManagerFactory entityManagerFactory;

    public GroupDaoImpl() {
        EntitiesManager entitiesManager = EntitiesManagerImpl.getInstance();
        entityManagerFactory = entitiesManager.getEntityManagerFactory();
    }

    @Override
    public Group create(Group group) {
        LOGGER.debug("create() [group:{}]", group);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(group);
        entityManager.getTransaction().commit();
        entityManager.clear();
        return group;
    }

    @Override
    public Group findById(int id) {
        LOGGER.debug("findById() [id:{}]", id);
        Group group = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        group = entityManager.find(Group.class, id);
        entityManager.getTransaction().commit();
        entityManager.clear();
        return group;
    }

    @Override
    public List<Group> findAll() {
        LOGGER.debug("findAll()");
        List<Group> groups = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        groups = entityManager.createQuery("from Group", Group.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return groups;
    }

    @Override
    public Group update(Group group) {
        LOGGER.debug("update() [group:{}]", group);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(group);
        entityManager.getTransaction().commit();
        entityManager.clear();
        return group;
    }

    @Override
    public List<Group> findByDepartmentId(int id) {
        LOGGER.debug("findByDepartmentId() [id:{}]", id);
        return null;
    }

}

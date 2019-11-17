package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.task.dao.EntitiesManagerFactory;
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.exception.EntityAlreadyExistsException;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.domain.Group;

@Repository
public class GroupDaoImpl implements GroupDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private EntityManager entityManager;

    @Autowired
    public GroupDaoImpl(EntitiesManagerFactory entitiesManagerFactory) {
        entityManager = entitiesManagerFactory.getEntityManager();
    }

    @Override
    public Group create(Group group) {
        LOGGER.debug("create() [group:{}]", group);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(group);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw new EntityAlreadyExistsException("create() group: " + group, e);
        }
        entityManager.clear();
        return group;
    }

    @Override
    public Group findById(int id) {
        LOGGER.debug("findById() [id:{}]", id);
        String exceptionMessage = "findById() Group by id#" + id + " not found";
        Group group = null;
        try {
            entityManager.getTransaction().begin();
            group = entityManager.find(Group.class, id);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw new NoEntityFoundException(exceptionMessage, e);
        }
        entityManager.clear();
        if (Objects.isNull(group)) {
            LOGGER.warn("findById() Group with id#{} not found", id);
            throw new NoEntityFoundException(exceptionMessage);
        }
        return group;
    }

    @Override
    public List<Group> findAll() {
        LOGGER.debug("findAll()");
        List<Group> groups = null;
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> groupCriteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> groupRoot = groupCriteriaQuery.from(Group.class);
        groupCriteriaQuery.select(groupRoot);
        groups = entityManager.createQuery(groupCriteriaQuery).getResultList();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return groups;
    }

    @Override
    public Group update(Group group) {
        LOGGER.debug("update() [group:{}]", group);
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(group);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw new EntityAlreadyExistsException("update() group: " + group, e);
        }
        entityManager.clear();
        return group;
    }

    @Override
    public List<Group> findByDepartmentId(int id) {
        LOGGER.debug("findByDepartmentId() [id:{}]", id);
        // TODO
        return null;
    }

}

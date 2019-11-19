package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.exception.EntityAlreadyExistsException;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.domain.Group;

@Repository
public class GroupDaoImpl implements GroupDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Group create(Group group) {
        LOGGER.debug("create() [group:{}]", group);
        try {
            entityManager.persist(group);
        } catch (PersistenceException e) {
            throw new EntityAlreadyExistsException("create() group: " + group, e);
        }
        return group;
    }

    @Transactional
    @Override
    public Group findById(int id) {
        LOGGER.debug("findById() [id:{}]", id);
        String exceptionMessage = "findById() Group by id#" + id + " not found";
        Group group = null;
        try {
            group = entityManager.find(Group.class, id);
        } catch (PersistenceException e) {
            throw new NoEntityFoundException(exceptionMessage, e);
        }
        if (Objects.isNull(group)) {
            LOGGER.warn("findById() Group with id#{} not found", id);
            throw new NoEntityFoundException(exceptionMessage);
        }
        return group;
    }

    @Transactional
    @Override
    public List<Group> findAll() {
        LOGGER.debug("findAll()");
        List<Group> groups = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> groupCriteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> groupRoot = groupCriteriaQuery.from(Group.class);
        groupCriteriaQuery.select(groupRoot);
        groups = entityManager.createQuery(groupCriteriaQuery).getResultList();
        return groups;
    }

    @Transactional
    @Override
    public Group update(Group group) {
        LOGGER.debug("update() [group:{}]", group);
        try {
            entityManager.merge(group);
        } catch (JDBCException e) {
            throw new EntityAlreadyExistsException("update() group: " + group, e);
        }
        return group;
    }

    @Override
    public List<Group> findByDepartmentId(int id) {
        LOGGER.debug("findByDepartmentId() [id:{}]", id);
        // TODO
        return null;
    }

}

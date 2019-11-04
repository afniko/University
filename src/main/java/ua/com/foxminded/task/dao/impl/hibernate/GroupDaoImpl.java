package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.domain.Group;

public class GroupDaoImpl implements GroupDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private EntityManager entityManager;

    public GroupDaoImpl() {
        entityManager = EntitiesManagerFactoryImpl.getInstance().getEntityManager();
    }

    @Override
    public Group create(Group group) {
        LOGGER.debug("create() [group:{}]", group);
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
        entityManager.getTransaction().begin();
        group = entityManager.find(Group.class, id);
        entityManager.getTransaction().commit();
        entityManager.clear();
        if (Objects.isNull(group)) {
            LOGGER.warn("findById() Group with id#{} not found", id);
            throw new NoEntityFoundException("findById() Group by id#" + id + " not found");
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
        entityManager.getTransaction().begin();
        entityManager.merge(group);
        entityManager.getTransaction().commit();
        entityManager.clear();
        return group;
    }

    @Override
    public List<Group> findByDepartmentId(int id) {
        LOGGER.debug("findByDepartmentId() [id:{}]", id);
        List<Group> groups = null;
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> groupCriteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> groupRoot = groupCriteriaQuery.from(Group.class);
        groupCriteriaQuery.select(groupRoot).where(criteriaBuilder.equal(groupRoot.get("department").get("id"), id));
        groups = entityManager.createQuery(groupCriteriaQuery).getResultList();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return groups;
    }

}

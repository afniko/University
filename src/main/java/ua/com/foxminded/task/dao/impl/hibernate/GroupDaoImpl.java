package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.SessionsFactory;
import ua.com.foxminded.task.domain.Group;

public class GroupDaoImpl implements GroupDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private SessionFactory sessionFactory;

    public GroupDaoImpl() {
        SessionsFactory sessionsFactory = SessionsFactoryImpl.getInstance();
        sessionFactory = sessionsFactory.getSessionFactory();
    }

    @Override
    public Group create(Group group) {
        LOGGER.debug("create() [group:{}]", group);
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.save(group);
            session.getTransaction().commit();
        }
        return group;
    }

    @Override
    public Group findById(int id) {
        LOGGER.debug("findById() [id:{}]", id);
        Group group = null;
        try (Session session = sessionFactory.openSession()) {
            group = session.createQuery("from Group as group where group.id = " + id, Group.class).uniqueResult();
        }
        return group;
    }

    @Override
    public List<Group> findAll() {
        LOGGER.debug("findAll()");
        List<Group> groups = null;
        try (Session session = sessionFactory.openSession()) {
            groups = session.createQuery("from Group", Group.class).list();
        }
        return groups;
    }

    @Override
    public Group update(Group group) {
        LOGGER.debug("update() [group:{}]", group);
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.update(group);
            session.getTransaction().commit();
        }
        return group;
    }

    @Override
    public List<Group> findByDepartmentId(int id) {
        LOGGER.debug("findByDepartmentId() [id:{}]", id);
        return null;
    }

}

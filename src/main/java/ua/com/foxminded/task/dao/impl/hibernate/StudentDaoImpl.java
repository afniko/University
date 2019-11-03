package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.domain.Student;

public class StudentDaoImpl implements StudentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private EntityManager entityManager;

    public StudentDaoImpl() {
        entityManager = EntitiesManagerFactoryImpl.getInstance().getEntityManager();
    }

    @Override
    public Student create(Student student) {
        LOGGER.debug("create() [student:{}]", student);
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        entityManager.clear();
        return student;
    }

    @Override
    public Student findById(int id) {
        LOGGER.debug("findById() [student id:{}]", id);
        Student student = null;
        entityManager.getTransaction().begin();
        student = entityManager.find(Student.class, id);
        entityManager.getTransaction().commit();
        entityManager.clear();
        if (Objects.isNull(student)) {
            LOGGER.warn("findById() Student with id#{} not found", id);
            throw new NoEntityFoundException("findById() Student by id#" + id + " not found");
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        LOGGER.debug("findAll()");
        List<Student> students = null;
        entityManager.getTransaction().begin();
        students = entityManager.createQuery("from Student", Student.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.clear();
        return students;
    }

    @Override
    public List<Student> findByGroupId(int id) {
        LOGGER.debug("findByGroupId() [id:{}]", id);
        //TODO method not use
        return null;
    }

    @Override
    public Student update(Student student) {
        LOGGER.debug("update() [student:{}]", student);
        entityManager.getTransaction().begin();
        entityManager.merge(student);
        entityManager.getTransaction().commit();
        entityManager.clear();
        return student;
    }
}

package ua.com.foxminded.task.dao.impl.hibernate;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.exception.EntityAlreadyExistsException;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Group_;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.Student_;

@Repository
public class StudentDaoImpl implements StudentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Student create(Student student) {
        LOGGER.debug("create() [student:{}]", student);
        try {
            entityManager.persist(student);
        } catch (PersistenceException e) {
            throw new EntityAlreadyExistsException("create() student: " + student, e);
        }
        return student;
    }

    @Transactional
    @Override
    public Student findById(int id) {
        LOGGER.debug("findById() [student id:{}]", id);
        String exceptionMessage = "findById() Student by id#" + id + " not found";
        Student student = null;
        try {
            student = entityManager.find(Student.class, id);
        } catch (PersistenceException e) {
            throw new NoEntityFoundException(exceptionMessage, e);
        }
        if (Objects.isNull(student)) {
            LOGGER.warn("findById() Student with id#{} not found", id);
            throw new NoEntityFoundException(exceptionMessage);
        }
        return student;
    }

    @Transactional
    @Override
    public List<Student> findAll() {
        LOGGER.debug("findAll()");
        List<Student> students = null;
        students = entityManager.createQuery("from Student", Student.class).getResultList();
        return students;
    }

    @Transactional
    @Override
    public List<Student> findByGroupId(int id) {
        LOGGER.debug("findByGroupId() [id:{}]", id);
        List<Student> students = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> studentCriteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = studentCriteriaQuery.from(Student.class);
        Join<Student, Group> groupJoin = studentRoot.join(Student_.group, JoinType.LEFT);
        studentCriteriaQuery.select(studentRoot).where(criteriaBuilder.equal(groupJoin.get(Group_.id), id));
        students = entityManager.createQuery(studentCriteriaQuery).getResultList();
        return students;
    }

    @Transactional
    @Override
    public Student update(Student student) {
        LOGGER.debug("update() [student:{}]", student);
        try {
            entityManager.merge(student);
        } catch (PersistenceException e) {
            throw new EntityAlreadyExistsException("update() student: " + student, e);
        }
        return student;
    }
}

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

//    @Autowired
//    public StudentDaoImpl(EntitiesManagerFactory entitiesManagerFactory) {
//        entityManager = entitiesManagerFactory.getEntityManager();
//    }

    @Transactional
    @Override
    public Student create(Student student) {
        LOGGER.debug("create() [student:{}]", student);
        try {
//            entityManager.getTransaction().begin();
            entityManager.persist(student);
//            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
//            entityManager.getTransaction().rollback();
            throw new EntityAlreadyExistsException("create() student: " + student, e);
        }
//        entityManager.clear();
        return student;
    }

    @Transactional
    @Override
    public Student findById(int id) {
        LOGGER.debug("findById() [student id:{}]", id);
        String exceptionMessage = "findById() Student by id#" + id + " not found";
        Student student = null;
        try {
//            entityManager.getTransaction().begin();
            student = entityManager.find(Student.class, id);
//            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
//            entityManager.getTransaction().rollback();
            throw new NoEntityFoundException(exceptionMessage, e);
        }
//        entityManager.clear();
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
//        entityManager.getTransaction().begin();
        students = entityManager.createQuery("from Student", Student.class).getResultList();
//        entityManager.getTransaction().commit();
//        entityManager.clear();
        return students;
    }

    @Transactional
    @Override
    public List<Student> findByGroupId(int id) {
        LOGGER.debug("findByGroupId() [id:{}]", id);
        List<Student> students = null;
//        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> studentCriteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = studentCriteriaQuery.from(Student.class);
        Join<Student, Group> groupJoin = studentRoot.join(Student_.group, JoinType.LEFT);
        studentCriteriaQuery.select(studentRoot).where(criteriaBuilder.equal(groupJoin.get(Group_.id), id));
        students = entityManager.createQuery(studentCriteriaQuery).getResultList();
//        entityManager.getTransaction().commit();
//        entityManager.clear();
        return students;
    }

    @Transactional
    @Override
    public Student update(Student student) {
        LOGGER.debug("update() [student:{}]", student);
        try {
//            entityManager.getTransaction().begin();
            entityManager.merge(student);
//            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
//            entityManager.getTransaction().rollback();
            throw new EntityAlreadyExistsException("update() student: " + student, e);
        }
//        entityManager.clear();
        return student;
    }
}

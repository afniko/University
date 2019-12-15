package ua.com.foxminded.task.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.StudentModelRepository;

@SpringBootTest
public class ITStudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private Flyway flyway;

    private static final Student STUDENT1 = StudentModelRepository.getModel1();
    private static final Student STUDENT2 = StudentModelRepository.getModel2();
    private static final Student STUDENT3 = StudentModelRepository.getModel3();
    private static final Student STUDENT4 = StudentModelRepository.getModel4();
    private static final Student STUDENT5 = StudentModelRepository.getModel5();
    private static final Student STUDENT6 = StudentModelRepository.getModel6();
    private static final Group GROUP11 = GroupModelRepository.getModel11();
    private static final Group GROUP12 = GroupModelRepository.getModel12();
    private static final Group GROUP13 = GroupModelRepository.getModel13();

    @BeforeEach
    public void setup() {
        flyway.migrate();
        groupRepository.save(GROUP11);
        groupRepository.save(GROUP12);
        groupRepository.save(GROUP13);
        studentRepository.save(STUDENT1);
        studentRepository.save(STUDENT2);
        studentRepository.save(STUDENT3);
        studentRepository.save(STUDENT4);
        studentRepository.save(STUDENT5);
        studentRepository.saveAndFlush(STUDENT6);
    }

    @Test
    public void WhenPutAtTableDbStudentObjects_thenGetThisObjectsFindById() {
        int id = STUDENT2.getId();
        assertTrue(studentRepository.findById(id).get().equals(STUDENT2));
    }

    @Test
    public void WhenPutAtTableDbStudentObjects_thenGetThisObjects() {
        assertTrue(studentRepository.findAll().containsAll(Arrays.asList(STUDENT1, STUDENT2, STUDENT3, STUDENT4, STUDENT5, STUDENT6)));
    }

    @Test
    public void WhenUpdateAtTableDbStudentObject_thenGetNewObject() {
        Student student = studentRepository.findById(5).get();
        String firstNameExpected = "test_first_name";
        student.setFirstName(firstNameExpected);
        student.setGroup(GROUP11);

        Student studentActually = studentRepository.save(student);

        String firstNameActually = studentActually.getFirstName();
        Group groupActually = studentActually.getGroup();
        assertEquals(firstNameExpected, firstNameActually);
        assertEquals(GROUP11, groupActually);
    }

    @Test
    public void WhenFindByIdGroup_thenReturnGroupWithSetId() {
        int groupId = GROUP12.getId();
        List<Student> students = studentRepository.findAllByGroupId(groupId);
        assertTrue(students.containsAll(Arrays.asList(STUDENT3, STUDENT4, STUDENT5)));
    }

    @Test
    public void whenPutIdGroup_thenGetListOfStudentsWithIdGroup() {
        int idGroup = STUDENT1.getGroup().getId();
        List<Student> studentsExpected = Arrays.asList(STUDENT1, STUDENT2);
        List<Student> studentsActually = studentRepository.findAllByGroupId(idGroup);
        assertEquals(studentsExpected, studentsActually);
    }

    @Test
    public void whenPutIdFees_thenGetExitsStudent() {
        int idFees = STUDENT1.getIdFees();
        Student studentActually = studentRepository.findByIdFees(idFees);
        assertEquals(STUDENT1, studentActually);
    }

    @Test
    public void whenPutIdGroup_thenGetCountStudentsInGroup() {
        int idGroup = STUDENT1.getGroup().getId();
        long count = studentRepository.countByGroupId(idGroup);
        assertEquals(2, count);
    }

    @AfterEach
    public void cleanDB() {
        flyway.clean();
    }
}

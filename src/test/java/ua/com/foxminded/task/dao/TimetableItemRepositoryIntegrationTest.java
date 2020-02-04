package ua.com.foxminded.task.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;

import ua.com.foxminded.task.domain.TimetableItem;

@DBRider
@SpringBootTest
public class TimetableItemRepositoryIntegrationTest {

    @Autowired
    private TimetableItemRepository timetableItemRepository;
    
    @Autowired
    private DataSource dataSource;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(() -> dataSource.getConnection());

    @Test
    @DataSet(cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void whenRepositoryHasNotRecords_thenReturnEmptyList() {
        List<TimetableItem> timetableItems = timetableItemRepository.findAll();
        assertThat(timetableItems).isNotNull().isEmpty();
    }

    @Test
    @Transactional
    @DataSet(value = "timetableItem/timetableItems.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "timetableItem/expected-timetableItems.yml")
    public void whenRepositoryHasRecords_thenReturnNonEmptyList() {
        List<TimetableItem> timetableItems = timetableItemRepository.findAll();
        assertThat(timetableItems).isNotNull().isNotEmpty().hasSize(10);
    }

//    @Test
//    @Transactional
//    @DataSet(value = "teacher/teachers.yml", 
//             cleanBefore = true, 
//             skipCleaningFor = "flyway_schema_history")
//    public void whenPutAtTableDbObjects_thenGetThisObjectsFindById() {
//        TimetableItem expectedTeacher = TeacherModelRepository.getModel3();
//        expectedTeacher.setDepartment(null);
//        expectedTeacher.setSubjects(new ArrayList<>());
//        expectedTeacher.setId(3);
//        int id = 3;
//        TimetableItem actuallyTeacher = timetableItemRepository.getOne(id);
//        assertThat(expectedTeacher).isEqualToComparingFieldByField(actuallyTeacher);
//    }
//
//    @Test
//    @DataSet(value = "teacher/teachers.yml", 
//             cleanBefore = true, 
//             skipCleaningFor = "flyway_schema_history")
//    public void whenFindByIdFees_thenTeacherReturned() {
//        int idFees = 333111111;
//        TimetableItem teacherActually = timetableItemRepository.findByIdFees(idFees);
//        assertThat(teacherActually.getIdFees()).isEqualTo(idFees);
//    }
//    
//    @Test
//    @DataSet(cleanBefore = true, skipCleaningFor = "flyway_schema_history")
//    @ExpectedDataSet(value = "teacher/expected-teacher.yml")
//    public void whenSaveObject_thenExpectRecord() {
//        TimetableItem teacher = TeacherModelRepository.getModel2();
//        teacher.setSubjects(new ArrayList<>());
//        teacher.setDepartment(null);
//        TimetableItem actuallyTeacher = timetableItemRepository.saveAndFlush(teacher);
//        assertThat(actuallyTeacher).isNotNull();
//    }
//
//    @Test
//    @DataSet(value = "teacher/teacher.yml", 
//             cleanBefore = true, 
//             skipCleaningFor = "flyway_schema_history")
//    @ExpectedDataSet(value = "teacher/expected-teacher.yml")
//    public void whenUpdateObject_thenExpectUpdatedRecord() {
//        TimetableItem teacher = TeacherModelRepository.getModel2();
//        teacher.setSubjects(new ArrayList<>());
//        teacher.setDepartment(null);
//        teacher.setId(1);
//        TimetableItem studentActually = timetableItemRepository.saveAndFlush(teacher);
//        assertThat(studentActually).isNotNull();
//    }

//    @Test
//    @DataSet(value = "student/studentsWithGroups.yml", 
//             cleanBefore = true, 
//             skipCleaningFor = "flyway_schema_history")
//    public void whenFindByGroupId_thenReturnTwoRecords() {
//        List<TimetableItem> students = teacherRepository.findAllByGroupId(2);
//        assertThat(students).isNotNull().isNotEmpty().hasSize(3);
//    }

//    @Test
//    @DataSet(value = "student/studentsWithGroups.yml", 
//             cleanBefore = true, 
//             skipCleaningFor = "flyway_schema_history")
//    public void whenCountByGroupId_thenReturnTwoRecords() {
//        long count = teacherRepository.countByGroupId(2);
//        assertThat(count).isEqualTo(3L);
//    }

//    @Test
//    @DataSet(value = "student/studentsWithGroups.yml", 
//             cleanBefore = true, 
//             skipCleaningFor = "flyway_schema_history")
//    public void whenCheckExistStudentByIdAndGroupId_thenReturnTrue() {
//        boolean isExists = teacherRepository.existsStudentByIdAndGroupId(3, 2);
//        assertTrue(isExists);
//    }
    
}

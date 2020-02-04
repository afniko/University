package ua.com.foxminded.task.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import ua.com.foxminded.task.domain.repository.TimetableItemModelRepository;

@DBRider
@SpringBootTest
public class TimetableItemRepositoryIntegrationTest {

    @Autowired
    private TimetableItemRepository timetableItemRepository;
    
    @Autowired
    private DataSource dataSource;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(() -> dataSource.getConnection());

//    @Test
    @DataSet(cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void whenRepositoryHasNotRecords_thenReturnEmptyList() {
        List<TimetableItem> timetableItems = timetableItemRepository.findAll();
        assertThat(timetableItems).isNotNull().isEmpty();
    }

//    @Test
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
    @Transactional
    @DataSet(value = "timetableItem/timetableItems.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    public void whenPutAtTableDbObjects_thenGetThisObjectsFindById() {
        int id = 3;
        TimetableItem expectedTimetableItem = TimetableItemModelRepository.getModel3();
        expectedTimetableItem.getTeacher().setSubjects(new ArrayList<>());
        expectedTimetableItem.setId(id);
        TimetableItem actuallyTimetableItem = timetableItemRepository.getOne(id);
        assertThat(expectedTimetableItem).isEqualToComparingFieldByField(actuallyTimetableItem);
    }

//    @Test
//    @DataSet(value = "timetableItem/timetableItems.yml", 
//             cleanBefore = true, 
//             skipCleaningFor = "flyway_schema_history")
//    public void whenFindByIdFees_thenTeacherReturned() {
//        int idFees = 333111111;
//        TimetableItem teacherActually = timetableItemRepository.findByIdFees(idFees);
//        assertThat(teacherActually.getIdFees()).isEqualTo(idFees);
//    }
    
    @Test
    @DataSet(cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "timetableItem/expected-timetableItem.yml")
    public void whenSaveObject_thenExpectRecord() {
        TimetableItem timetableItem = TimetableItemModelRepository.getModel2();
        timetableItem.setAuditory(null);
        timetableItem.setLecture(null);
        timetableItem.setSubject(null);
        timetableItem.setTeacher(null);
        timetableItem.setGroups(new ArrayList<>());
        TimetableItem actuallyTeacher = timetableItemRepository.saveAndFlush(timetableItem);
        assertThat(actuallyTeacher).isNotNull();
    }

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

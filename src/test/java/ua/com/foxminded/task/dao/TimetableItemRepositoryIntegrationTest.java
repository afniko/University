package ua.com.foxminded.task.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
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

    @Test
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
        TimetableItem actuallyTimetableItem = timetableItemRepository.saveAndFlush(timetableItem);
        assertThat(actuallyTimetableItem).isNotNull();
    }

    @Test
    @DataSet(value = "timetableItem/timetableItem.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "timetableItem/expected-timetableItem.yml")
    public void whenUpdateObject_thenExpectUpdatedRecord() {
        TimetableItem timetableItem = TimetableItemModelRepository.getModel2();
        timetableItem.setId(1);
        timetableItem.setAuditory(null);
        timetableItem.setLecture(null);
        timetableItem.setSubject(null);
        timetableItem.setTeacher(null);
        timetableItem.setGroups(new ArrayList<>());
        TimetableItem actuallyTimetableItem = timetableItemRepository.saveAndFlush(timetableItem);
        assertThat(actuallyTimetableItem).isNotNull();
    }
    
    @Test
    @Transactional
    @DataSet(value = "timetableItem/timetableItems.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "timetableItem/expected-timetableItems.yml")
    public void whenRepositoryHasDuplicateAuditoryLectureAndDate_thenReturnBoolean() {
        LocalDate date = LocalDate.of(2020, 06, 25);
        boolean existTrue = timetableItemRepository.existsByAuditoryIdAndLectureIdAndDate(1, 1, date); 
        assertTrue(existTrue);
        boolean existFalse = timetableItemRepository.existsByAuditoryIdAndLectureIdAndDate(2, 3, date); 
        assertFalse(existFalse);
    }
    
    @Test
    @Transactional
    @DataSet(value = "timetableItem/timetableItems.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "timetableItem/expected-timetableItems.yml")
    public void whenRepositoryHasDuplicateTeacherLectureAndDate_thenReturnBoolean() {
        LocalDate date = LocalDate.of(2020, 06, 25);
        boolean existTrue = timetableItemRepository.existsByTeacherIdAndLectureIdAndDate(1, 1, date); 
        assertTrue(existTrue);
        boolean existFalse = timetableItemRepository.existsByTeacherIdAndLectureIdAndDate(1, 4, date); 
        assertFalse(existFalse);
    }

}

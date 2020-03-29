package ua.com.foxminded.task.dao;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.repository.AuditoryModelRepository;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@SpringBootTest
public class AuditoryRepositoryIntegrationTest {
    
    @Autowired
    private AuditoryRepository auditoryRepository;
    @Autowired
    private DataSource dataSource;
    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(() -> dataSource.getConnection());
    
    @Test
    @DataSet(cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void whenRepositoryHasNotRecords_thenReturnEmptyList() {
        List<Auditory> auditory = auditoryRepository.findAll();
        assertThat(auditory).isNotNull().isEmpty();
    }

    @Test
    @DataSet(value = "auditory/auditories.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "auditory/expected-auditories.yml")
    public void whenRepositoryHasRecords_thenReturnNonEmptyList() {
        List<Auditory> auditory = auditoryRepository.findAll();
        assertThat(auditory).isNotNull().isNotEmpty().hasSize(6);
    }

    @Test
    @DataSet(value = "auditory/auditories.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    public void whenPutAtTableDbObjects_thenGetThisObjectsFindById() {
        Auditory expectedAuditory = AuditoryModelRepository.getModel3();
        int id = 3;
        Auditory actuallyAuditory = auditoryRepository.findById(id).get();
        assertThat(expectedAuditory).isEqualTo(actuallyAuditory);
    }

    @Test
    @DataSet(value = "auditory/auditories.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    public void whenFindByAuditoryNumber_thenAuditoryReturned() {
        String auditoryNumber = "103a";
        Auditory auditoryActually = auditoryRepository.findByAuditoryNumber(auditoryNumber);
        assertThat(auditoryActually.getAuditoryNumber()).isNotNull().isEqualTo(auditoryNumber);
    }

    @Test
    @DataSet(cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "auditory/expected-auditory.yml")
    public void whenSaveObject_thenExpectRecord() {
        Auditory auditory = AuditoryModelRepository.getModel2();
        auditory.setType(null);
        Auditory actuallyAuditory = auditoryRepository.saveAndFlush(auditory);
        assertThat(actuallyAuditory).isNotNull();
    }

    @Test
    @DataSet(value = "auditory/auditory.yml", 
             cleanBefore = true, 
             skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "auditory/expected-auditory.yml")
    public void whenUpdateObject_thenExpectUpdatedRecord() {
        Auditory auditory = AuditoryModelRepository.getModel2();
        auditory.setId(1);
        auditory.setType(null);
        Auditory actuallyAuditory = auditoryRepository.saveAndFlush(auditory);
        assertThat(actuallyAuditory).isNotNull();
    }
}

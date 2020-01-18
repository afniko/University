package ua.com.foxminded.task.dao;

import javax.sql.DataSource;

import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.junit5.api.DBRider;

@DBRider
@SpringBootTest
public class AuditoryRepositoryIntegrationTest {
    
    @Autowired
    private AuditoryRepository auditoryRepository;
    @Autowired
    private DataSource dataSource;
    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(() -> dataSource.getConnection());
}

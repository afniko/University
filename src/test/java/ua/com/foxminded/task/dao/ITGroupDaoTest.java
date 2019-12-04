package ua.com.foxminded.task.dao;

import org.flywaydb.test.annotation.FlywayTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@FlywayTest
public class ITGroupDaoTest {

//    @Autowired
//    private  GroupDao groupDao;
//
//    private static final Group GROUP11 = GroupModelRepository.getModel11();
//    private static final Group GROUP12 = GroupModelRepository.getModel12();
//    private static final Group GROUP13 = GroupModelRepository.getModel13();
//
////    @Autowired
////    public ITGroupDaoTest(GroupDao groupDao) {
////        this.groupDao = groupDao;
////    }
//
//    @BeforeAll
//    public static void createRecords(@Autowired GroupDao groupDao) {
//        groupDao.create(GROUP11);
//        groupDao.create(GROUP12);
//        groupDao.create(GROUP13);
//    }
//
////    @Test
//    public void WhenPutAtTableDbGroupObjects_thenGetThisObjectsFindById() {
//        int id = GROUP12.getId();
//        assertTrue(groupDao.findById(id).equals(GROUP12));
//    }
//
////    @Test
//    public void WhenPutAtTableDbGroupObjects_thenGetThisObjects() {
//        assertTrue(groupDao.findAll().containsAll(Arrays.asList(GROUP12, GROUP13)));
//    }
//
////    @Test
//    public void WhenUpdateAtTableDbGroupObject_thenGetNewObject() {
//        String titleExpected = "test_title_text";
//        Group group = groupDao.findById(1);
//        group.setTitle(titleExpected);
//        Group groupActually = groupDao.update(group);
//        String titleActually = groupActually.getTitle();
//        assertEquals(titleExpected, titleActually);
//    }
//
////    @Test
//    public void WhenFindByIdNotExistinRecord_thenGetException() {
//        Assertions.assertThrows(NoEntityFoundException.class, () -> groupDao.findById(9999));
//    }

//    @AfterAll
//    public static void removeCreatedTables(@Autowired Flyway flyway) {
//        flyway.clean();
//    }
}

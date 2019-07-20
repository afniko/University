package ua.com.foxminded.task.dao;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.dao.impl.TimetableItemDaoImpl;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.TimetableItem;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.TimetableItemModelRepository;

//@RunWith(JUnitPlatform.class)
public class TimetableItemDaoTest {

    private static TimetableItemDao timetableItemDao = null;
    private static GroupDao groupDao = null;
    private static final TimetableItem TIMETABLE_ITEM1 = TimetableItemModelRepository.getModel1();
    private static final TimetableItem TIMETABLE_ITEM2 = TimetableItemModelRepository.getModel2();
    private static final TimetableItem TIMETABLE_ITEM3 = TimetableItemModelRepository.getModel3();
    private static final Group GROUP1 = GroupModelRepository.getModel1();
    private static final Group GROUP2 = GroupModelRepository.getModel2();
    private static final Group GROUP3 = GroupModelRepository.getModel3();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        timetableItemDao = new TimetableItemDaoImpl();
        groupDao = new GroupDaoImpl();
        groupDao.create(GROUP1);
        groupDao.create(GROUP2);
        groupDao.create(GROUP3);
        timetableItemDao.create(TIMETABLE_ITEM1);
        timetableItemDao.create(TIMETABLE_ITEM2);
        timetableItemDao.create(TIMETABLE_ITEM3);
    }

    @Test
    public void whenGetTimetableItemsByFindId_thenContainsTimetableItem() {
        TimetableItem timetableItem = new TimetableItem();
        timetableItem.setId(1);
        assertTrue(timetableItemDao.findById(timetableItem).equals(TIMETABLE_ITEM1));
    }

    @Test
    public void whenGetTimetableItemsByFindAll_thenContainsTimetableItems() {
        assertTrue(timetableItemDao.findAll().containsAll(Arrays.asList(TIMETABLE_ITEM1, TIMETABLE_ITEM2, TIMETABLE_ITEM3)));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }

}

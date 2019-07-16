package ua.com.foxminded.task.dao;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.TimetableItemDaoImpl;
import ua.com.foxminded.task.domain.TimetableItem;
import ua.com.foxminded.task.domain.repository.TimetableItemModelRepository;

@RunWith(JUnitPlatform.class)
public class TimetableItemDaoTest {

    private static TimetableItemDao timetableItemDao = null;
    private static final TimetableItem TIMETABLE_ITEM1 = TimetableItemModelRepository.getModel1();
    private static final TimetableItem TIMETABLE_ITEM2 = TimetableItemModelRepository.getModel2();
    private static final TimetableItem TIMETABLE_ITEM3 = TimetableItemModelRepository.getModel3();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        timetableItemDao = new TimetableItemDaoImpl();
        timetableItemDao.create(TIMETABLE_ITEM1);
        timetableItemDao.create(TIMETABLE_ITEM2);
        timetableItemDao.create(TIMETABLE_ITEM3);
        System.out.println("TimetableItem created!");
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

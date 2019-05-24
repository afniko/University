package ua.com.foxminded.task.domain;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.StudentModelRepository;
import ua.com.foxminded.task.domain.repository.TeacherModelRepository;
import ua.com.foxminded.task.domain.repository.TestModel;
import ua.com.foxminded.task.domain.repository.UniversityModelRepository;

@RunWith(JUnitPlatform.class)
public class UniversityTest {

    @Test
    public void test() {
        University university = UniversityModelRepository.getModel(TestModel.MODEL_1);
        System.out.println(university.getTimetable().getTimetableItems());
        System.out.println();
        Student student = StudentModelRepository.getModel(TestModel.MODEL_1);
        Date startPeriod = Date.valueOf("2019-01-20");
        Date endPeriod = Date.valueOf("2019-01-22");
        
        Timetable timetable = university.getTimetable().findPeriod(student, startPeriod, endPeriod);
        System.out.println(timetable.getTimetableItems());
// TODO візуально перевіряю що дійсно розклад містить лише ті позиції які призначені для цього студента
//        та у встановлений період. Дороблю цю перевірку через Mickito
        System.out.println("\n test university student \n");
    }
    
    @Test
    public void test2() {
        University university = UniversityModelRepository.getModel(TestModel.MODEL_1);
        System.out.println(university.getTimetable().getTimetableItems());
        System.out.println();
        Teacher teacher = TeacherModelRepository.getModel(TestModel.MODEL_1);
        Date startPeriod = Date.valueOf("2019-01-20");
        Date endPeriod = Date.valueOf("2019-01-22");
        
        Timetable timetable = university.getTimetable().findPeriod(teacher, startPeriod, endPeriod);
        System.out.println(timetable.getTimetableItems());
// TODO візуально перевіряю що дійсно розклад містить лише ті позиції які призначені для цього вчителя
//      та у встановлений період. Дороблю цю перевірку через Mickito
        System.out.println("\n test university teacher \n");
    }
}

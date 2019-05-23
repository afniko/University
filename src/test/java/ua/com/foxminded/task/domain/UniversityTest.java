package ua.com.foxminded.task.domain;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.TestModel;
import ua.com.foxminded.task.domain.repository.UniversityModelRepository;

@RunWith(JUnitPlatform.class)
public class UniversityTest {

    @Test
    public void test() {
        University university = UniversityModelRepository.getModel(TestModel.MODEL_1);
        System.out.println(university.getFaculties());
        System.out.println(university.getTimetable().getTimetableItems());
        System.out.println("\n test university");
    }
}

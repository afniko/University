package ua.com.foxminded.task.domain;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.DepartmentModelRepository;
import ua.com.foxminded.task.domain.repository.TestModel;

@RunWith(JUnitPlatform.class)
class DepartmentTest {

    @Test
    void test() {
        Department departmentTest = DepartmentModelRepository.getModel(TestModel.MODEL_1);
        System.out.println(departmentTest.getGroups());
        System.out.println(departmentTest.getTeachers());
        System.out.println("\n test department \n");
    }

}

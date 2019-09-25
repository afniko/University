package ua.com.foxminded.task.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ua.com.foxminded.task.domain.repository.DepartmentModelRepository;
import ua.com.foxminded.task.domain.repository.FacultyModelRepository;

public class FacultyTest {

    @Test
    public void whenAddDepartmentToFaculty_thenFacultyContainsDepartment() {
        Faculty faculty = FacultyModelRepository.getEmptyModel1();
        Department department = DepartmentModelRepository.getModel1();
        faculty.addDepartment(department);
        assertTrue(faculty.getDepartments().contains(department));
    }

    @Test
    public void whenRemoveDepartmentFromFaculty_thenFacultyNonContainsDepartment() {
        Faculty faculty = FacultyModelRepository.getModel1();
        Department department = DepartmentModelRepository.getModel1();
        faculty.removeDepartment(department);
        assertFalse(faculty.getDepartments().contains(department));
    }
}

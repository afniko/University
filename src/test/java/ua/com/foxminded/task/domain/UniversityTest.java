package ua.com.foxminded.task.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.AuditoryModelRepository;
import ua.com.foxminded.task.domain.repository.FacultyModelRepository;
import ua.com.foxminded.task.domain.repository.UniversityModelRepository;

@RunWith(JUnitPlatform.class)
public class UniversityTest {

    @Test
    public void whenAddFacultyToUniversity_thenUniversityContainsFaculty() {
        University university = UniversityModelRepository.getEmptyModel();
        Faculty faculty = FacultyModelRepository.getModel1();
        university.addFaculty(faculty);
        assertTrue(university.getFaculties().contains(faculty));
    }

    @Test
    public void whenRemoveFacultyFromUniversity_thenUniversityNonContainsFaculty() {
        University university = UniversityModelRepository.getModel();
        Faculty faculty = FacultyModelRepository.getModel1();
        university.removeFaculty(faculty);
        assertFalse(university.getFaculties().contains(faculty));
    }

    @Test
    public void whenAddAuditoryToUniversity_thenUniversityContainsAuditory() {
        University university = UniversityModelRepository.getEmptyModel();
        Auditory auditory = AuditoryModelRepository.getModel1();
        university.addAuditory(auditory);
        assertTrue(university.getAuditories().contains(auditory));
    }

    @Test
    public void whenRemoveAuditoryFromUniversity_thenUniversityNonContainsAuditory() {
        University university = UniversityModelRepository.getModel();
        Auditory auditory = AuditoryModelRepository.getModel1();
        university.removeAuditory(auditory);
        assertFalse(university.getAuditories().contains(auditory));
    }
}

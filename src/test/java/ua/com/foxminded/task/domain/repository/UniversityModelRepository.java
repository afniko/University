package ua.com.foxminded.task.domain.repository;

import ua.com.foxminded.task.domain.University;

public class UniversityModelRepository {

    public static University getModel() {
        University university = new University();
        university.setTitle("university");
        university.setFaculties(FacultyModelRepository.getModels());
        university.setAuditories(AuditoryModelRepository.getModels());
        university.setTimetable(TimetableModelRepository.getModel());
        return university;
    }

}

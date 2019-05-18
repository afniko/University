package ua.com.foxminded.task.service;

import java.util.List;

import ua.com.foxminded.task.domain.Faculty;
import ua.com.foxminded.task.domain.University;

public class FacultyCreator {

    public void create(University university) {
        List<Faculty> faculties = university.getFaculties();

    }

}

package ua.com.foxminded.task.domain.service;

import java.util.List;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Student;

public class StudentController {
    private static StudentDao studentDao = new StudentDaoImpl();

    private static StudentController instance;

    private StudentController() {
    }

    public Student findById(int id) {
        return studentDao.findById(id);
    }

    public List<Student> findAll() {
        return studentDao.findAll();
    }

    public synchronized static StudentController getInstance() {
        if (instance == null) {
            instance = new StudentController();
        }
        return instance;
    }
}

package ua.com.foxminded.task.domain.service;

import java.util.List;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Student;

public class StudentService {
    private static StudentDao studentDao = new StudentDaoImpl();

    public Student findById(int id) {
        return studentDao.findById(id);
    }

    public List<Student> findAll() {
        return studentDao.findAll();
    }

}

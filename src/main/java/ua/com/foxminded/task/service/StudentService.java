package ua.com.foxminded.task.service;

import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.dto.StudentDto;

public class StudentService {
    private StudentDao studentDao = new StudentDaoImpl();

    public StudentDto findById(int id) {
        return studentDao.findById(id).convertToDto();
    }

    public List<StudentDto> findAll() {
        return studentDao.findAll().stream().map(s -> s.convertToDto()).collect(Collectors.toList());
    }

}

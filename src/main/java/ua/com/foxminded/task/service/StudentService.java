package ua.com.foxminded.task.service;

import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.converter.ConverterToDtoService;

public class StudentService {
    private StudentDao studentDao = new StudentDaoImpl();

    public StudentDto findById(int id) {
        Student student = studentDao.findById(id);
        return ConverterToDtoService.convert(student);
    }

    public List<StudentDto> findAll() {
        return studentDao.findAll().stream().map(ConverterToDtoService::convert).collect(Collectors.toList());
    }

}

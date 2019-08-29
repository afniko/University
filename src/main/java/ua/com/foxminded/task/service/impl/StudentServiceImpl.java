package ua.com.foxminded.task.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.converter.ConverterFromDtoService;
import ua.com.foxminded.task.service.converter.ConverterToDtoService;

public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao = new StudentDaoImpl();

    public StudentServiceImpl() {
    }

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public StudentDto findByIdDto(int id) {
        Student student = studentDao.findById(id);
        return ConverterToDtoService.convert(student);
    }

    @Override
    public List<StudentDto> findAllDto() {
        return studentDao.findAll().stream().map(ConverterToDtoService::convert).collect(Collectors.toList());
    }

    @Override
    public StudentDto create(StudentDto studentDto) {
        Student student = ConverterFromDtoService.convert(studentDto);
        Student studentResult = studentDao.create(student);
        return ConverterToDtoService.convert(studentResult);
    }

    @Override
    public StudentDto update(StudentDto studentDto) {
        Student student = ConverterFromDtoService.convert(studentDto);
        Student studenUpdated = studentDao.update(student);
        return ConverterToDtoService.convert(studenUpdated);
    }

}

package ua.com.foxminded.task.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.converter.ConverterFromDtoService;
import ua.com.foxminded.task.service.converter.ConverterToDtoService;

public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao = new StudentDaoImpl();
    private GroupDao groupDao = new GroupDaoImpl();

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
        Group group = null;
        Student student = ConverterFromDtoService.convert(studentDto);
        if (Objects.nonNull(student.getGroup())) {
            group = groupDao.findById(student.getGroup().getId());
        }
        student.setGroup(group);
        Student studenUpdated = studentDao.update(student);
        return ConverterToDtoService.convert(studenUpdated);
    }

}

package ua.com.foxminded.task.service.impl;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.dao.impl.StudentDaoImpl;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
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
        Student student = retriveStudentFromDto(studentDto);

        Student studentResult = studentDao.create(student);
        return ConverterToDtoService.convert(studentResult);
    }

    @Override
    public StudentDto update(StudentDto studentDto) {
        Student student = retriveStudentFromDto(studentDto);

        Student studenUpdated = studentDao.update(student);
        return ConverterToDtoService.convert(studenUpdated);
    }

    private Student retriveStudentFromDto(StudentDto studentDto) {
        Student student = (studentDto.getId() != 0) ? studentDao.findById(studentDto.getId()) : new Student();

        Group group = nonNull(studentDto.getIdGroup()) ? groupDao.findById(Integer.valueOf(studentDto.getIdGroup())) : null;
        student.setGroup(group);

        student.setFirstName(studentDto.getFirstName());
        student.setMiddleName(studentDto.getMiddleName());
        student.setLastName(studentDto.getLastName());
        student.setBirthday(studentDto.getBirthday());
        student.setIdFees(studentDto.getIdFees());
        return student;
    }

}

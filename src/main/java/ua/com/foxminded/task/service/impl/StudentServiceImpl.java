package ua.com.foxminded.task.service.impl;

import static java.util.Objects.nonNull;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.impl.jdbc.GroupDaoImpl;
import ua.com.foxminded.task.dao.impl.jdbc.StudentDaoImpl;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.converter.ConverterToDtoService;

public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao;
    private GroupDao groupDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public StudentServiceImpl() {
        studentDao = new StudentDaoImpl();
        groupDao = new GroupDaoImpl();
    }

    public StudentServiceImpl(StudentDao studentDao, GroupDao groupDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
    }

    @Override
    public StudentDto findByIdDto(int id) {
        LOGGER.debug("findByIdDto() [id:{}]", id);
        Student student = studentDao.findById(id);
        return ConverterToDtoService.convert(student);
    }

    @Override
    public List<StudentDto> findAllDto() {
        LOGGER.debug("findAllDto()");
        return studentDao.findAll().stream().map(ConverterToDtoService::convert).collect(Collectors.toList());
    }

    @Override
    public StudentDto create(StudentDto studentDto) {
        LOGGER.debug("create() [studentDto:{}]", studentDto);
        Student student = retriveStudentFromDto(studentDto);
        Student studentResult = studentDao.create(student);
        return ConverterToDtoService.convert(studentResult);
    }

    @Override
    public StudentDto update(StudentDto studentDto) {
        LOGGER.debug("update() [studentDto:{}]", studentDto);
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

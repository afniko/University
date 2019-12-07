package ua.com.foxminded.task.service.impl;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ua.com.foxminded.task.dao.GroupRepository;
import ua.com.foxminded.task.dao.StudentRepository;
import ua.com.foxminded.task.dao.exception.EntityAlreadyExistsException;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.converter.ConverterToDtoService;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private GroupRepository groupRepository;
    private Logger logger;

    @Autowired
    public StudentServiceImpl(Logger logger, StudentRepository studentRepository, GroupRepository groupRepository) {
        this.logger = logger;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public StudentDto findByIdDto(int id) {
        logger.debug("findByIdDto() [id:{}]", id);
        Student student = studentRepository.getOne(id);
        return ConverterToDtoService.convert(student);
    }

    @Override
    public List<StudentDto> findAllDto() {
        logger.debug("findAllDto()");
        return studentRepository.findAll().stream().map(ConverterToDtoService::convert).collect(Collectors.toList());
    }

    @Override
    public StudentDto create(StudentDto studentDto) {
        logger.debug("create() [studentDto:{}]", studentDto);
        Student student = retriveStudentFromDto(studentDto);
        Student studentResult = null;
        try {
            studentResult = studentRepository.saveAndFlush(student);
        } catch (DataIntegrityViolationException e) {
            logger.warn("create() [student:{}], exception:{}", student, e);
            throw new EntityAlreadyExistsException("create() student: " + student, e);
        }
        return ConverterToDtoService.convert(studentResult);
    }

    @Override
    public StudentDto update(StudentDto studentDto) {
        logger.debug("update() [studentDto:{}]", studentDto);
        int studentId = studentDto.getId();
        if (!studentRepository.existsById(studentId)) {
            throw new NoEntityFoundException("Student not exist!");
        }
        Student student = retriveStudentFromDto(studentDto);
        Student studenUpdated = null;
        try {
            studenUpdated = studentRepository.saveAndFlush(student);
        } catch (DataIntegrityViolationException e) {
            logger.warn("update() [student:{}], exception:{}", student, e);
            throw new EntityAlreadyExistsException("update() student: " + student, e);
        }
        return ConverterToDtoService.convert(studenUpdated);
    }

    private Student retriveStudentFromDto(StudentDto studentDto) {
        Student student = (studentDto.getId() != 0) ? studentRepository.getOne(studentDto.getId()) : new Student();

        Group group = nonNull(studentDto.getIdGroup()) && isNoneBlank(studentDto.getIdGroup()) ? groupRepository.getOne(Integer.valueOf(studentDto.getIdGroup())) : null;
        student.setGroup(group);

        student.setFirstName(studentDto.getFirstName());
        student.setMiddleName(studentDto.getMiddleName());
        student.setLastName(studentDto.getLastName());
        student.setBirthday(studentDto.getBirthday());
        student.setIdFees(studentDto.getIdFees());
        return student;
    }

}

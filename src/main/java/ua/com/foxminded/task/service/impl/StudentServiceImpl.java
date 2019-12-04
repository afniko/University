package ua.com.foxminded.task.service.impl;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.foxminded.task.dao.GroupRepo;
import ua.com.foxminded.task.dao.StudentRepo;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.service.StudentService;
import ua.com.foxminded.task.service.converter.ConverterToDtoService;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepo studentRepo;
    private GroupRepo groupRepo;
    private Logger logger;

    @Autowired
    public StudentServiceImpl(Logger logger, StudentRepo studentDao, GroupRepo groupDao) {
        this.logger = logger;
        this.studentRepo = studentDao;
        this.groupRepo = groupDao;
    }

    @Override
    public StudentDto findByIdDto(int id) {
        logger.debug("findByIdDto() [id:{}]", id);
        Student student = studentRepo.getOne(id);
        return ConverterToDtoService.convert(student);
    }

    @Override
    public List<StudentDto> findAllDto() {
        logger.debug("findAllDto()");
        return studentRepo.findAll().stream().map(ConverterToDtoService::convert).collect(Collectors.toList());
    }

    @Override
    public StudentDto create(StudentDto studentDto) {
        logger.debug("create() [studentDto:{}]", studentDto);
        Student student = retriveStudentFromDto(studentDto);
        Student studentResult = studentRepo.save(student);
        return ConverterToDtoService.convert(studentResult);
    }

    @Override
    public StudentDto update(StudentDto studentDto) {
        logger.debug("update() [studentDto:{}]", studentDto);
        Student student = retriveStudentFromDto(studentDto);

        Student studenUpdated = studentRepo.save(student);
        return ConverterToDtoService.convert(studenUpdated);
    }

    private Student retriveStudentFromDto(StudentDto studentDto) {
        Student student = (studentDto.getId() != 0) ? studentRepo.getOne(studentDto.getId()) : new Student();

        Group group = nonNull(studentDto.getIdGroup()) && isNoneBlank(studentDto.getIdGroup()) ? groupRepo.getOne(Integer.valueOf(studentDto.getIdGroup())) : null;
        student.setGroup(group);

        student.setFirstName(studentDto.getFirstName());
        student.setMiddleName(studentDto.getMiddleName());
        student.setLastName(studentDto.getLastName());
        student.setBirthday(studentDto.getBirthday());
        student.setIdFees(studentDto.getIdFees());
        return student;
    }

}

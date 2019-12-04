package ua.com.foxminded.task.service.impl;

public class StudentServiceImplTest {

//    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
//    private StudentDao studentDao = mock(StudentDaoImpl.class);
//    private GroupDao groupDao = mock(GroupDaoImpl.class);
////    private StudentServiceImpl studentService = new StudentServiceImpl(logger, studentDao, groupDao);
//    private StudentServiceImpl studentService;
//
//    @Test
//    void whenFindById_thenFindStudentAndConvertItToDto() {
//        Student student = StudentModelRepository.getModel1();
//        StudentDto studentDtoExpected = StudentDtoModelRepository.getModel1();
//        doReturn(student).when(studentDao).findById(1);
//
//        StudentDto studentDtoActually = studentService.findByIdDto(1);
//
//        verify(studentDao, times(1)).findById(any(Integer.class));
//        assertEquals(studentDtoExpected, studentDtoActually);
//    }
//
//    @Test
//    void whenFindByAll_thenFindStudentsAndConvertItToDto() {
//        List<Student> students = StudentModelRepository.getModels1();
//        List<StudentDto> studentDtosExpected = StudentDtoModelRepository.getModels1();
//        doReturn(students).when(studentDao).findAll();
//
//        List<StudentDto> studentDtosActually = studentService.findAllDto();
//
//        verify(studentDao, times(1)).findAll();
//        assertEquals(studentDtosExpected, studentDtosActually);
//    }
//
//    @Test
//    void whenCreate_thenInvocCreateDaoClass() {
//        Student student = StudentModelRepository.getModel1();
//        StudentDto studentDto = StudentDtoModelRepository.getModel1();
//        doReturn(student).when(studentDao).create(student);
//        doReturn(student.getGroup()).when(groupDao).findById(student.getGroup().getId());
//
//        StudentDto studentDtoActually = studentService.create(studentDto);
//
//        verify(studentDao, times(1)).create(student);
//        verify(groupDao, times(1)).findById(student.getGroup().getId());
//        assertEquals(studentDto, studentDtoActually);
//    }
//
//    @Test
//    void whenUpdate_thenInvocUpdateDaoClass() {
//        Student student = StudentModelRepository.getModel1();
//        StudentDto studentDto = StudentDtoModelRepository.getModel1();
//        studentDto.setId(1);
//        student.setId(1);
//        doReturn(student).when(studentDao).update(student);
//        doReturn(student).when(studentDao).findById(student.getId());
//        doReturn(student.getGroup()).when(groupDao).findById(student.getGroup().getId());
//
//        StudentDto studentDtoActually = studentService.update(studentDto);
//
//        verify(studentDao, times(1)).update(student);
//        verify(studentDao, times(1)).findById(student.getId());
//        verify(groupDao, times(1)).findById(student.getGroup().getId());
//        assertEquals(studentDto, studentDtoActually);
//    }

}

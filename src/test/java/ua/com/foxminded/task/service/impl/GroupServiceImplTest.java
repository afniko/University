package ua.com.foxminded.task.service.impl;

public class GroupServiceImplTest {

//    private Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
//    private GroupDao groupDao = mock(GroupDaoImpl.class);
////    private GroupServiceImpl groupService = new GroupServiceImpl(logger, groupDao);
//    private GroupServiceImpl groupService;
//
//    @Test
//    void whenFindById_thenFindGroup() {
//        Group group = GroupModelRepository.getModel1();
//        doReturn(group).when(groupDao).findById(1);
//
//        groupService.findById(1);
//
//        verify(groupDao, times(1)).findById(any(Integer.class));
//    }
//
//    @Test
//    void whenFindById_thenFindGroupAndConvertItToDto() {
//        Group group = GroupModelRepository.getModel1();
//        GroupDto groupDtoExpected = GroupDtoModelRepository.getModel1();
//        doReturn(group).when(groupDao).findById(1);
//
//        GroupDto groupDtoActually = groupService.findByIdDto(1);
//
//        verify(groupDao, times(1)).findById(any(Integer.class));
//        assertEquals(groupDtoExpected, groupDtoActually);
//    }
//
//    @Test
//    void whenFindByAll_thenFindGroupsAndConvertItToDto() {
//        List<Group> groups = GroupModelRepository.getModels1();
//        List<GroupDto> groupDtosExpected = GroupDtoModelRepository.getModels1();
//        doReturn(groups).when(groupDao).findAll();
//
//        List<GroupDto> groupDtosActually = groupService.findAllDto();
//
//        verify(groupDao, times(1)).findAll();
//        assertEquals(groupDtosExpected, groupDtosActually);
//    }
//
//    @Test
//    void whenCreate_thenInvocCreateDaoClass() {
//        GroupDto groupDto = GroupDtoModelRepository.getModel1();
//        Group groupInput = GroupModelRepository.getModel1();
//        Group groupExpected = GroupModelRepository.getModelWithId();
//
//        doReturn(groupExpected).when(groupDao).create(groupInput);
//
//        GroupDto groupDtoActually = groupService.create(groupDto);
//
//        verify(groupDao, times(1)).create(groupInput);
//        assertEquals(groupDto, groupDtoActually);
//    }
//
//    @Test
//    void whenUpdate_thenInvocUpdateDaoClass() {
//        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
//        Group group = GroupModelRepository.getModelWithId();
//        Group groupExpected = GroupModelRepository.getModelWithId();
//        groupExpected.setId(1);
//        doReturn(groupExpected).when(groupDao).update(group);
//        doReturn(group).when(groupDao).findById(groupDto.getId());
//
//        GroupDto groupDtoActually = groupService.update(groupDto);
//
//        verify(groupDao, times(1)).update(groupExpected);
//        verify(groupDao, times(1)).findById(groupDto.getId());
//        assertEquals(groupDto, groupDtoActually);
//    }
}

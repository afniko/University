package ua.com.foxminded.task.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import ua.com.foxminded.task.dao.GroupRepository;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;

public class GroupServiceImplTest {

    @Mock private Logger logger;
    @Mock private GroupRepository groupRepository;
    @InjectMocks private GroupServiceImpl groupService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenFindById_thenFindGroup() {
        Group group = GroupModelRepository.getModel1();
        doReturn(group).when(groupRepository).getOne(1);

        groupService.findById(1);

        verify(groupRepository, times(1)).getOne(any(Integer.class));
    }

    @Test
    void whenFindById_thenFindGroupAndConvertItToDto() {
        Group group = GroupModelRepository.getModel1();
        GroupDto groupDtoExpected = GroupDtoModelRepository.getModel1();
        doReturn(group).when(groupRepository).getOne(1);

        GroupDto groupDtoActually = groupService.findByIdDto(1);

        verify(groupRepository, times(1)).getOne(any(Integer.class));
        assertEquals(groupDtoExpected, groupDtoActually);
    }

    @Test
    void whenFindByAll_thenFindGroupsAndConvertItToDto() {
        List<Group> groups = GroupModelRepository.getModels1();
        List<GroupDto> groupDtosExpected = GroupDtoModelRepository.getModels1();
        doReturn(groups).when(groupRepository).findAll();

        List<GroupDto> groupDtosActually = groupService.findAllDto();

        verify(groupRepository, times(1)).findAll();
        assertEquals(groupDtosExpected, groupDtosActually);
    }

    @Test
    void whenCreate_thenInvocCreateDaoClass() {
        GroupDto groupDto = GroupDtoModelRepository.getModel1();
        Group groupInput = GroupModelRepository.getModel1();
        Group groupExpected = GroupModelRepository.getModelWithId();

        doReturn(groupExpected).when(groupRepository).saveAndFlush(groupInput);

        GroupDto groupDtoActually = groupService.create(groupDto);

        verify(groupRepository, times(1)).saveAndFlush(groupInput);
        assertEquals(groupDto, groupDtoActually);
    }

    @Test
    void whenUpdate_thenInvocUpdateDaoClass() {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        Group group = GroupModelRepository.getModelWithId();
        Group groupExpected = GroupModelRepository.getModelWithId();
        groupExpected.setId(1);
        doReturn(groupExpected).when(groupRepository).saveAndFlush(group);
        doReturn(true).when(groupRepository).existsById(groupDto.getId());
        doReturn(group).when(groupRepository).getOne(groupDto.getId());

        GroupDto groupDtoActually = groupService.update(groupDto);

        verify(groupRepository, times(1)).saveAndFlush(groupExpected);
        verify(groupRepository, times(1)).getOne(groupDto.getId());
        assertEquals(groupDto, groupDtoActually);
    }
}

package ua.com.foxminded.task.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;

@RunWith(JUnitPlatform.class)
public class GroupServiceImplTest {
    private GroupDao groupDao = mock(GroupDaoImpl.class);
    private GroupServiceImpl groupService = new GroupServiceImpl(groupDao);

    @Test
    void whenFindById_thenFindGroupAndConvertItToDto() {
        Group group = GroupModelRepository.getModel1();
        GroupDto groupDtoExpected = GroupDtoModelRepository.getModel1();
        doReturn(group).when(groupDao).findById(1);

        GroupDto groupDtoActually = groupService.findByIdDto(1);

        verify(groupDao, times(1)).findById(any(Integer.class));
        assertEquals(groupDtoExpected, groupDtoActually);
    }

    @Test
    void whenFindByAll_thenFindGroupsAndConvertItToDto() {
        List<Group> groups = GroupModelRepository.getModels1();
        List<GroupDto> groupDtosExpected = GroupDtoModelRepository.getModels1();
        doReturn(groups).when(groupDao).findAll();

        List<GroupDto> groupDtosActually = groupService.findAllDto();

        verify(groupDao, times(1)).findAll();
        assertEquals(groupDtosExpected, groupDtosActually);
    }

    @Test
    void whenCreate_thenInvocCreateDaoClass() {
        Group groupEmpty = GroupModelRepository.getEmptyModel();
        Group groupExpected = GroupModelRepository.getModel1();
        doReturn(groupExpected).when(groupDao).create(groupEmpty);

        Group groupActually = groupService.create(groupEmpty);

        verify(groupDao, times(1)).create(groupEmpty);
        assertEquals(groupExpected, groupActually);
    }

    @Test
    void whenUpdate_thenInvocUpdateDaoClass() {
        Group group = GroupModelRepository.getModel1();
        GroupDto groupDtoExpected = GroupDtoModelRepository.getModel1();
        doReturn(group).when(groupDao).update(group);

        GroupDto groupDtoActually = groupService.update(group);

        verify(groupDao, times(1)).update(group);
        assertEquals(groupDtoExpected, groupDtoActually);
    }
}

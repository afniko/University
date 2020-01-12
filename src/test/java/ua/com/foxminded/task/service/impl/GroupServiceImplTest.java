package ua.com.foxminded.task.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

import ua.com.foxminded.task.dao.GroupRepository;
import ua.com.foxminded.task.dao.exception.EntityAlreadyExistsException;
import ua.com.foxminded.task.dao.exception.EntityNotValidException;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.dto.GroupDtoModelRepository;

public class GroupServiceImplTest {

    @Mock
    private Logger logger;
    @Mock
    private GroupRepository groupRepository;
    @InjectMocks
    private GroupServiceImpl groupService;

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
    public void whenFindById_thenFindGroupAndConvertItToDto() {
        Group group = GroupModelRepository.getModel1();
        GroupDto groupDtoExpected = GroupDtoModelRepository.getModel1();
        doReturn(group).when(groupRepository).getOne(1);

        GroupDto groupDtoActually = groupService.findByIdDto(1);

        verify(groupRepository, times(1)).getOne(any(Integer.class));
        assertEquals(groupDtoExpected, groupDtoActually);
    }

    @Test
    public void whenFindByAll_thenFindGroupsAndConvertItToDto() {
        List<Group> groups = GroupModelRepository.getModels1();
        List<GroupDto> groupDtosExpected = GroupDtoModelRepository.getModels1();
        doReturn(groups).when(groupRepository).findAll();

        List<GroupDto> groupDtosActually = groupService.findAllDto();

        verify(groupRepository, times(1)).findAll();
        assertEquals(groupDtosExpected, groupDtosActually);
    }

    @Test
    public void whenCreate_thenInvocCreateDaoClass() {
        GroupDto groupDto = GroupDtoModelRepository.getModel1();
        Group groupInput = GroupModelRepository.getModel1();
        Group groupExpected = GroupModelRepository.getModelWithId();

        doReturn(groupExpected).when(groupRepository).saveAndFlush(groupInput);

        GroupDto groupDtoActually = groupService.create(groupDto);

        verify(groupRepository, times(1)).saveAndFlush(groupInput);
        assertEquals(groupDto, groupDtoActually);
    }

    @Test
    public void whenUpdate_thenInvocUpdateDaoClass() {
        GroupDto groupDto = GroupDtoModelRepository.getModelWithId();
        Group group = new Group();
        group.setId(groupDto.getId());
        group.setTitle(groupDto.getTitle());
        group.setYearEntry(groupDto.getYearEntry());
        doReturn(group).when(groupRepository).saveAndFlush(group);
        doReturn(true).when(groupRepository).existsById(groupDto.getId());
        doReturn(group).when(groupRepository).getOne(groupDto.getId());

        GroupDto groupDtoActually = groupService.update(groupDto);

        verify(groupRepository, times(1)).saveAndFlush(group);
        verify(groupRepository, times(1)).getOne(groupDto.getId());
        assertEquals(groupDto, groupDtoActually);
    }
    
    @Test
    public void whenFindByTitle_thenInvokeMethod() {
        String title = "title";
        groupService.findByTitle(title);
        
        verify(groupRepository, times(1)).findByTitle(title);
    }
    
    @Test
    public void whenNotFindById_thenThrowException() {
        int id = 1;
        doThrow(EntityNotFoundException.class).when(groupRepository).getOne(id);

        assertThatThrownBy(() -> groupService.findByIdDto(id))
             .isInstanceOf(NoEntityFoundException.class)
             .hasMessage("findByIdDto() id: %s", id);
    }
    
    @Test
    public void whenCreateRecordEntityWithId_thenThrowException() {
        GroupDto group = new GroupDto();
        group.setId(1);

        assertThatThrownBy(() -> groupService.create(group))
             .isInstanceOf(EntityAlreadyExistsException.class)
             .hasMessage("create() groupDto: %s", group);
    }
    
    @Test
    public void whenCreateRecordWithNotValidEntity_thenThrowException() {
        GroupDto groupDto = GroupDtoModelRepository.getModel1();
        Group group = GroupModelRepository.getModel1();
        doThrow(DataIntegrityViolationException.class).when(groupRepository).saveAndFlush(group);

        assertThatThrownBy(() -> groupService.create(groupDto))
             .isInstanceOf(EntityNotValidException.class)
             .hasMessageContaining("create() group: " + group);
    }
    
    @Test
    public void whenUpdateRecordEntityWithId_thenThrowException() {
        GroupDto group = new GroupDto();
        group.setId(1);
        doReturn(false).when(groupRepository).existsById(group.getId());

        assertThatThrownBy(() -> groupService.update(group))
             .isInstanceOf(NoEntityFoundException.class)
             .hasMessage("Group not exist!");
    }

    @Test
    public void whenUpdateRecordWithNotValidEntity_thenThrowException() {
        GroupDto groupDto = GroupDtoModelRepository.getModel1();
        Group group = GroupModelRepository.getModel1();
        doReturn(true).when(groupRepository).existsById(group.getId());
        doThrow(DataIntegrityViolationException.class).when(groupRepository).saveAndFlush(group);

        assertThatThrownBy(() -> groupService.update(groupDto))
             .isInstanceOf(EntityNotValidException.class)
             .hasMessageContaining("update() group: " + group);
    }
}

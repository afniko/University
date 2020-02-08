package ua.com.foxminded.task.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.task.domain.TimetableItem;

@Repository
@Transactional
public interface TimetableItemRepository extends JpaRepository<TimetableItem, Integer> {

    public boolean existsById(Integer id);
    
}

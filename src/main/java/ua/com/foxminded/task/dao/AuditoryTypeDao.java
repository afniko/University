package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.AuditoryType;

public interface AuditoryTypeDao {

    public boolean create(AuditoryType auditoryType);

    public AuditoryType findById(int id);

    public List<AuditoryType> findAll();

    public AuditoryType findByType(String type);
}

package spring.security.jwt.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.jwt.bean.Project;
import spring.security.jwt.exception.ServiceException;

import java.beans.Transient;
import java.util.List;

@Service
public interface ProjectService
{
    boolean existsByName(String name)throws ServiceException;

    @Transactional
    void deleteByName(String name)throws ServiceException;

    @Transactional
    void setProjectById(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("description") String description) throws ServiceException;

    Project getByName(String name)throws ServiceException;

    List<Project> findAll()throws ServiceException;

    Project create(Project project)throws ServiceException;

    Project getById(long id)throws ServiceException;
}

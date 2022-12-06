package spring.security.jwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.security.jwt.bean.Project;
import spring.security.jwt.exception.RepositoryException;
import spring.security.jwt.exception.ServiceException;
import spring.security.jwt.repository.ProjectRepository;
import spring.security.jwt.service.ProjectService;

import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService
{

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public boolean existsByName(String name) throws ServiceException {
        try {
            return projectRepository.existsByName(name);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByName(String name) throws ServiceException {
        try {
            projectRepository.deleteByName(name);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setProjectById(Long id, String name, String description) throws ServiceException {
        try {
            projectRepository.setProjectById(id, name, description);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Project getByName(String name) throws ServiceException {
        try {
            return projectRepository.getByName(name);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project getById(long id) throws ServiceException {
        try {
            return projectRepository.getById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}

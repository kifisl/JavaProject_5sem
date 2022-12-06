package spring.security.jwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.security.jwt.bean.UserProject;
import spring.security.jwt.exception.RepositoryException;
import spring.security.jwt.exception.ServiceException;
import spring.security.jwt.repository.UserProjectRepository;
import spring.security.jwt.service.UserProjectService;

import java.util.List;

@Service
public class UserProjectServiceImpl implements UserProjectService {
    @Autowired
    private UserProjectRepository userProjectRepository;

    @Override
    public boolean existsByUserIdAndProjectId(Long user_id, long project_id) throws ServiceException {
        try
        {
            return userProjectRepository.existsByUserIdAndProjectId(user_id, project_id);
        }
        catch (RepositoryException e)
        {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean existsByProjectId(long project_id) throws ServiceException {
        try {
            return userProjectRepository.existsByProjectId(project_id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserProject> getAllByUserLogin(String user_login) throws ServiceException {
        try {
            return userProjectRepository.getAllByUserLogin(user_login);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserProject create(UserProject userProject) {
        return userProjectRepository.save(userProject);
    }

    @Override
    public List<UserProject> getAll() {
        return userProjectRepository.findAll();
    }

    @Override
    public void setUserProjectById(Long id, boolean isDenide, boolean isSet) throws ServiceException {
        try {
            userProjectRepository.setUserProjectById(id, isDenide, isSet);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

}

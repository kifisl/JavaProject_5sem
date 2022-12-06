package spring.security.jwt.service;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.jwt.bean.UserProject;
import spring.security.jwt.exception.ServiceException;

import java.util.List;

@Service
public interface UserProjectService
{
    boolean existsByUserIdAndProjectId(Long user_id, long project_id)throws ServiceException;
    boolean existsByProjectId(long project_id)throws ServiceException;
    List<UserProject> getAllByUserLogin(String user_login)throws ServiceException;
    UserProject create(UserProject userProject)throws ServiceException;
    List<UserProject> getAll()throws ServiceException;

    @Transactional
    void setUserProjectById(
            @Param("id") Long id,
            @Param("isDenide") boolean isDenide,
            @Param("isSet") boolean isSet) throws ServiceException;

}

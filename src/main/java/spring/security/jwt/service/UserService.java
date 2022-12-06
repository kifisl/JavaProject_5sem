package spring.security.jwt.service;

import org.springframework.stereotype.Service;
import spring.security.jwt.bean.User;
import spring.security.jwt.exception.RepositoryException;
import spring.security.jwt.exception.ServiceException;

import java.util.List;

@Service
public interface UserService {
    User saveUser(User user) throws ServiceException, RepositoryException;

    User findByLogin(String login) throws ServiceException, RepositoryException;

    List<User> findAll()throws ServiceException, RepositoryException;

    User findByLoginAndPassword(String login, String password) throws ServiceException, RepositoryException;

    boolean existsUserByLogin(String login)throws ServiceException;

    boolean existsUserByLoginAndPassword(String login, String password)throws ServiceException;

    User findById(long id)throws ServiceException;
}

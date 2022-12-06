package spring.security.jwt.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.security.jwt.bean.User;
import spring.security.jwt.bean.UserRole;
import spring.security.jwt.bean.dto.Role;
import spring.security.jwt.exception.RepositoryException;
import spring.security.jwt.exception.ServiceException;
import spring.security.jwt.repository.UserRepository;
import spring.security.jwt.repository.UserRoleRepository;
import spring.security.jwt.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailSenderImpl mailSender;

    public User saveUser(User user) throws ServiceException {
        try {
            UserRole userRole1 = userRoleRepository.findByName(Role.ROLE_USER);
            user.setUserRole(userRole1);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationCode(UUID.randomUUID().toString());



            user.setActive(false);
            String message = String.format(
                    "Hello %s!\n" +
                            "activate your code , need to visit http://localhost:8080/activate/%s",
                    user.getLogin(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
            return userRepository.save(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public User findByLogin(String login) throws ServiceException {
        try {
            return userRepository.findByLogin(login);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public List<User> findAll() throws ServiceException {
        return userRepository.findAll();
    }

    public User findByLoginAndPassword(String login, String password) throws ServiceException {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public boolean existsUserByLogin(String login) throws ServiceException {
        try {
            return userRepository.existsUserByLogin(login);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public boolean existsUserByLoginAndPassword(String login, String password) throws ServiceException {
        return findByLoginAndPassword(login, password) != null;
    }

    @Override
    public User findById(long id) throws ServiceException {
        try {
            return userRepository.getById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }


    public boolean activateUser(String code) throws ServiceException {
        try {
            User user = userRepository.findByActivationCode(code);

            if (user == null) {
                return false;
            }
            user.setActivationCode(null);
            user.setActive(true);
            userRepository.save(user);

            return true;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}

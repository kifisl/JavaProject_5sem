package spring.security.jwt.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.security.jwt.bean.User;
import spring.security.jwt.exception.RepositoryException;
import spring.security.jwt.exception.ServiceException;
import spring.security.jwt.repository.UserRepository;
import spring.security.jwt.service.UserService;
import spring.security.jwt.service.impl.UserServiceImpl;

@Aspect
@Component
public class UserServiceAspect
{
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(UserServiceAspect.class);

    @Pointcut("execution(public * spring.security.jwt.service.UserService.findById(..))")
    public void callAtUserServiceFindById() {
    }

    @Pointcut("execution(public * spring.security.jwt.service.UserService.existsUserByLogin(..))")
    public void callAtUserServiceExistsUserByLogin() {
    }

    @Before("callAtUserServiceFindById()")
    public void beforeCallAtUserServiceSaveUser(JoinPoint jp) throws ServiceException, RepositoryException {
        Object[] objects = jp.getArgs();
        long id = (long) objects[0];
        logger.info("user id = " + id);
        System.out.println("user id = " + id);
    }

    @Before("callAtUserServiceExistsUserByLogin()")
    public void beforeCallAtUserServiceExistsUserByLogin(JoinPoint jp) throws ServiceException, RepositoryException
    {
        Object[] objects = jp.getArgs();
        String login = (String) objects[0];
        User result = userService.findByLogin(login);
        logger.info("is user exist = " + result);
        System.out.println("is user exist = " + result);
    }

    @After("callAtUserServiceExistsUserByLogin()")
    public void afterCallAtUserServiceExistsUserByLogin(JoinPoint jp) {
        logger.info("After callAtUserServiceExistsUserByLogin");
        System.out.println("After callAtUserServiceExistsUserByLogin");
    }

    @After("callAtUserServiceFindById()")
    public void afterCallAtUserServiceSaveUser(JoinPoint jp)
    {
        logger.info("After callAtUserServiceSaveUser");
        System.out.println("After callAtUserServiceSaveUser");
    }
}

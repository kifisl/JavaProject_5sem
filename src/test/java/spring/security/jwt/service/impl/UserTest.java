package spring.security.jwt.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.security.jwt.bean.User;
import spring.security.jwt.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserTest {
    @Autowired
    private UserServiceImpl userService;
    @Test
    void existsUserByLogin()
    {
        try {
            Assert.assertFalse(userService.existsUserByLogin("1234"));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    void existsUserByLoginAndPassword() {
        try {
            Assert.assertTrue(userService.existsUserByLoginAndPassword("Admin","12345"));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isActive()
    {
            Assert.assertFalse(new User().isActive());
    }

    @Test
    void activate()
    {
        try
        {
            Assert.assertFalse(userService.activateUser("some code"));
        }
        catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
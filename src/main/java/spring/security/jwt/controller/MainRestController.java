package spring.security.jwt.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.security.jwt.config.jwt.JwtProvider;
import spring.security.jwt.bean.User;
import spring.security.jwt.controller.dto.AuthRequest;
import spring.security.jwt.controller.dto.AuthResponse;
import spring.security.jwt.controller.dto.RegistrationRequest;
import spring.security.jwt.controller.dto.UserResponse;
import spring.security.jwt.exception.ControllerException;
import spring.security.jwt.exception.ServiceException;
import spring.security.jwt.service.impl.UserServiceImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;


@RestController
public class MainRestController
{
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private JwtProvider jwtProvider;

    private static final Logger logger = Logger.getLogger(MainRestController.class);


    @PostMapping("/users")
    public List<User> getUsers() throws ControllerException {
        try
        {
            logger.debug("getting all users");
            return userServiceImpl.findAll();
        }
        catch (ServiceException e)
        {
            logger.error("error get all users");
            throw new ControllerException("getUsers", e);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) throws ControllerException {
        try {
            logger.debug("try to register user");

            if (!userServiceImpl.existsUserByLogin(registrationRequest.getLogin())) {
                User u = new User();
                u.setPassword(registrationRequest.getPassword());
                u.setLogin(registrationRequest.getLogin());
                u.setEmail(registrationRequest.getEmail());
                userServiceImpl.saveUser(u);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FOUND);
            }
        } catch (ServiceException e) {
            logger.error("error register user");

            throw new ControllerException("registerUser", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequest request) throws ControllerException {
        try {
            logger.debug("try to login user");

            User user = userServiceImpl.findByLoginAndPassword(request.getLogin(), request.getPassword());
            if (user != null && user.isActive()) {
               String token = jwtProvider.generateToken(user.getLogin());
               AuthResponse response = new AuthResponse(token, user.getUserRole().getName());
               return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new ControllerException("not such user");
            }
        } catch (ServiceException e) {
            logger.error("error login");

            throw new ControllerException("auth", e);
        }
    }

    @PostMapping("/authorized")
    public ResponseEntity<?> isAuthorized() throws ControllerException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getUser")
    public UserResponse getUser(@RequestHeader(name = "Authorisation") String jwt) throws ControllerException {
        try {
            logger.debug("get user by token");

            String userName = jwtProvider.getLoginFromToken(jwt.substring(7));
            User user = userServiceImpl.findByLogin(userName);


            return new UserResponse(user.getId(), user.getLogin(), user.getUserRole().getName());
        } catch (ServiceException e) {
            logger.error("error get user");

            throw new ControllerException("getUser", e);
        }
    }
}

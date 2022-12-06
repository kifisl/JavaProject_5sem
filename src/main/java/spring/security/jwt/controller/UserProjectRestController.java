package spring.security.jwt.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.security.jwt.bean.UserProject;
import spring.security.jwt.controller.dto.IdRequest;
import spring.security.jwt.controller.dto.UserProjectNoIdRequest;
import spring.security.jwt.controller.dto.UserProjectNoUserNoProductRequest;
import spring.security.jwt.exception.ControllerException;
import spring.security.jwt.exception.ServiceException;
import spring.security.jwt.repository.UserProjectRepository;
import spring.security.jwt.service.UserProjectService;


@RestController
public class UserProjectRestController
{
    @Autowired
    private UserProjectService userProjectService;

    @Autowired
    private UserProjectRepository userProjectRepository;

    private static final Logger logger = Logger.getLogger(MainController.class);


    @PostMapping("/user/createUserProject")
    public ResponseEntity<?> createUserProject(@RequestBody UserProjectNoIdRequest userProjectNoIdRequest) throws ControllerException
    {
        try
        {
            UserProject userProject = new UserProject(userProjectNoIdRequest.getUser(), userProjectNoIdRequest.getProject());
            userProjectService.create(userProject);
            return new ResponseEntity<>(userProject, HttpStatus.OK);
        }
        catch (ServiceException e)
        {
            logger.error("createUserProject");
            throw new ControllerException(e);
        }
    }

    @PostMapping("/user/isSubscribed")
    public ResponseEntity<?> isSubscribed(@RequestBody UserProjectNoIdRequest userProjectNoIdRequest) throws ControllerException
    {
        try {
            if (!userProjectService.existsByUserIdAndProjectId(userProjectNoIdRequest.getUser().getId(), userProjectNoIdRequest.getProject().getId())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        catch (ServiceException e)
        {
            logger.error("isSubscribed");
            throw new ControllerException(e);
        }
    }

    @PostMapping("/admin/isUserProjectExistByProjectId")
    public ResponseEntity<?> isUserProjectExistByProjectId(@RequestBody IdRequest idRequest) throws ControllerException
    {
        try
        {
            if (!userProjectService.existsByProjectId(idRequest.getId()))
            {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        catch (ServiceException e)
        {
            logger.error("isUserProjectExistByProjectId");
            throw new ControllerException(e);
        }
    }

    @GetMapping("/user/getUserProductListByUserName/{data}")
    public ResponseEntity<?> getUserProductListByUserName(@PathVariable(name = "data") String data) throws ControllerException
    {
        try
        {
            return new ResponseEntity<>(userProjectService.getAllByUserLogin(data), HttpStatus.OK);
        }
        catch (ServiceException e)
        {
            logger.error("getUserProductListByUserName");
            throw new ControllerException(e);
        }
    }

    @GetMapping("/admin/getAllUserProduct")
    public ResponseEntity<?> getAllUserProduct() throws ControllerException
    {

        try
        {
            return new ResponseEntity<>(userProjectService.getAll(), HttpStatus.OK);
        }
        catch (ServiceException e)
        {
            logger.error("getAllUserProduct");
            throw new ControllerException(e);
        }
    }

    @PutMapping("/user/updateUserProduct")
    public ResponseEntity<?> updateUserProduct(@RequestBody UserProjectNoUserNoProductRequest request) throws ControllerException
    {
        try
        {
            userProjectService.setUserProjectById(request.getId(), request.isDenide(), request.isSet());
            return new ResponseEntity<>(userProjectRepository.getById(request.getId()), HttpStatus.OK);
        }
        catch (ServiceException e)
        {
            logger.error("updateUserProduct");
            throw new ControllerException(e);
        }
    }
}

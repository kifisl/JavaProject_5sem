package spring.security.jwt.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.security.jwt.exception.ControllerException;
import spring.security.jwt.exception.ServiceException;
import spring.security.jwt.service.impl.UserServiceImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Controller
public class MainController
{

    private static final Logger logger = Logger.getLogger(MainController.class);


    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String home()
    {
        return "index";
    }

    @GetMapping("/registration")
    public String registration()
    {
        return "registration";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/projects")
    public String projects()
    {
        return "projects";
    }

    @GetMapping("/allowprojects")
    public String allowprojects()
    {
        return "redirect:/projects";
    }

    @GetMapping("/userprojects")
    public String userprojects() {return "userprojects";}

    @GetMapping("/allowuserprojects")
    public String allowuserprojects() {return "redirect:/userprojects";}

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code) throws ControllerException
    {
        try
        {
            boolean isActivated = userService.activateUser(code);

            System.out.println(isActivated);
            return "redirect:/login";
        }
        catch (ServiceException e)
        {
            logger.error("error activate user");

            throw new ControllerException("activate", e);
        }
    }
}

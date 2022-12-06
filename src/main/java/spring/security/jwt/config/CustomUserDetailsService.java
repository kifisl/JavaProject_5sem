package spring.security.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import spring.security.jwt.bean.User;
import spring.security.jwt.exception.ControllerException;
import spring.security.jwt.exception.ServiceException;
import spring.security.jwt.service.impl.UserServiceImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userServiceImpl.findByLogin(username);
            return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
        } catch (ServiceException e) {
            throw new UsernameNotFoundException("user not found");
        }
    }
}

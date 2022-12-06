package spring.security.jwt.service;

import org.springframework.stereotype.Service;
import spring.security.jwt.bean.UserRole;
import spring.security.jwt.bean.dto.Role;
import spring.security.jwt.exception.RepositoryException;
import spring.security.jwt.exception.ServiceException;

@Service
public interface UserRoleService {
    UserRole findByName(Role name)throws ServiceException, RepositoryException;
}

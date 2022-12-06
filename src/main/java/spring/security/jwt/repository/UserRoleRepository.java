package spring.security.jwt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.security.jwt.bean.UserRole;
import spring.security.jwt.bean.dto.Role;
import spring.security.jwt.exception.RepositoryException;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    UserRole findByName(Role name)throws RepositoryException;;
}

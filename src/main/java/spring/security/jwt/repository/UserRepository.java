package spring.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.security.jwt.bean.User;
import spring.security.jwt.exception.RepositoryException;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login)throws RepositoryException;
    List<User> findAll();
    boolean existsUserByLogin(String login)throws RepositoryException;
    boolean existsUserByLoginAndPassword(String login, String password)throws RepositoryException;
    User getById(Long id)throws RepositoryException;

    User findByActivationCode(String code)throws RepositoryException;
}

package spring.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.security.jwt.bean.Project;
import spring.security.jwt.exception.RepositoryException;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    boolean existsByName(String name) throws RepositoryException;
    void deleteByName(String name)throws RepositoryException;
    @Modifying
    @Query("update Project p set p.name =:name, p.description=:description where p.id =:id ")
    void setProjectById(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("description") String description)throws RepositoryException;
    Project getByName(String name)throws RepositoryException;
    List<Project> findAll();
    Project getById(long id)throws RepositoryException;

}

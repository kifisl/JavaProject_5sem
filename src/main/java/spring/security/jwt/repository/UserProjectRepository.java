package spring.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.security.jwt.bean.Project;
import spring.security.jwt.bean.UserProject;
import spring.security.jwt.exception.RepositoryException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject,Long>
{
    boolean existsByUserIdAndProjectId(Long user_id, long project_id)throws RepositoryException;
    boolean existsByProjectId(long project_id)throws RepositoryException;
    List<UserProject> getAllByUserLogin(String user_login)throws RepositoryException;

    @Modifying
    @Query("update UserProject up set up.isDenide =:isDenide,up.isSet =:isSet where up.id =:id ")
    void setUserProjectById(
            @Param("id") Long id,
            @Param("isDenide") boolean isDenide,
            @Param("isSet") boolean isSet
    )throws RepositoryException;

    UserProject getById(long id);
}

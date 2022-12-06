package spring.security.jwt.bean;

import lombok.Data;
import spring.security.jwt.bean.dto.Role;

import javax.persistence.*;

@Entity
@Table(name = "role_table")
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Role name;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getName() {
        return name;
    }

    public void setName(Role name) {
        this.name = name;
    }
}

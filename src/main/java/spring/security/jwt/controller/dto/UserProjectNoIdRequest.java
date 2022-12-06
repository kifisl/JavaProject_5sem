package spring.security.jwt.controller.dto;

import spring.security.jwt.bean.Project;
import spring.security.jwt.bean.User;

public class UserProjectNoIdRequest {
    private User user;
    private Project project;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

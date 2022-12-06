package spring.security.jwt.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProjectNoIdRequest {
    @Size(min = 4 , max = 16, message = "name from 4 to 16")
    @NotNull(message = "Can not be null")
    private String name;

    @Size(min = 4, message = "description from 4 to inf")
    @NotNull(message = "Can not be null")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

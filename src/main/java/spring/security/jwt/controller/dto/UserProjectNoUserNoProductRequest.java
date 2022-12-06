package spring.security.jwt.controller.dto;

public class UserProjectNoUserNoProductRequest
{
    private long id;
    private boolean denide;

    private boolean set;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDenide() {
        return denide;
    }

    public void setDenide(boolean denide) {
        this.denide = denide;
    }

    public boolean isSet() {
        return set;
    }

    public void setSet(boolean set) {
        this.set = set;
    }
}

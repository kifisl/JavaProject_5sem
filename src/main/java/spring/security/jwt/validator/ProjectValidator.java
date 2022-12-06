package spring.security.jwt.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.security.jwt.bean.Project;
import spring.security.jwt.bean.User;

@Component
public class ProjectValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors)
    {
        Project pr =(Project)o;
        if(pr.getId()<0)
        {
            errors.rejectValue("id","negative value");
        }
    }
}

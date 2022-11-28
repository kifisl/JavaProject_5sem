package com.example.nastya.service;

import com.example.nastya.entity.Roles;
import com.example.nastya.entity.Users;
import com.example.nastya.repository.RolesRepository;
import com.example.nastya.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSenderService mailSenderService;

    public boolean addUser(Users user) {
        if(findByLogin(user.getLogin()) != null) {
            return false;
        }
        else {
            Roles role = rolesService.findByName("ROLE_USER");
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationCode(UUID.randomUUID().toString());

            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sport Events application. Please, visit next link: http://localhost:3000/activate/%s",
                    user.getLogin(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation code", message);

            usersRepository.save(user);
            return true;
        }
    }

    public Users findByLogin(String login) {
        return usersRepository.findByLogin(login);
    }

    public Users findByLoginAndPassword(String login, String password) {
        Users user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
            else return null;
        }
        else return null;
    }

    public void delete(long id) {
        usersRepository.deleteById(id);
    }

    public Users editUser(Users user) {
        return usersRepository.saveAndFlush(user);
    }

    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    public Optional<Users> getById(long id) {
        return usersRepository.findById(id);
    }

    public boolean acrivateUser(String code) {
        Users user = usersRepository.findByActivationCode(code);

        if(user == null){
            return false;
        }

        user.setActivationCode(null);

        usersRepository.save(user);

        return true;
    }
}

package spring.security.jwt.service;

import org.springframework.stereotype.Service;
import spring.security.jwt.exception.ServiceException;

@Service
public interface MailSender {
    void send(String emailTo, String subject, String message)throws ServiceException;
}

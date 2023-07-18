package com.smarthome.hometasker.services.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smarthome.hometasker.dao.models.user.AuthenticationToken;
import com.smarthome.hometasker.dao.models.user.UserRegistration;
import com.smarthome.hometasker.dao.repository.user.AuthenticationTokenRepository;
import com.smarthome.hometasker.dao.repository.user.UserRegistrationRepository;
import com.smarthome.hometasker.dto.user.UserRegistrationDTO;
import com.smarthome.hometasker.mapper.user.UserRegistrationMapper;
import com.smarthome.hometasker.services.email.EmailService;
import com.smarthome.hometasker.utils.AuthenticationTokenGenerator;

import jakarta.mail.MessagingException;

@Service
public class UserRegistrationService {
    
    private final UserRegistrationRepository userRegistrationRepository;
    private final AuthenticationTokenRepository authenticationTokenRepository;

    EmailService emailService;

    @Autowired
    public UserRegistrationService(UserRegistrationRepository userRegistrationRepository, EmailService emailService, AuthenticationTokenRepository authenticationTokenRepository) {
        this.userRegistrationRepository = userRegistrationRepository;
        this.authenticationTokenRepository = authenticationTokenRepository;
        this.emailService = emailService;
    }

    public List<UserRegistration> getAllUsers() {
        return userRegistrationRepository.findAll();
    }

    public void saveUserRegistration(UserRegistrationDTO userRegistrationPayload) {
        UserRegistration userRegistrationData = UserRegistrationMapper.mapDtoToEntity(userRegistrationPayload);
        // String token = AuthenticationTokenGenerator.generateToken();
        AuthenticationToken tokenEntity = new AuthenticationToken();
        tokenEntity.setToken(AuthenticationTokenGenerator.generateToken());
        tokenEntity.setExpirationDate(AuthenticationTokenGenerator.getTokenExpiration());
        tokenEntity.setUser(userRegistrationData);

        userRegistrationData.getTokens().add(tokenEntity);

        //set the token for email verification
        // userRegistrationData.setToken(token);

        this.userRegistrationRepository.save(userRegistrationData);
        // try {
        //     emailService.sendVerificationEmail(userRegistrationData, token);
        // } catch (MessagingException e) {
        //     // Handle the exception
        // }
    }

    public boolean verifyUser(String verificationToken) {
        AuthenticationToken token = this.authenticationTokenRepository.findByToken(verificationToken);

        //if user token is expired 
        if (AuthenticationTokenGenerator.isTokenExpired(token.getExpirationDate())) {
            return false;
        }

        //Update verification flag for User
        UserRegistration user = token.getUser();
        user.setVerified(true);
        this.userRegistrationRepository.save(user);
        return true;
    }
}

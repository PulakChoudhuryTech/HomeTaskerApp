package com.smarthome.hometasker.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarthome.hometasker.dao.models.user.UserRegistration;
import com.smarthome.hometasker.dto.common.CommonResponseDTO;
import com.smarthome.hometasker.dto.user.UserRegistrationDTO;
import com.smarthome.hometasker.services.user.UserRegistrationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserRegistrationController {
    
    UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping(value = "/test")
    public String getTest() {
        return "hello hometasker";
    }


    @GetMapping(value = "/all")
    public List<UserRegistration> getAllUser() {
        return this.userRegistrationService.getAllUsers();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<CommonResponseDTO> saveUserRegistation(HttpServletRequest request, @RequestBody UserRegistrationDTO registrationData) {
        this.userRegistrationService.saveUserRegistration(registrationData);
        CommonResponseDTO response = new CommonResponseDTO();
        response.setMessage("User registered successfully");
        return new ResponseEntity<CommonResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<CommonResponseDTO> verifyUser(@RequestParam("token") String verificationToken) {
        boolean isVerified  = this.userRegistrationService.verifyUser(verificationToken);
        CommonResponseDTO response = new CommonResponseDTO();
        response.setMessage(isVerified ? "Success" : "Session Expired");
        return new ResponseEntity<CommonResponseDTO>(response, HttpStatus.OK);
    }
}

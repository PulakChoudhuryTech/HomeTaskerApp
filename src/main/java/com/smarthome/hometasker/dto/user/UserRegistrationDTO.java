package com.smarthome.hometasker.dto.user;

import lombok.Data;

@Data
public class UserRegistrationDTO {

    public String email;
    public String userId;
    public String password;
    public String first_name;
    public String last_name;
}

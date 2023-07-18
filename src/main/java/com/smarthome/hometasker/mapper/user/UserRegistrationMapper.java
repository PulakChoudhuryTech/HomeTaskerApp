package com.smarthome.hometasker.mapper.user;
import com.smarthome.hometasker.dao.models.user.UserRegistration;
import com.smarthome.hometasker.dto.user.UserRegistrationDTO;


public class UserRegistrationMapper {
    
    public static UserRegistration mapDtoToEntity(UserRegistrationDTO dto) {
        UserRegistration entity = new UserRegistration();
        entity.setEmail(dto.getEmail());
        entity.setFirst_name(dto.getFirst_name());
        entity.setLast_name(dto.getLast_name());
        entity.setUserId(dto.getUserId());
        entity.setPassword(dto.getPassword());
        entity.setVerified(false);
        return entity;
    }
}

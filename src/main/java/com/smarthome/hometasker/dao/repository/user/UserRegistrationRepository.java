package com.smarthome.hometasker.dao.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smarthome.hometasker.dao.models.user.UserRegistration;
import com.smarthome.hometasker.dto.user.UserRegistrationDTO;


@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, String>{
    
    // UserRegistration findByToken(String token);

    // @Query("SELECT DISTINCT u FROM UserRegistration u JOIN FETCH u.tokens")
    // List<UserRegistration> findAllUsersWithTokens();

    // @Query("SELECT new com.example.UserDTO(u.userId, u.first_name, t.id) FROM UserRegistration u JOIN u.tokens t")
    // List<UserRegistrationDTO> findAllUsersWithTokenIds();
}

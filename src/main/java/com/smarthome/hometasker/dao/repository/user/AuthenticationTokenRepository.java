package com.smarthome.hometasker.dao.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarthome.hometasker.dao.models.user.AuthenticationToken;

@Repository
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken, Long>{
    
    AuthenticationToken findByToken(String token);
}

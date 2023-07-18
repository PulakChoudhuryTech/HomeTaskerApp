package com.smarthome.hometasker.dao.models.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity(name="user_registrations")
public class UserRegistration {
    
    public String email;
    @Id
    public String userId;
    public String password;
    public String first_name;
    public String last_name;
    @Column(name="is_verified")
    public boolean verified;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<AuthenticationToken> tokens = new ArrayList();
}

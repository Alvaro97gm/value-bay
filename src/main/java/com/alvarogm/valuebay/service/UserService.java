package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.domain.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUser(UserDTO userDTO){

    }
}

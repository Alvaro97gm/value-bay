package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.domain.dto.UserDTO;
import com.alvarogm.valuebay.domain.model.User;
import com.alvarogm.valuebay.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public List<User> findAll(){
        return userRepository.findAll();
    }


    public User findByUserId(Integer userId){
        return userRepository.findByUserId(userId);
    }


    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public void registerNewUser(UserDTO userDTO){

        if(isEmailAvailable(userDTO.getEmail())){
            User user = new User(
                    userDTO.getUserId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail()
            );
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);
        }else{
            System.out.println("[USERS] - El email: " + userDTO.getEmail() + " ya está en uso.");
        }
    }


    public void deleteUser(Integer userId){
        userRepository.delete(findByUserId(userId));
    }


    private boolean isEmailAvailable(String email){
        return userRepository.findByEmail(email) == null;
    }
}

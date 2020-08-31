package com.alvarogm.valuebay.service;

import com.alvarogm.valuebay.persistence.domain.dto.UserDTO;
import com.alvarogm.valuebay.persistence.domain.model.User;
import com.alvarogm.valuebay.persistence.repository.UserRepository;
import com.alvarogm.valuebay.security.UserRole;
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


    public void signIn(UserDTO userDTO){

        userDTO.setUserId(CommonService.generate5DigitsId());

        if(isEmailAvailable(userDTO.getEmail())){
            User user = new User(
                userDTO.getUserId(), userDTO.getFirstName(),
                userDTO.getLastName(), userDTO.getEmail(), UserRole.USER.name()
            );
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);
            System.out.println("[USERS] - Usuario: " + userDTO.getUserId() + " registrado en el sistema.");
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

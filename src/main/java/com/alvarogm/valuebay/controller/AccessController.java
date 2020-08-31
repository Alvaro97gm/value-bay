package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.persistence.domain.dto.UserDTO;
import com.alvarogm.valuebay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class AccessController {

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<ResponseStatus> registerNewUser(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "password") String password
    ){

        try{
            if(!userService.signIn(new UserDTO(null, email, firstName, lastName, password, "USER")))
                throw new Exception("[USERS] - El email: "+ email + " ya está en uso.");
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}

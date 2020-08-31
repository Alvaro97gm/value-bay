package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.persistence.domain.dto.UserDTO;
import com.alvarogm.valuebay.persistence.domain.mapper.UserMapper;
import com.alvarogm.valuebay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Transactional
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/get/id/{userId}")
    public ResponseEntity<UserDTO> findByUserId(@PathVariable(name = "userId") Integer userId){

        UserDTO result = userMapper.userToUserDTO(userService.findByUserId(userId));
        if(result == null) {
            System.out.println("[USERS] - No se ha encontrado al usuario " + userId + ".");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/get/email")
    public ResponseEntity<UserDTO> findByEmail(@RequestParam(name = "email") String email){

        UserDTO result = userMapper.userToUserDTO(userService.findByEmail(email));
        if(result == null){
            System.out.println("[USERS] - No se ha encontrado al usuario "+ email +".");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/all")
    public ResponseEntity<List<UserDTO>> findAll(){

        List<UserDTO> result =  userMapper.usersToUserDTOs(userService.findAll());
        if(result.isEmpty()){
            System.out.println("[USERS] - No se ha encontrado ningún usuario.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseStatus> deleteUser(@PathVariable(name = "userId") Integer userId){
        try{
            userService.deleteUser(userId);
            System.out.println("[USERS] - Usuario: " + userId + " eliminado del sistema.");
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}

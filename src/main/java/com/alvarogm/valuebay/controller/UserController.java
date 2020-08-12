package com.alvarogm.valuebay.controller;

import com.alvarogm.valuebay.domain.dto.UserDTO;
import com.alvarogm.valuebay.domain.mapper.UserMapper;
import com.alvarogm.valuebay.service.CommonService;
import com.alvarogm.valuebay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Transactional
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;


    @GetMapping("/get/id/{userId}")
    public ResponseEntity<UserDTO> findByUserId(@PathVariable(name = "userId") Integer userId){

        UserDTO result = userMapper.userToUserDTO(userService.findByUserId(userId));
        if(result == null) {
            System.out.println("[USERS] - No se ha encontrado al usuario " + userId + ".");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(result);
    }


    @GetMapping("/get/email")
    public ResponseEntity<UserDTO> findByEmail(@RequestParam(name = "email") String email){

        UserDTO result = userMapper.userToUserDTO(userService.findByEmail(email));
        if(result == null){
            System.out.println("[USERS] - No se ha encontrado al usuario "+ email +".");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(result);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<UserDTO>> findAll(){

        List<UserDTO> result =  userMapper.usersToUserDTOs(userService.findAll());
        if(result.isEmpty()){
            System.out.println("[USERS] - No se ha encontrado ningún usuario.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(result);
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseStatus> registerNewUser(
        @RequestParam(name = "email") String email,
        @RequestParam(name = "firstName") String firstName,
        @RequestParam(name = "lastName") String lastName,
        @RequestParam(name = "password") String password
    ){

        Integer newUserId = CommonService.generate5DigitsId();
        try{
            userService.registerNewUser(
                new UserDTO(newUserId, email, firstName, lastName, password)
            );
            System.out.println("[USERS] - Usuario: " + newUserId + " registrado en el sistema.");
            return ResponseEntity.ok().build();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseStatus> deleteUser(@PathVariable(name = "userId") Integer userId){
        try{
            userService.deleteUser(userId);
            System.out.println("[USERS] - Usuario: " + userId + " eliminado del sistema.");
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

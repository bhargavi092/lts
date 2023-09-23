package com.LTS.Backend.controller;


import com.LTS.Backend.exception.CustomErrorResponse;
import com.LTS.Backend.models.Leaves;
import com.LTS.Backend.models.User;
import com.LTS.Backend.request.UserLoginRequest;
import com.LTS.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            String errorMessage = "An error occurred during user registration: " + e.getMessage();
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest userLoginRequest){
        User user = userService.loginUser(userLoginRequest.getEmail(),userLoginRequest.getPassword());
        if( user!= null){
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/manager/settings/{updatedLeaveCount}")
    public ResponseEntity<?> updateAllEmployeesLeaveCount(@PathVariable int updatedLeaveCount){
        List<User> users = userService.updateLeaveCount(updatedLeaveCount);

        if(!users.isEmpty()){
            return ResponseEntity.ok("success");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

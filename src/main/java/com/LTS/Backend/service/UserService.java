package com.LTS.Backend.service;

import com.LTS.Backend.exception.CustomErrorResponse;
import com.LTS.Backend.models.User;
import com.LTS.Backend.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user){
        return userRepository.save(user);
    }

    public User loginUser(String email , String password){
        User user = userRepository.findByEmail(email);
        if(user!=null && user.getPassword().equals(password)){
            return user;
        }
        else{
            return null;
        }
    }


    public List<User> updateLeaveCount(int updatedLeaveCount){
        List<User> employees = userRepository.findByRole("employee");

        for(User employee: employees){
            employee.setTotalLeaveCount(updatedLeaveCount);
        }
        return  userRepository.saveAll(employees);
    }
}

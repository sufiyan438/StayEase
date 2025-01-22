package com.StayEase.Stay_Ease_Project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StayEase.Stay_Ease_Project.Exchange.User.DeleteUserRequest;
import com.StayEase.Stay_Ease_Project.Exchange.User.GetUserByEmailRequest;
import com.StayEase.Stay_Ease_Project.Exchange.User.GetUserResponse;
import com.StayEase.Stay_Ease_Project.Exchange.User.PutUserRequest;
import com.StayEase.Stay_Ease_Project.Service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody DeleteUserRequest request) {
        return ResponseEntity.ok().body(userService.deleteByEmail(request.getEmail()));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody PutUserRequest putUserRequest) {
        return ResponseEntity.ok().body(userService.updateUser(putUserRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping
    public ResponseEntity<GetUserResponse> getUser(@Valid @RequestBody GetUserByEmailRequest request) {
        return ResponseEntity.ok().body(userService.getUserByEmail(request.getEmail()));
    }

}

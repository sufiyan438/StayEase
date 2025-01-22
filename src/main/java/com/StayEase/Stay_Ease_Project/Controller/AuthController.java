package com.StayEase.Stay_Ease_Project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.StayEase.Stay_Ease_Project.Exchange.Auth.AuthResponse;
import com.StayEase.Stay_Ease_Project.Exchange.Auth.AuthRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Auth.RegisterRequest;
import com.StayEase.Stay_Ease_Project.Service.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request){ 
        return ResponseEntity.ok().body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("JWTtest")
    public ResponseEntity<String> random() {
        return ResponseEntity.ok("String");
    }

}

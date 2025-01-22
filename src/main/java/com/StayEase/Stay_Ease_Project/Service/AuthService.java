package com.StayEase.Stay_Ease_Project.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.StayEase.Stay_Ease_Project.Exchange.Auth.AuthResponse;
import com.StayEase.Stay_Ease_Project.Exception.ResourceNotFoundException;
import com.StayEase.Stay_Ease_Project.Exception.ResourceAlreadyExistsException;
import com.StayEase.Stay_Ease_Project.Exchange.Auth.AuthRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Auth.RegisterRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Auth.Role;
import com.StayEase.Stay_Ease_Project.Model.User;
import com.StayEase.Stay_Ease_Project.Repository.UserRepository;

@Service
public class AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    private static final String MESSAGE= "Successfully registered! Kindly log in to generate and store your JWT token";
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            logger.info(request.getEmail() + " already exists!");
            throw new ResourceAlreadyExistsException(request.getEmail() + " already exists!");
        }
        if(request.getRole() == null)request.setRole(Role.CUSTOMER);
        String roleStr = request.getRole().toString().toUpperCase();
        try{
            Role role = Role.valueOf(roleStr);
            request.setRole(role);
        }catch(Exception e){
            request.setRole(Role.CUSTOMER);
        }
        User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();
        userRepository.save(user);
        logger.info(request.getName() + " has successfully registered!");
        return MESSAGE;
    }

    public AuthResponse login(AuthRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> {
                                    logger.warn(request.getEmail() + " doesn't exist");
                                    return new ResourceNotFoundException(request.getEmail() + " doesn't exist!");
                                });
        String storedPassword = user.getPassword();
        if(!passwordEncoder.matches(request.getPassword(), storedPassword)){
            logger.warn(request.getEmail() + " gave the wrong password");
            throw new ResourceNotFoundException("Wrong password!");
        }
        String jwtToken = jwtService.generateToken(user);
        logger.info(request.getEmail() + " has successfully logged in!");
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}

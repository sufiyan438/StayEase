package com.StayEase.Stay_Ease_Project.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.StayEase.Stay_Ease_Project.Exception.ResourceNotFoundException;
import com.StayEase.Stay_Ease_Project.Exchange.Auth.Role;
import com.StayEase.Stay_Ease_Project.Exchange.User.GetUserResponse;
import com.StayEase.Stay_Ease_Project.Exchange.User.PutUserRequest;
import com.StayEase.Stay_Ease_Project.Model.Booking;
import com.StayEase.Stay_Ease_Project.Model.User;
import com.StayEase.Stay_Ease_Project.Repository.UserRepository;
import com.StayEase.Stay_Ease_Project.Repository.BookingRepository;

@Service
public class UserService implements UserDetailsService{

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    // @Autowired
    private UserRepository userRepository;

    // @Autowired
    private PasswordEncoder passwordEncoder;
    
    private BookingRepository bookingRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, BookingRepository bookingRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).get();
    }

    public String deleteByEmail(String email){
        logger.info("Beginning deletion of " + email);
        User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> {
                                    logger.warn(email + " doesn't exist");
                                    return new ResourceNotFoundException(email + " doesn't exist!");
                                });

        userRepository.deleteById(user.getId());
        logger.info(email + " successfully deleted!");
        return email + " successully deleted";
    }

    public String updateUser(PutUserRequest request){
        logger.info("Beginning updation of " + request.getEmail());
        String email = request.getEmail();
        User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> {
                                    logger.warn(email + " doesn't exist");
                                    return new ResourceNotFoundException(email + " doesn't exist!");
                                });

        if(request.getName() != null)user.setName(request.getName());
        if(request.getPassword() != null)user.setPassword(passwordEncoder.encode(request.getPassword()));
        if(request.getRole() != null){
            String roleStr = request.getRole().toString().toUpperCase();
            Role role = Role.valueOf(roleStr);
            user.setRole(role);
        }

        userRepository.save(user);
        logger.info(request.getEmail() + " has been updated!");
        return "Details updated! Kindly log in again to generate a new JWT.";
    }

    public List<GetUserResponse> getAllUsers() {
        logger.info("Retrieving all users...");
        List<User> userList = userRepository.findAll();
        if(userList.size() == 0)return new ArrayList<>();
        List<GetUserResponse> gurList = new ArrayList<>();
        for(User user : userList){
            gurList.add(getUserByEmail(user.getEmail()));
        }
        return gurList;
    }

    public GetUserResponse getUserByEmail(String email){
        logger.info("Retreiving details of " + email);
        User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> {
                                    logger.warn(email + " doesn't exist");
                                    return new ResourceNotFoundException(email + " doesn't exist!");
                                });
        
        GetUserResponse gur = mapToGUR(user);
        List<Booking> bookingList = bookingRepository.findAllByEmail(email);
        
        logger.info("Booking found of " + email);
        List<String> hotelNames = new ArrayList<>();
        for(Booking booking : bookingList){
            hotelNames.add(booking.getHotelName());
        }

        gur.setHotels(hotelNames);
        logger.info("Details retreived.");
        return gur;
    }

    private GetUserResponse mapToGUR(User user){
        GetUserResponse gur = GetUserResponse.builder()
                                            .name(user.getName())
                                            .email(user.getEmail())
                                            .role(user.getRole().toString())
                                            .build();
                                            
        logger.info("Mapped to GetUserResponse");
        return gur;
    }
    
}

package com.StayEase.Stay_Ease_Project.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StayEase.Stay_Ease_Project.Model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);

    boolean existsByPassword(String password);
}

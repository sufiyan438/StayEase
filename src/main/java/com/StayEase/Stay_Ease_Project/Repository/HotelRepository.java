package com.StayEase.Stay_Ease_Project.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StayEase.Stay_Ease_Project.Model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
    boolean existsByName(String name);
    
    Optional<Hotel> findByName(String name);
}

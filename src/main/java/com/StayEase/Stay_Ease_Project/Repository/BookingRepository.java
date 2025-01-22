package com.StayEase.Stay_Ease_Project.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.StayEase.Stay_Ease_Project.Model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{
    boolean existsByHotelName(String hotelName);

    @Query(value="select b.id from booking b where b.email =:email and b.hotel_name=:hotelName", nativeQuery=true)
    Long getBooking(@Param("email") String email, @Param("hotelName") String hotelName);

    @Query(value="select * from booking b where b.email =:email and b.hotel_name=:hotelName and b.active_booking=true", nativeQuery=true)
    Optional<Booking> checkOut(@Param("email") String email, @Param("hotelName") String hotelName);

    List<Booking> findAllByEmail(String email);

    List<Booking> findAllByHotelName(String name);

    boolean existsByEmail(String email);
}

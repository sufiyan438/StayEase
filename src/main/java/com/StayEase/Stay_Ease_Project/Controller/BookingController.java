package com.StayEase.Stay_Ease_Project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StayEase.Stay_Ease_Project.Exchange.Booking.DeleteBookingRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Booking.GetHotelAdminResponse;
import com.StayEase.Stay_Ease_Project.Exchange.Booking.GetHotelRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Booking.CheckOutRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Booking.PostBookingRequest;
import com.StayEase.Stay_Ease_Project.Service.BookingService;
import com.StayEase.Stay_Ease_Project.Service.JWTService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private JWTService jwtService;
    
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<String> createBookingCheckIn(@RequestHeader("Authorization") String header, @Valid @RequestBody PostBookingRequest request){ 
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        return ResponseEntity.ok().body(bookingService.createBooking(email, request));
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkOut(@RequestHeader("Authorization") String header, @Valid @RequestBody CheckOutRequest request){ 
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        return ResponseEntity.ok().body(bookingService.checkOut(email, request.getHotelName()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBooking(@Valid @RequestBody DeleteBookingRequest request) {
        return ResponseEntity.ok().body(bookingService.deleteBooking(request));
    }

    @GetMapping("/hotel")
    public ResponseEntity<GetHotelAdminResponse> getBookingDataByHotels(@Valid @RequestBody GetHotelRequest request) {
        return ResponseEntity.ok().body(bookingService.getHotelData(request.getHotelname()));
    }

    @GetMapping("/hotel/all")
    public ResponseEntity<List<GetHotelAdminResponse>> getBookingDataForAllHotels() {
        return ResponseEntity.ok().body(bookingService.getAllHotelData());
    }
}

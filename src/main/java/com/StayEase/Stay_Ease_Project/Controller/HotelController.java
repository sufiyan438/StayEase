package com.StayEase.Stay_Ease_Project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StayEase.Stay_Ease_Project.Exchange.Hotel.DeleteHotelRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Hotel.GetHotelRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Hotel.GetHotelResponse;
import com.StayEase.Stay_Ease_Project.Exchange.Hotel.PostHotelRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Hotel.PutHotelRequest;
import com.StayEase.Stay_Ease_Project.Service.HotelService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    
    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<String> createHotel(@Valid @RequestBody PostHotelRequest request){ 
        return ResponseEntity.ok().body(hotelService.createHotel(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteHotel(@Valid @RequestBody DeleteHotelRequest request) {
        return ResponseEntity.ok().body(hotelService.deleteHotel(request.getName()));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateHotel(@Valid @RequestBody PutHotelRequest request) {
        return ResponseEntity.ok().body(hotelService.updateHotel(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetHotelResponse>> getAllHotels() {
        return ResponseEntity.ok().body(hotelService.getAllHotels());
    }

    @GetMapping
    public ResponseEntity<GetHotelResponse> getHotel(@Valid @RequestBody GetHotelRequest request) {
        return ResponseEntity.ok().body(hotelService.getHotel(request.getName()));
    }

}

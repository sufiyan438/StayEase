package com.StayEase.Stay_Ease_Project.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StayEase.Stay_Ease_Project.Exception.ResourceAlreadyExistsException;
import com.StayEase.Stay_Ease_Project.Exception.ResourceNotAvailableException;
import com.StayEase.Stay_Ease_Project.Exception.ResourceNotFoundException;
import com.StayEase.Stay_Ease_Project.Exchange.Hotel.GetHotelResponse;
import com.StayEase.Stay_Ease_Project.Exchange.Hotel.PostHotelRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Hotel.PutHotelRequest;
import com.StayEase.Stay_Ease_Project.Model.Hotel;
import com.StayEase.Stay_Ease_Project.Repository.HotelRepository;

@Service
public class HotelService {

    private Logger logger = LoggerFactory.getLogger(HotelService.class);
    
    @Autowired
    private HotelRepository hotelRepository;

    public String createHotel(PostHotelRequest request){
        if(hotelRepository.existsByName(request.getName())){
            logger.info(request.getName() + " already exists!");
            throw new ResourceAlreadyExistsException(request.getName() + " already exists!");
        }

        if(request.getNumberOfRoomsAvailable() <= 0)throw new ResourceNotAvailableException("Hotel with 0 or less rooms cannot be created!");

        Hotel hotel = Hotel.builder()
                    .name(request.getName())
                    .location(request.getLocation())
                    .description(request.getDescription())
                    .numberOfRoomsAvailable(request.getNumberOfRoomsAvailable())
                    .build();

        hotelRepository.save(hotel);
        logger.info(request.getName() + " has successfully registered!");
        return "Hotel successfully created!";
    }
    
    public String deleteHotel(String name){
        logger.info("Beginning deletion of " + name);
        Hotel hotel = hotelRepository.findByName(name)
                                .orElseThrow(() -> {
                                    logger.warn(name + " doesn't exist");
                                    return new 
                                    ResourceNotFoundException(name + " doesn't exist!");
                                });
        hotelRepository.deleteById(hotel.getId());
        logger.info(name + " successfully deleted!");
        return name + " successully deleted";
    }

    public String updateHotel(PutHotelRequest request){
        logger.info("Beginning updation of " + request.getName());
        String name = request.getName();
        Hotel hotel = hotelRepository.findByName(name)
                                .orElseThrow(() -> {
                                    logger.warn(name + " doesn't exist");
                                    return new ResourceNotFoundException(name + " doesn't exist!");
                                });

        if(request.getLocation() != null)hotel.setLocation(request.getLocation());
        if(request.getDescription() != null)hotel.setDescription(request.getDescription());
        if(request.getNumberOfRoomsAvailable() != null){
            if(request.getNumberOfRoomsAvailable() <= 0)throw new ResourceNotAvailableException("Hotel with 0 or less rooms cannot be created!");
            hotel.setNumberOfRoomsAvailable(request.getNumberOfRoomsAvailable());
        }

        hotelRepository.save(hotel);
        logger.info(request.getName() + " has been updated!");
        return "Hotel details updated!";
    }

    public List<GetHotelResponse> getAllHotels() {
        logger.info("Retrieving all hotels...");
        List<Hotel> hotelList = hotelRepository.findAll();

        if(hotelList.size() == 0)throw new ResourceNotFoundException("No hotels present!");
        List<GetHotelResponse> ghrList = new ArrayList<>();
        for(Hotel hotel : hotelList){
            ghrList.add(mapToGHR(hotel));
        }

        logger.info("Retrieval completed.");
        return ghrList;
    }

    public GetHotelResponse getHotel(String name){
        logger.info("Retreiving details of " + name);
        Hotel hotel = hotelRepository.findByName(name)
                                .orElseThrow(() -> {
                                    logger.warn(name + " doesn't exist");
                                    return new ResourceNotFoundException(name + " doesn't exist!");
                                });

        logger.info("Details retreived.");
        return mapToGHR(hotel);
    }

    private GetHotelResponse mapToGHR(Hotel hotel){
        GetHotelResponse ghr = GetHotelResponse.builder()
                                            .name(hotel.getName())
                                            .location(hotel.getLocation())
                                            .description(hotel.getDescription())
                                            .numberOfRoomsAvailable(hotel.getNumberOfRoomsAvailable())
                                            .build();
                                            
        logger.info("Mapped to GetHotelResponse");
        return ghr;
    }
}

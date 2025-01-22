package com.StayEase.Stay_Ease_Project.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StayEase.Stay_Ease_Project.Exception.AccessDeniedException;
import com.StayEase.Stay_Ease_Project.Exception.ResourceNotAvailableException;
import com.StayEase.Stay_Ease_Project.Exception.ResourceNotFoundException;
import com.StayEase.Stay_Ease_Project.Model.Booking;
import com.StayEase.Stay_Ease_Project.Model.Hotel;
import com.StayEase.Stay_Ease_Project.Model.User;
import com.StayEase.Stay_Ease_Project.Repository.BookingRepository;
import com.StayEase.Stay_Ease_Project.Repository.HotelRepository;
import com.StayEase.Stay_Ease_Project.Repository.UserRepository;
import com.StayEase.Stay_Ease_Project.Exchange.Booking.DeleteBookingRequest;
import com.StayEase.Stay_Ease_Project.Exchange.Booking.GetHotelAdminResponse;
import com.StayEase.Stay_Ease_Project.Exchange.Booking.PostBookingRequest;

@Service
public class BookingService {

    private Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    public String deleteBooking(DeleteBookingRequest request){
        String hotelName = request.getHotelName();
        String email = request.getEmail();
        logger.info("Beginning deletion of " + request);
        
        if(!userRepository.existsByEmail(email)){
            logger.warn(email + " doesn't exist!");
            throw new ResourceNotFoundException(email + " doesn't exists!");
        }

        if(!hotelRepository.existsByName(hotelName)){
            logger.warn(hotelName + " doesn't exist!");
            throw new ResourceNotFoundException(hotelName + " doesn't exists!");
        }

        Long bookingId = bookingRepository.getBooking(email, hotelName);
        logger.info("Query worked: " + bookingId);
        bookingRepository.deleteById(bookingId);
        logger.info("Booking deleted.");
        return "Booking deleted!";
    }

    public List<GetHotelAdminResponse> getAllHotelData(){
        List<GetHotelAdminResponse> ghrList = new ArrayList<>();
        List<Hotel> hotelList = hotelRepository.findAll();
        for(Hotel hotel : hotelList){
            ghrList.add(getHotelData(hotel.getName()));
        }
        return ghrList;
    }

    public GetHotelAdminResponse getHotelData(String name){
        logger.info("Retreiving details of " + name);
        Hotel hotel = hotelRepository.findByName(name)
                                .orElseThrow(() -> {
                                    logger.warn(name + " doesn't exist");
                                    return new ResourceNotFoundException(name + " doesn't exist!");
                                });

        List<String> emails = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findAllByHotelName(name);
        for(Booking booking : bookings){
            emails.add(booking.getEmail());
        }

        GetHotelAdminResponse ghr = GetHotelAdminResponse.builder()
                                            .name(hotel.getName())
                                            .location(hotel.getLocation())
                                            .description(hotel.getDescription())
                                            .numberOfRoomsAvailable(hotel.getNumberOfRoomsAvailable())
                                            .emailIds(emails)
                                            .build();
        logger.info("Mapped to GetHotelResponse");
        return ghr;
    }

    public String checkOut(String email, String hotelName){
        logger.info("Beginning checkout " + email);
        User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> {
                                    logger.warn(email + " doesn't exist");
                                    return new ResourceNotFoundException(email + " doesn't exist!");
                                });

        Hotel hotel = hotelRepository.findByName(hotelName)
                                .orElseThrow(() -> {
                                    logger.warn(hotelName + " doesn't exist");
                                    return new ResourceNotFoundException(hotelName + " doesn't exist!");
                                });

        Booking booking = bookingRepository.checkOut(email, hotelName)
                                .orElseThrow(() -> {
                                    logger.warn("Booking doesn't exist");
                                    return new ResourceNotFoundException("Booking doesn't exist!");
                                });

        if(!booking.getEmail().equals(email))throw new AccessDeniedException("You did not book this room, so you cannot checkout! Only the CUSTOMER themself can!");
        booking.setActiveBooking(false);
        bookingRepository.save(booking);

        hotel.setNumberOfRoomsAvailable(hotel.getNumberOfRoomsAvailable() + 1);
        hotelRepository.save(hotel);
        logger.info("Checkout completed...");
        return "Checked out!";
    }

    public String createBooking(String email, PostBookingRequest request){
        User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> {
                                    logger.warn(email + " doesn't exist");
                                    return new ResourceNotFoundException(email + " doesn't exist!");
                                });

        if(!user.getRole().toString().equals("CUSTOMER"))throw new AccessDeniedException("User doesn't have the role needed to make the booking!");
        if(request.getNumberOfCustomers() == null)request.setNumberOfCustomers(1);
        if(request.getNumberOfCustomers() > 2)throw new ResourceNotAvailableException("Cannot accomodate more than 2 guests!");

        Hotel hotel = hotelRepository.findByName(request.getHotelName())
                                .orElseThrow(() -> {
                                    logger.warn(request.getHotelName() + " doesn't exist");
                                    return new ResourceNotFoundException(request.getHotelName() + " doesn't exist!");
                                });

        if(hotel.getNumberOfRoomsAvailable() <= 0){
            logger.info("No rooms available at the " + request.getHotelName());
            throw new ResourceNotAvailableException("No rooms available at the " + request.getHotelName());
        }
        
        hotel.setNumberOfRoomsAvailable(hotel.getNumberOfRoomsAvailable() - 1);
        hotelRepository.save(hotel);     

        Booking booking = Booking.builder()
                    .hotelName(request.getHotelName())
                    .email(email)
                    .activeBooking(true)
                    .numberOfGuests(request.getNumberOfCustomers())
                    .build();
        bookingRepository.save(booking);
        logger.info("Booking is completed!");
        return "Booking successfully created!";
    }
}

package com.StayEase.Stay_Ease_Project.Exchange.Booking;

import lombok.Data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetHotelAdminResponse {
    private String name;
    private String location;
    private String description;
    private int numberOfRoomsAvailable;
    private List<String> emailIds;
}

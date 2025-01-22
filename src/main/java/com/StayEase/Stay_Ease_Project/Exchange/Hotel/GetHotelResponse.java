package com.StayEase.Stay_Ease_Project.Exchange.Hotel;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetHotelResponse {
    private String name;
    private String location;
    private String description;
    private int numberOfRoomsAvailable;
}

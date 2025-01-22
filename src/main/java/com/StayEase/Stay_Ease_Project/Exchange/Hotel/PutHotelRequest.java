package com.StayEase.Stay_Ease_Project.Exchange.Hotel;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutHotelRequest {
    @NotNull
    private String name;
    private String location;
    private String description;
    private Integer numberOfRoomsAvailable;
}

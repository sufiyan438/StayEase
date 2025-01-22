package com.StayEase.Stay_Ease_Project.Exchange.Booking;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class PostBookingRequest {
    @NotNull
    private String hotelName;

    private Integer numberOfCustomers;
}

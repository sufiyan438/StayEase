package com.StayEase.Stay_Ease_Project.Exchange.Booking;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class DeleteBookingRequest {
    @NotNull
    private String hotelName;

    @NotNull
    private String email;
}

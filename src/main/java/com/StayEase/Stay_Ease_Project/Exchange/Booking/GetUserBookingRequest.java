package com.StayEase.Stay_Ease_Project.Exchange.Booking;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class GetUserBookingRequest {
    @NotNull
    private String email;
}

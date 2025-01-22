package com.StayEase.Stay_Ease_Project.Exchange.Booking;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckOutRequest {
    @NotNull
    private String hotelName;
}

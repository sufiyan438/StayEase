package com.StayEase.Stay_Ease_Project.Exchange.Hotel;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteHotelRequest {
    @NotNull
    private String name;
}

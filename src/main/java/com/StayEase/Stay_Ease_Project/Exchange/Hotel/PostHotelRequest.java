package com.StayEase.Stay_Ease_Project.Exchange.Hotel;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostHotelRequest {
    @NotNull
    private String name;

    @NotNull
    private String location;

    @NotNull
    @Length(min=20, max=100)
    private String description;

    @NotNull
    private int numberOfRoomsAvailable;
}

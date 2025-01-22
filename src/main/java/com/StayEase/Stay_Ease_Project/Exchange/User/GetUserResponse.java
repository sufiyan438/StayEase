package com.StayEase.Stay_Ease_Project.Exchange.User;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserResponse {
    private String name;

    private String email;
    
    private String role;

    private List<String> hotels;
}

package com.StayEase.Stay_Ease_Project.Exchange.User;

import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NoArgsConstructor
public class GetUserByEmailRequest {
    @NotNull
    @Email
    private String email;
}

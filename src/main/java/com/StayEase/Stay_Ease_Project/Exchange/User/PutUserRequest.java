package com.StayEase.Stay_Ease_Project.Exchange.User;

import com.StayEase.Stay_Ease_Project.Exchange.Auth.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutUserRequest {
    private String name;

    @NotNull
    @Email
    private String email;

    private String password;
    
    private Role role;
}    
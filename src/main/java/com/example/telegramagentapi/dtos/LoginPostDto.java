package com.example.telegramagentapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPostDto {
    @NotNull(message = "Field cannot be null")
    @Min(value = 3, message = "Minimum login length is 3")
    @Max(value = 40, message = "Maximum message length is 40")
    private String username;
    private String email;
    @NotNull
    @Min(value = 3, message = "Minimum login length is 3")
    @Max(value = 40, message = "Maximum message length is 40")
    private String password;
}

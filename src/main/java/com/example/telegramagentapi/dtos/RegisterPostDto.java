package com.example.telegramagentapi.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPostDto {
    @NotNull(message = "Field cannot be null")
    @Min(value = 3, message = "Minimum name length is 3")
    @Max(value = 40, message = "Maximum name length is 40")
    private String name;
    @NotNull(message = "Field cannot be null")
    @Min(value = 3, message = "Minimum surname length is 3")
    @Max(value = 40, message = "Maximum surname length is 40")
    private String surname;

    @NotNull(message = "Field cannot be null")
    @Email
    private String email;
    private String phone;
    @NotNull(message = "Field cannot be null")
    @Min(value = 3, message = "Minimum surname length is 3")
    @Max(value = 40, message = "Maximum surname length is 40")
    private String password;
    @NotNull(message = "Field cannot be null")
    @Min(value = 3, message = "Minimum username length is 3")
    @Max(value = 40, message = "Maximum username length is 40")
    private String username;
}

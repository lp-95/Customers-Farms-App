package com.example.customersfarms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Size( min = 3, max = 25, message = "Username needs between 3 and 25 characters" )
    private String username;
    @Size( min = 8, max = 25, message = "Password needs between 8 and 25 characters")
    private String password;
    @Email( message = "E-mail not exist" )
    private String email;
}
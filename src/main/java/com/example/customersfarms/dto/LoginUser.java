package com.example.customersfarms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    @Size( min = 3, max = 25 )
    private String username;
    @Size( min = 8, max = 25 )
    private String password;
}
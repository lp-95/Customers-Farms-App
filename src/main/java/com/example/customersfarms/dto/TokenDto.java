package com.example.customersfarms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    @NotBlank
    private String token;
    @NotBlank
    private String username;
}
package com.example.customersfarms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmDto {
    @Size( min = 3, max = 25, message = "Name needs between 3 and 25 characters" )
    private String name;
    private String description;
}

package com.example.customersfarms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmResponse {
    private Long id;
    private String name;
    private String description;
    private Long userId;
}

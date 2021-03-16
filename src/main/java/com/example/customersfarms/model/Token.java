package com.example.customersfarms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Token {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    private String token;
    @OneToOne( fetch = FetchType.LAZY )
    private User user;
}

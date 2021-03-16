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
public class Farm {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    @Column( unique = true )
    private String name;
    private String description;
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "user_id" )
    private User user;
}

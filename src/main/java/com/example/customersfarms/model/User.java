package com.example.customersfarms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    @Column( unique = true )
    private String username;
    private String password;
    @Column( unique = true )
    private String email;
    private boolean enabled = false;
    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
            @JoinColumn( name = "USER_ID")
            },
            inverseJoinColumns = {
            @JoinColumn( name = "ROLE_ID") })
    private Set<Role> roles;
}
package org.example.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long sid;
    @Column(name = "coins")
    private Integer coins;
    @Column(name = "username", unique = true)
    private String username;

}

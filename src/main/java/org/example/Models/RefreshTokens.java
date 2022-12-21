package org.example.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tokens")
public class RefreshTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "token_id", nullable = false)
    private Long tokenId;
    @Column(name = "value")
    private String value;

}

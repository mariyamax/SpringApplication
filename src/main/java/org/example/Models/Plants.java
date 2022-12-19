package org.example.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "plants")
public class Plants {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long sid;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "family")
    private String family;
    @Column(name = "area")
    private String area;
    @Column(name = "author")
    private String author;
    @Column(name = "coast")
    private Integer coast;
    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;
    @Column(name = "image_id")
    private Long imageId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_token")
    private String userToken;

    public Plants() {
    }
}

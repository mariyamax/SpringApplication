package org.example.Controllers.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="data")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "type", nullable = true)
    private String type;
    @Column(name = "area", nullable = true)
    private String area;
    @Column(name = "author", nullable = true)
    private String author;
    @Column(name = "sale", nullable = true)
    private Boolean isSale;
    @Column(name="coast")
    private Integer coast;
    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "plant")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime dataofCreate;
    @ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
    @JoinColumn
    private User user;

    @PrePersist
    private void init(){
        dataofCreate=LocalDateTime.now();
    }
    public Plant() {
    }

    public void addImageToPlant(Image image) {
        image.setPlant(this);
        images.add(image);
    }


    protected boolean canEqual(final Object other) {
        return other instanceof Plant;
    }


}

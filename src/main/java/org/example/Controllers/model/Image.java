package org.example.Controllers.model;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Data
@Table(name="images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long ID;
    @Column(name = "path")
    private String path;
    @Column(name = "originalName")
    private String originalName;
    @Column(name = "size")
    private Long size;
    @Column(name = "fileExtension")
    private String fileExtension;
    @Column(name = "isPreview")
    private boolean isPreview;
    @Column(name = "bytes")
    @Lob
    private byte[] bytes;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private Plant plant;

    public Image(){}

    public void toImageEntity(MultipartFile file, Image image) throws IOException {
        image.setPath(file.getName());
        image.setOriginalName(file.getOriginalFilename());
        image.setFileExtension(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
    }
}

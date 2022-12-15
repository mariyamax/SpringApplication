package org.example.Models;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Data
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long ID;
    @Column(name = "path")
    private String path;
    @Column(name = "size")
    private Long size;
    @Column(name = "fileExtension")
    private String fileExtension;
    @Column(name = "bytes")
    @Lob
    private byte[] bytes;

    public Images() {
    }

    public void toImageEntity(MultipartFile file) {
        this.setPath(file.getName());
        this.setFileExtension(file.getContentType());
        this.setSize(file.getSize());
        try {
            this.setBytes(file.getBytes());
        } catch (IOException e) {
            //todo write some
            throw new RuntimeException(e);
        }
    }

}

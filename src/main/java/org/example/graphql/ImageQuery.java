package org.example.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.example.Models.Images;
import org.example.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class ImageQuery implements GraphQLQueryResolver {

    @Autowired
    private ImageService service;

    public Images getImage(Long id) {
        return service.findBySid(id);
    }

}

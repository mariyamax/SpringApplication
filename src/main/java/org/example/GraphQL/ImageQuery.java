package org.example.GraphQL;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.example.Models.Images;
import org.example.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageQuery implements GraphQLQueryResolver {

    @Autowired
    private ImageService service;

    public Images getImage(String imageURL) {
        return service.findByURL(imageURL);
    }

}

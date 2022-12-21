package org.example.GraphQL;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.example.Models.Images;
import org.example.Services.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ImagesQuery implements GraphQLQueryResolver {

    @Autowired
    private ImagesService service;

    public Images getImage(Long id) {
        return service.findById(id);
    }

}

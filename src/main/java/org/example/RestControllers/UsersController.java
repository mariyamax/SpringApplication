package org.example.RestControllers;

import lombok.AllArgsConstructor;
import org.example.Models.Users;
import org.example.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService service;

    @GetMapping(value = "")
    public List<Users> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public Users findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(Users resource) {
        return service.save(resource);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

}
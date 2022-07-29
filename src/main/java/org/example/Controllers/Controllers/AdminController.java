package org.example.Controllers.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.Controllers.Services.UserService;
import org.example.Controllers.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;


    @GetMapping("/admin")
    public String admin(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users",users);
        model.addAttribute("user",users.iterator().next());
        return "admin";
    }
    @PostMapping("/user/ban/{email}")
    public String userBan(@PathVariable("email") String email){
        userService.banUser(email);
        return "redirect:/admin";
    }
    @PostMapping("/user/edit/{email}")
    public String changeRole(@PathVariable("email") String email){
    userService.ChangeRole(email);
    return "redirect:/admin";
    }

}

package org.example.Controllers;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.example.Models.User;
import org.example.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class GreetingController {
  private final UserService userService;


  @GetMapping("/")
  public String mainPage(Principal principal, Model model) {
    System.out.println("get /");
    User user = userService.getByUserMail(principal.getName());
    model.addAttribute("user", user);
    return "homePage";
  }

  @PostMapping("/homePage")
  public String redirectPage(Principal principal, Model model) {
    System.out.println("/ post home pge");
    User user = userService.getByUserMail(principal.getName());
    model.addAttribute("user", user);
    return "homePage";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @PostMapping("/login")
  public String mainPage() {
    return "redirect:/";
  }

  @GetMapping("/registration")
  public String registration() {
    return "registration";
  }

  @PostMapping("/registration")
  public String createUser(User user) {
    User existingUser = userService.getByUserMail(user.getEmail());
    if (!userService.createUser(user)) {
      return "redirect:/registration";
    } else {
      return "redirect:/login";
    }
  }

}

package org.example.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.Models.User;
import org.example.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;

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

    /*@GetMapping("/user/{id}")
    public String userInfo(@PathVariable("id") String id,Model model,Principal principal){
        User user = userService.findByID(Long.decode(id));
        if(user.getAvatar()!=null){
            model.addAttribute("avatar","avatar exist");
            model.addAttribute("id",user.getAvatar().getID());
        }
        if(principal.getName().equals(user.getEmail())){
            model.addAttribute("homepage","mypage");
        }
        model.addAttribute("owner",userService.getByUserMail(principal.getName()));
        model.addAttribute("coins",user.getCoins());
        model.addAttribute("user",user);
        model.addAttribute("uName",user.getUname());
        model.addAttribute("plants",user.getPlants());
        return "user-info";
    }
    @PostMapping("/foto")
    public String addAvatar(@RequestParam("avatar") MultipartFile avatar, Principal principal) throws IOException {
        userService.addAvatar(avatar, principal);
        return "redirect:/";
    }*/

   /* @PostMapping("/coins")
    public String userCoins(Principal principal){
       User user = userService.getByUserMail(principal.getName());
       System.out.println((LocalDateTime.now().getDayOfMonth()-user.getLastVisitTime().getDayOfMonth()));
       if(((LocalDateTime.now().getDayOfMonth()-user.getLastVisitTime().getDayOfMonth())>=1)){
           user.setCoins(user.getCoins()+user.getRegular());
           user.setLastVisitTime(LocalDateTime.now());
           userService.saveUser(user);
           if(user.getRegular()<7){
               user.setRegular(user.getRegular()+1);
               userService.saveUser(user);
           }
       }
       return "redirect:/";
    }*/
}

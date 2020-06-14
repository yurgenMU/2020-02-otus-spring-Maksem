package ru.otus.spring.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.domain.user.User;
import ru.otus.spring.domain.user.UserPrincipal;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/")
    public String welcome() {
        return "index";
    }

//    @GetMapping("/login")
//    public String getLoginPage() {
//        return "login";
//    }
//
//    @PostMapping("/processLogin")
//    public String processLogin(Model model, HttpSession session) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder
//                .getContext().getAuthentication().getPrincipal();
//        System.out.println(userDetails.getUsername());
//        System.out.println(userDetails.getPassword());
//        model.addAttribute("currentUser", userDetails.getUsername());
//        return "redirect:/";
//    }
}

package com.javatechie.controller;

import com.javatechie.config.UserInfoUserDetails;
import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;
import com.javatechie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationProvider authenticationProvider;
    @Autowired
    private UserService service;

    @Autowired
    UserInfoRepository repository;



    @RequestMapping("/hello")
    @ResponseBody
    public ModelAndView welcome(Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("privateOffice");
        Optional<UserInfo> userInfo = repository.findByName(principal.getName());

        if (userInfo.isPresent()){
            modelAndView.addObject("login", userInfo.get().getLogin());
            modelAndView.addObject("name", userInfo.get().getName());
            modelAndView.addObject("lastname", userInfo.get().getLastname());
            modelAndView.addObject("email", userInfo.get().getEmail());
        }
        return modelAndView;
    }
    @GetMapping("/auth")
    public String auth() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/create/user")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return service.addUser(userInfo);
    }
}

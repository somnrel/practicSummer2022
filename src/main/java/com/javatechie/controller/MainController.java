package com.javatechie.controller;

import com.javatechie.config.UserInfoUserDetails;
import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;
import com.javatechie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@RestController
public class MainController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationProvider authenticationProvider;
    @Autowired
    private UserService service;
    @Autowired
    UserInfoRepository repository;


    @RequestMapping("/privateOffice")
    @ResponseBody
    public ModelAndView welcome(Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("privateOffice");
        Optional<UserInfo> userInfo = repository.findUserInfoByLogin(principal.getName());

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
    public String addUser(@RequestBody UserInfo userInfo){
        return service.addUser(userInfo);
    }

    @PutMapping("/edit/user")
    public String editUser(@RequestBody UserInfo userInfo){
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.editUser(userInfo, ((UserInfoUserDetails) authentication).getUsername());
    }

    @GetMapping("/login")
    @ResponseBody
    public ModelAndView getLogin(@RequestParam(value = "error", required = false) String error,
                                 @RequestParam(value = "logout", required = false) String logout, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("error", error!=null);
        modelAndView.addObject("logout", logout!=null);
        return modelAndView;
    }
}

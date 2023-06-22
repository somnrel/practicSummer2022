package com.javatechie.controller;

import com.javatechie.config.UserInfoUserDetails;
import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;
import com.javatechie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
    public Model privateOffice(Principal principal, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserInfo> userInfo = repository.findUserInfoByLogin(principal.getName());
        model.addAttribute("userInfo", userInfo.get());
        return model;
    }
    @GetMapping("/auth")
    public String auth() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/create/user")
    public String addUser(@RequestBody UserInfo userInfo){
        return service.addUser(userInfo);
    }

    @PostMapping("/editUser")
    public RedirectView editUser(@ModelAttribute("userInfo") UserInfo userInfo, Model model, Principal principal){
        Optional<UserInfo> userInfoByLogin = repository.findUserInfoByLogin(principal.getName());
        userInfo.setPassword(userInfoByLogin.get().getPassword());
        userInfo.setRoles(userInfoByLogin.get().getRoles());
        service.editUser(userInfo, principal.getName());
        UserInfoUserDetails forChangePrincipal = (UserInfoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        forChangePrincipal.setName(userInfo.getLogin());
        forChangePrincipal.setPassword(userInfo.getPassword());
        return new RedirectView("privateOffice");
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

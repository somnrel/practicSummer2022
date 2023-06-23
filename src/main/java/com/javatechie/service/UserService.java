package com.javatechie.service;

import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "user added to system ";
    }

    public String editUser(UserInfo userInfo, String oldLogin) {
        Optional<UserInfo> oldUser = repository.findUserInfoByLogin(oldLogin);
        if (oldUser.isPresent()){
            oldUser.get().setLogin(userInfo.getLogin());
            oldUser.get().setName(userInfo.getName());
            oldUser.get().setLastname(userInfo.getLastname());
            oldUser.get().setEmail(userInfo.getEmail());
            repository.save(oldUser.get());
        }
        return "user edited";
    }
}

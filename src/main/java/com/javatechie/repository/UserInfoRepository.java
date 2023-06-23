package com.javatechie.repository;

import com.javatechie.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    @Query(value = "select id from auth.`users` u where u.login =:login", nativeQuery = true)
    Integer getIdByLogin(@Param("login") String login);
    Optional<UserInfo> findByName(String username);
    Optional<UserInfo> findUserInfoByLogin(String userlogin);

}

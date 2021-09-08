package com.example.SpringBoot.repository;

import com.example.SpringBoot.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String username);

//    @Query("select user from User user join fetch user.roles where user.name = :username")
//    Optional<User> findByName(@Param("name") String name);

    @Query("select distinct u from User u join fetch u.roles")
    List<User> getAllUsers();


}

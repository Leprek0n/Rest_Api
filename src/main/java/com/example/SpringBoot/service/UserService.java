package com.example.SpringBoot.service;

import com.example.SpringBoot.model.User;
import com.example.SpringBoot.transferObject.UserDto;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public User getUserById(Long id);

    public User getUserByUsername(String username);

    public void save(User user);

    public void updateUser( User user);

    public void deleteUser(Long id);

    public void save(UserDto userDto);



    public boolean existsByUsername(String username);
}

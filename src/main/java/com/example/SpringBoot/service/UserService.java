package com.example.SpringBoot.service;


import com.example.SpringBoot.model.Role;
import com.example.SpringBoot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> getCustomerList();

    public boolean save(User user);

    public User showById(Long id);

    public void update(User user, Long id);

    public void delete(Long id);

    public List<Role> getRoles();
    public User get(Long id);
    public Optional<User> getOne(Long id);
}

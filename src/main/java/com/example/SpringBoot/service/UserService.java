package com.example.SpringBoot.service;




import com.example.SpringBoot.model.User;
import com.example.SpringBoot.transferObject.NewUserRequest;

import java.util.List;

public interface UserService {
     List<User> getAllUsers();
     User getUserById(Long id);
    boolean existByName(String name);
    User getUserName(String name);
    void save(User user);
    void delete(Long id);
    void update(Long id, NewUserRequest newUserRequest);
    void save(NewUserRequest user);

}

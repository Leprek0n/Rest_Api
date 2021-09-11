package com.example.SpringBoot.service;

import com.example.SpringBoot.model.ERole;
import com.example.SpringBoot.model.Role;
import com.example.SpringBoot.model.User;
import com.example.SpringBoot.repository.RoleRepository;
import com.example.SpringBoot.repository.UserRepository;
import com.example.SpringBoot.service.UserService;
import com.example.SpringBoot.transferObject.NewUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.SpringBoot.model.ERole.ROLE_ADMIN;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
      return userRepository.getById(id);
    }

    @Override
    public boolean existByName(String name) {
        return false;
    }

    @Override
    public User getUserName(String name) {
        return userRepository.getUserByName(name);
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update( Long id, NewUserRequest newUserRequest) {
        User userToBeUpdated = userRepository.getById(id);

        userToBeUpdated.setName(newUserRequest.getName());
        userToBeUpdated.setEmail(newUserRequest.getEmail());


        String rolesForSave = newUserRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByRole(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
        Role adminRole = roleRepository.findByRole(ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));

        if ("ADMIN".equals(rolesForSave)) {

            roles.add(adminRole);
            userToBeUpdated.setRoles(roles);
        } else if ("USER".equals(rolesForSave)) {

            roles.add(userRole);
            userToBeUpdated.setRoles(roles);
        } else {
            Set<Role> rolesFromDb = userToBeUpdated.getRoles();
            for (Role role : (rolesFromDb)) {
                if ((role.getRole().equals(ROLE_ADMIN))) {
                    rolesFromDb.add(userRole);
                } else {
                    rolesFromDb.add(adminRole);
                }
                userToBeUpdated.setRoles(rolesFromDb);
            }
        }
        if ((newUserRequest.getPassword()).length() > 1) {
            userToBeUpdated.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));
        } else {
            userRepository.save(userToBeUpdated);
        }
    }

    @Override
    @Transactional
    public void save(NewUserRequest user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user1 = new User(user.getName(), user.getEmail(), user.getPassword());
        String rolesForSave = user.getRoles();
        Set<Role> roles = new HashSet<>();

        Role adminRole = roleRepository.findByRole(ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
        Role userRole = roleRepository.findByRole(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));

        if("ADMIN".equals(rolesForSave)) {
            roles.add(adminRole);
        } else if ("USER".equals(rolesForSave)) {
            roles.add(userRole);
        } else {
            roles.add(adminRole);
            roles.add(userRole);
        }
        user1.setRoles(roles);
        user1.setPassword(user.getPassword());
        userRepository.save(user1);
    }
}
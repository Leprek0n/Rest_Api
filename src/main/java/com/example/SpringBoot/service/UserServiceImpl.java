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

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.SpringBoot.model.ERole.ROLE_ADMIN;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository repository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = repository;
        this.passwordEncoder = passwordEncoder;

    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(Long id) { return userRepository.getOne(id);}

    @Override
    public User getUserByUsername(String username) {
        Optional<User> userFromDbByUserName = userRepository.findByUsername(username);

        return userFromDbByUserName.orElse(new User());
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void save(NewUserRequest newUserRequest) {
        newUserRequest.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));

        User user = new User(newUserRequest.getUsername(),
                newUserRequest.getPassword(),
                newUserRequest.getFirstName(),
                newUserRequest.getLastName(),
                newUserRequest.getAge());
        String rolesForSave = newUserRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        Role adminRole = roleRepository.findByRole(ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
        Role userRole = roleRepository.findByRole(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));

        if ("ADMIN".equals(rolesForSave)) {
            roles.add(adminRole);
        } else if ("USER".equals(rolesForSave)) {
            roles.add(userRole);
        } else {
            roles.add(userRole);
            roles.add(adminRole);
        }
        user.setRoles(roles);
        user.setPassword(newUserRequest.getPassword());

        userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    @Override
    @Transactional
    public void updateUser(Long id, NewUserRequest updatedUser) {
        User userToBeUpdated = userRepository.getOne(id);

        userToBeUpdated.setFirstName(updatedUser.getFirstName());
        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setAge(updatedUser.getAge());

        String rolesForSave = updatedUser.getRoles();
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
        if ((updatedUser.getPassword()).length() > 1) {
            userToBeUpdated.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        } else {
            userRepository.save(userToBeUpdated);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }
}

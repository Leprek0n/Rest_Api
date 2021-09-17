package com.example.SpringBoot.transferObject;

import com.example.SpringBoot.model.Role;
import com.example.SpringBoot.model.RoleNameEnum;
import com.example.SpringBoot.model.User;
import com.example.SpringBoot.repository.RoleRepository;
import com.example.SpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.example.SpringBoot.model.RoleNameEnum.ROLE_ADMIN;
@Component
public class Converter {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User convert(UserDto userDto) {
        User user = new User(userDto.getUsername(),
                userDto.getPassword(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getAge());
        String rolesForSave = userDto.getRoles();
        Set<Role> roles = new HashSet<>();

        Role adminRole = roleRepository.findByRole(ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
        Role userRole = roleRepository.findByRole(RoleNameEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));

        if ("ADMIN".equals(rolesForSave)) {
            roles.add(adminRole);
        } else if ("USER".equals(rolesForSave)) {
            roles.add(userRole);
        } else {
            roles.add(userRole);
            roles.add(adminRole);
        }

        user.setRoles(roles);
        user.setPassword(userDto.getPassword());

        return user;
    }
    public User update(Long id, UserDto updatedUser) {

        User userToBeUpdated = userRepository.getOne(id);

        userToBeUpdated.setFirstName(updatedUser.getFirstName());
        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setAge(updatedUser.getAge());

        String rolesForSave = updatedUser.getRoles();
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByRole(RoleNameEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
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
        return userToBeUpdated;
    }
}

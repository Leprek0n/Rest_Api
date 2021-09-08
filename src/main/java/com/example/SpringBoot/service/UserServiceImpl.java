package com.example.SpringBoot.service;

import com.example.SpringBoot.model.Role;
import com.example.SpringBoot.model.User;
import com.example.SpringBoot.repository.RoleRepository;
import com.example.SpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository repository;

    @Transactional
    public List<User> getCustomerList() {
        return userRepository.getAllUsers();
    }


    public boolean save(User user) {
        userRepository.save(user);
        return true;
    }

    @Transactional
    public User showById(Long id) {
        return userRepository.getById(id);
    }

    @Transactional
    public void update(User user, Long id) {
        User user1 = userRepository.findByName(user.getName());

        userRepository.save(user1);
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name);
    }
    @Transactional
    public List<Role> getRoles() {
        return repository.findAll();
    }
    @Transactional
    public User get(Long id) {
        return userRepository.getById(id);
    }
    @Transactional
    public Optional<User> getOne(Long id) {
        return userRepository.findById(id);
    }
}

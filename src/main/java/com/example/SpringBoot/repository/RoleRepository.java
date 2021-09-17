package com.example.SpringBoot.repository;

import com.example.SpringBoot.model.RoleNameEnum;
import com.example.SpringBoot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleNameEnum name);



}
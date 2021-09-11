package com.example.SpringBoot.model;

import com.example.SpringBoot.model.ERole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="roles")
@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private ERole role;
    public Role(ERole eRole) {
        this.role = eRole;
    }

    @Override
    public String getAuthority() {
        return role.toString();
    }
}
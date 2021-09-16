package com.example.SpringBoot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Builder
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "user_name")})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    @NotEmpty(message = "Username should not be empty!")
    @Size(min = 2, max = 30)
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty!")
    @Size(min = 5, max = 100, message = "Minimum 5 and maximum 15 characters")
    private String password;

    @Column(name = "first_name")
    @NotEmpty(message = "Fist name should not be empty!")
    @Size(min = 2, max = 30, message = "Minimum 2 and maximum 30 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name should not be empty!")
    @Size(min = 2, max = 30, message = "Minimum 2 and maximum 30 characters")
    private String lastName;

    @Column(name = "age")
    @Min(value = 1, message = "The age cannot be less than 0.")
    private int age;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(@NotEmpty(message = "Username should not be empty!")
                @Size(min = 2, max = 30, message = "Minimum 2 and maximum 30 characters")
                        String username,
                @NotEmpty(message = "Password should not be empty!")
                @Size(min = 5, max = 100, message = "Minimum 5 and maximum 15 characters")
                        String password,
                @NotEmpty(message = "Fist name should not be empty!")
                @Size(min = 2, max = 30, message = "Minimum 2 and maximum 30 characters")
                        String firstName,
                @NotEmpty(message = "Last name should not be empty!")
                @Size(min = 2, max = 30, message = "Minimum 2 and maximum 30 characters")
                        String lastName,
                //@NotEmpty(message = "Age name should not be empty!")
                @Min(value = 0, message = "The age cannot be less than 0.")
                        int age) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
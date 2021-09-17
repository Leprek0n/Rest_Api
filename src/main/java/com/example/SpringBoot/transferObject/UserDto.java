package com.example.SpringBoot.transferObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Size(min = 2, max = 30, message ="Minimum 2 and maximum 30 characters")
    private String username;

    @Size(min = 2, max = 30, message = "Minimum 2 and maximum 30 characters")
    private String firstName;

    @Size(min = 2, max = 30, message = "Minimum 2 and maximum 30 characters")
    private String lastName;

    @Min(value = 0, message = "The age cannot be less than 0.")
    private int age;

    private String roles;

    private String password;


}
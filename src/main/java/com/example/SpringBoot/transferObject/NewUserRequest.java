package com.example.SpringBoot.transferObject;

import lombok.Data;


@Data
public class NewUserRequest {

    private String username;

    private String firstName;

    private String lastName;

    private int age;

    private String roles;

    //@Size(min = 5, max = 100, message ="Minimum 5 and maximum 15 characters")
    private String password;

}
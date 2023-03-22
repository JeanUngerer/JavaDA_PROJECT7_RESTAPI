package com.nnk.springboot.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    Integer id;
    @NotBlank(message = "Username is mandatory")
    String username;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password is too short min 8 characters")
    @Size(max = 16, message = "Password is too long max 16 characters")
    String password;
    String fullname;
    String role;
}

package com.nnk.springboot.models;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    Integer id;
    String username;
    String password;
    String fullname;
    String role;
}
